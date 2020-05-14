package org.cgic.commons.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description  Jwt_token 导出器
 * @Author: charleyZZZZ
 * @Date: 2019/10/25 16:45
 * @Version 1.0
 */
//@Service
public class JwtTokenExtractor implements TokenExtractor {


    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenExtractor.class);

    public JwtTokenExtractor() {
    }

    @Override
    public Authentication extract(HttpServletRequest httpServletRequest) {
        String token = this.extractToken(httpServletRequest);
        return token != null? new PreAuthenticatedAuthenticationToken(token,""):null;
    }

    private String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null) {
            LOGGER.debug("Token not found in headers. Trying request parameters.");
        }
        return token;
    }

    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders("Jwt_Token");
        while(headers.hasMoreElements()) {
            String value = (String)headers.nextElement();
            if (value.toLowerCase().startsWith("Bearer".toLowerCase())) {
                String authHeaderValue = value.substring("Bearer".length()).trim();
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, value.substring(0, "Bearer".length()).trim());
                // 逗号
                int commaIndex = authHeaderValue.indexOf(44);
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
