package org.cgic.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.cgic.commons.dto.BaseResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/4/17 17:13
 * @Version 1.0
 */
@Component
public class CgicOauthLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final String HEADER_AUTH = "Authorization";
    private static final String PREFIX_AUTH = "Bearer ";
    private static final String CONTENT_TYPE_STR = "application/json;charset=utf-8";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String accessToken = httpServletRequest.getHeader(HEADER_AUTH);
        if (StringUtils.isNotBlank(accessToken)) {
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken.replace(PREFIX_AUTH, ""));
            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessToken(oAuth2AccessToken);
//                OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
//                tokenStore.removeRefreshToken(oAuth2RefreshToken);
//                tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
            }
        }
        httpServletResponse.setContentType(CONTENT_TYPE_STR);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(objectMapper.writeValueAsString(new BaseResponseDTO()));
        out.flush();
        out.close();
    }
}
