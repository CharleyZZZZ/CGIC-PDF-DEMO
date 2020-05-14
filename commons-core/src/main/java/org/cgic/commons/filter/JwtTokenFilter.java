package org.cgic.commons.filter;

import org.cgic.commons.config.CgicCommonsProperties;
import org.cgic.commons.oauth.JwtTokenExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description Jwt_token 过滤器
 * @Author: charleyZZZZ
 * @Date: 2019/10/21 16:30
 * @Version 1.0
 */
public class JwtTokenFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private CgicCommonsProperties cgicCommonsProperties;

    private JwtTokenExtractor jwtTokenExtractor;
    private ResourceServerTokenServices tokenServices;

    public JwtTokenFilter() {
    }

    public JwtTokenFilter(JwtTokenExtractor jwtTokenExtractor, ResourceServerTokenServices tokenServices) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.tokenServices = tokenServices;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean jwtTokenFilter = cgicCommonsProperties.isJwtTokenFilter();
        if (!jwtTokenFilter) {
            chain.doFilter(request, response);
        }else {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            Authentication authentication = this.jwtTokenExtractor.extract(req);
            if (null == authentication) {
                if (this.isAuthenticated()) {
                    LOGGER.debug("Clearing security context.");
                    SecurityContextHolder.clearContext();
                }
                LOGGER.debug("No Jwt token in request, will continue chain.");
                ((HttpServletResponse) response).sendError(401, "No Jwt token in request.");
                return;
            }
            request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, authentication.getPrincipal());
            if (authentication instanceof AbstractAuthenticationToken) {
                AbstractAuthenticationToken needsDetails = (AbstractAuthenticationToken) authentication;
                needsDetails.setDetails(new OAuth2AuthenticationDetails(req));
            }

            Authentication authResult = this.authenticate(authentication);
            LOGGER.debug("Authentication success: {}", authResult);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {

    }

    protected Authentication authenticate(Authentication authentication) {
        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        } else {
            String token = (String) authentication.getPrincipal();
            OAuth2Authentication auth = this.tokenServices.loadAuthentication(token);
            if (auth == null) {
                throw new InvalidTokenException("Invalid token: " + token);
            } else {
                if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                    if (!details.equals(auth.getDetails())) {
                        details.setDecodedDetails(auth.getDetails());
                    }
                }
                auth.setDetails(authentication.getDetails());
                auth.setAuthenticated(true);
                return auth;
            }
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
