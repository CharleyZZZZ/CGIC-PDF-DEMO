package org.cgic.oauth.config;

import org.cgic.oauth.handler.CgicOauthLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/4/17 17:11
 * @Version 1.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private CgicOauthLogoutSuccessHandler logoutSuccessHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 禁止 CSRF
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/test/**", "/signup", "/about").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("authorize-server");
    }
}
