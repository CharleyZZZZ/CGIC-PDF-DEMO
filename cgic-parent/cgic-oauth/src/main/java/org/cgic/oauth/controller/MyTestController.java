package org.cgic.oauth.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cgic.commons.dto.BaseResponseDTO;
import org.cgic.commons.service.ISysUserService;
import org.cgic.commons.web.BaseController;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/12 17:15
 * @Version 1.0
 */
@Controller
@RequestMapping("/test")
public class MyTestController extends BaseController {


    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisConnectionFactory connectionFactory;


    @GetMapping("/user/{username}")
    @ResponseBody
    public BaseResponseDTO getUserInfo(@PathVariable(value = "username", required = true) String username) {
        return new BaseResponseDTO(sysUserService.selectUserByUserName(username));
    }


    @GetMapping("/user/info")
    @ResponseBody
    public BaseResponseDTO getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return new BaseResponseDTO(principal);
    }


    @ApiOperation(value = "获取access存储内容", httpMethod = "GET")
    @GetMapping("/deserialize/access")
    @ResponseBody
    public BaseResponseDTO deserializeAccessToken(
            @ApiParam("key") @NotBlank(message = "key不能为空") @RequestParam("key") String key
    ) {
        RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();
        byte[] serializedKey = serializationStrategy.serialize(key);
        RedisConnection conn = connectionFactory.getConnection();
        byte[] bytes;
        try {
            bytes = conn.get(serializedKey);
        } finally {
            conn.close();
        }
        OAuth2AccessToken content = serializationStrategy.deserialize(bytes, OAuth2AccessToken.class);
        return new BaseResponseDTO(content);
    }

}
