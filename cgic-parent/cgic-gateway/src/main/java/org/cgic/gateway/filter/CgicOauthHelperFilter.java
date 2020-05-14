package org.cgic.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.cgic.commons.dto.BaseResponseDTO;
import org.cgic.commons.oauth.UserDetailImpl;
import org.cgic.gateway.config.CgicGatewayProperties;
import org.cgic.gateway.exception.ExtractJwtTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 鉴权
 * @Author: charleyZZZZ
 * @Date: 2019/10/14 16:22
 * @Version 1.0
 */
@Component
public class CgicOauthHelperFilter extends ZuulFilter {

    private static final String JWT_TOKEN_KEY = "jwt_token:";
    private static final String JWT_TOKEN = "Jwt_Token";
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "username";
    private static final String NO_LOGIN_PASSWORD = "unknow";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String DISPLAY_NAME = "displayName";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String ROLE_IDS = "roleIds";

    private static final String HEADER_AUTH = "Authorization";
    private static final String PREFIX_AUTH = "Bearer ";

    @Value("${cgic.oauth.jwtToken.time:3600}")
    private int jwtTokenTime;

    @Autowired
    private Signer jwtSigner;

    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> operations = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(CgicOauthHelperFilter.class);
    @Autowired
    private CgicGatewayProperties gatewayHelperProperties;

    private final AntPathMatcher matcher = new AntPathMatcher();
    private final String[] headers = new String[]{"server", "host", "x-application-context", "transfer-encoding"};

    public CgicOauthHelperFilter() {

    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private RedisConnection getConnection() {
        return this.redisConnectionFactory.getConnection();
    }


    /**
     * 过滤器主体
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        HttpServletResponse resp = ctx.getResponse();
        // 跨域处理
        processAccessControl(req, resp);
        //只过滤OPTIONS 请求
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            //不再路由
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            return null;
        }
        // 匹配是否放行请求
        boolean releasedPath = Arrays.stream(this.gatewayHelperProperties.getReleasedPath()).anyMatch((t) -> {
            return this.matcher.match(t, req.getRequestURI());
        });
        // 构造请求oauth服务的header
        HttpEntity entity = getHttpEntity(req);
        String jwtToken = "";
        if (releasedPath) {
            return null;
        }
        String accessToken = getAccessTokenFromRequest(req);
        // 非放行路径检查 access_token
        if (StringUtils.isEmpty(accessToken)) {
            LOGGER.info("get access_token: {} from request failure.", accessToken);
            setGatewayHelperFailureResponse(ctx, 401);
            return null;
        }

        // 退出登录
        if (isLogoutRequest(req.getRequestURI())) {
            // 清除redis jwtToken
            removeJwtTokenFromRedis(req);
            return null;
        }

        try {
            jwtToken = getJwtTokenFromRedis(req);
            if (StringUtils.isEmpty(jwtToken)) {
                ResponseEntity<BaseResponseDTO> responseEntity = null;
                responseEntity = this.restTemplate.exchange(this.gatewayHelperProperties.getUserInfoUri(), HttpMethod.GET, entity, BaseResponseDTO.class, new Object[0]);
                if (null != responseEntity && responseEntity.getStatusCode().is2xxSuccessful()) {
                    BaseResponseDTO responseDTO = responseEntity.getBody();
                    if (!"200".equals(responseDTO.getCode())) {
                        LOGGER.info("get access_token from cgic-oauth failure, response code :{}", responseDTO.getCode());
                        setGatewayHelperFailureResponse(ctx, 401);
                        return null;
                    }
                    UserDetailImpl userDetail = this.extractUserDetail((Map) responseDTO.getRows().get(0));
                    jwtToken = extractJwtToken(userDetail);
                    setJwtTokenForRedis(req, jwtToken);
                }
            }
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.addZuulRequestHeader(JWT_TOKEN, jwtToken);
        } catch (Exception e) {
            LOGGER.error("get jwt_token failure: {}", e.getLocalizedMessage());
            setGatewayHelperFailureResponse(ctx, 401);
        }
        return null;
    }


    /**
     * 处理跨域问题
     *
     * @param req
     * @param resp
     */
    private void processAccessControl(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
        resp.setHeader("Access-Control-Expose-Headers", "X-forwared-port, X-forwarded-host");
        resp.setHeader("Vary", "Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
    }


    /**
     * 构建 HttpEntity
     *
     * @param req
     * @return
     */
    private HttpEntity getHttpEntity(HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        String token = req.getHeader(HEADER_AUTH);
        headers.set(HEADER_AUTH, token);
        return new HttpEntity("", headers);
    }

    /**
     * 构建 UserDetailImpl
     *
     * @param map
     * @return
     */
    private UserDetailImpl extractUserDetail(Map<String, Object> map) {
        if (map.containsKey("userId")) {
            UserDetailImpl userDetail = new UserDetailImpl((String) map.get(USER_NAME), NO_LOGIN_PASSWORD, Collections.emptyList());
            if (map.get(USER_ID) != null) {
                userDetail.setUserId((long) ((Integer) map.get(USER_ID)).intValue());
                userDetail.setDisplayName((String) map.get(DISPLAY_NAME));
                userDetail.setEmployeeId(Long.valueOf(map.get(EMPLOYEE_ID).toString()));
                if (map.get(EMAIL) != null) {
                    userDetail.setEmail((String) map.get(EMAIL));
                }
                if (map.get(PHONE) != null) {
                    userDetail.setPhone((String) map.get(PHONE));
                }
                List roleIdList;
                if (map.get(ROLE_IDS) != null) {
                    Object roleIds = map.get(ROLE_IDS);
                    if (roleIds instanceof List) {
                        roleIdList = (List) roleIds;
                        userDetail.setRoleIds((List) roleIdList.stream().map((item) -> {
                            return Long.valueOf(String.valueOf(item));
                        }).collect(Collectors.toList()));
                    }
                }
            }
            return userDetail;
        }
        return null;
    }

    /**
     * userDetails导出JwtToken
     *
     * @param userDetail
     * @return
     * @throws ExtractJwtTokenException
     */
    private String extractJwtToken(UserDetailImpl userDetail) throws ExtractJwtTokenException {
        try {
            String token = this.objectMapper.writeValueAsString(userDetail);
            String jwt = PREFIX_AUTH + JwtHelper.encode(token, this.jwtSigner).getEncoded();
            return jwt;
        } catch (Exception e) {
            throw new ExtractJwtTokenException("400001", "导出jwt_token异常...");
        }
    }


    /**
     * 设置出错响应
     *
     * @param ctx
     */
    private void setGatewayHelperFailureResponse(RequestContext ctx, int statusCode) {
        ctx.setResponseStatusCode(statusCode);
        ctx.setSendZuulResponse(false);
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setSuccess(false);
        baseResponseDTO.setCode(String.valueOf(statusCode));
        if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            baseResponseDTO.setMessage("unauthorized");
            LOGGER.warn("gateway-helper response unauthorized, {}", statusCode);
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            baseResponseDTO.setMessage("forbidden");
            LOGGER.warn("gateway-helper response forbidden, {}", statusCode);
        } else {
            baseResponseDTO.setMessage("error");
            LOGGER.warn("gateway-helper response error, {}", statusCode);
        }
        try {
            ctx.setResponseBody(objectMapper.writeValueAsString(baseResponseDTO));
        } catch (JsonProcessingException e) {
            LOGGER.error("Response Data  To Json Processing Exception : ", e);
        }
    }


    /**
     * 从请求体头中获取 access_token
     *
     * @param req
     * @return
     */
    private String getAccessTokenFromRequest(HttpServletRequest req) {
        return req.getHeader(HEADER_AUTH);
    }

    /**
     * 判断是否是 退出登录
     *
     * @param requestURI
     * @return
     */
    private boolean isLogoutRequest(String requestURI) {
        return this.matcher.match(this.gatewayHelperProperties.getLogoutPath(), requestURI);
    }


    /**
     * redis 读取 jwtToken
     *
     * @param req
     * @return
     */
    public String getJwtTokenFromRedis(HttpServletRequest req) {
        byte[] key = this.serializeKey(JWT_TOKEN_KEY + processJwtToken(req.getHeader(HEADER_AUTH)));
        RedisConnection conn = this.getConnection();

        byte[] bytes;
        try {
            bytes = conn.get(key);
        } finally {
            conn.close();
        }
        return this.deserializeJwtToken(bytes);
    }

    /**
     * redis 设置 jwt_token
     *
     * @param req
     * @param jwtToken
     */
    private void setJwtTokenForRedis(HttpServletRequest req, String jwtToken) {
        byte[] authJwtTokenKey = this.serializeKey(JWT_TOKEN_KEY + processJwtToken(req.getHeader(HEADER_AUTH)));
        byte[] authJwtTokenValue = this.serialize((Object) jwtToken);
        RedisConnection conn = this.getConnection();
        try {
            conn.openPipeline();
            conn.set(authJwtTokenKey, authJwtTokenValue);
            conn.expire(authJwtTokenKey, (long) jwtTokenTime);
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    /**
     * redis 删除 jwt_token
     *
     * @param req
     */
    private void removeJwtTokenFromRedis(HttpServletRequest req) {
        byte[] key = this.serializeKey(JWT_TOKEN_KEY + processJwtToken(req.getHeader(HEADER_AUTH)));
        RedisConnection conn = this.getConnection();

        byte[] bytes;
        try {
            conn.del(key);
        } finally {
            conn.close();
        }
    }

    private String processJwtToken(String jwtTokenKey) {
        return jwtTokenKey.replace(PREFIX_AUTH, "");
    }

    private byte[] serializeKey(String s) {
        return this.serializationStrategy.serialize(s);
    }

    private String deserializeJwtToken(byte[] bytes) {
        return (String) this.serializationStrategy.deserialize(bytes, String.class);
    }

    private byte[] serialize(Object object) {
        return this.serializationStrategy.serialize(object);
    }


}
