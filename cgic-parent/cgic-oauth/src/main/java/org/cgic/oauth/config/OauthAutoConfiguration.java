package org.cgic.oauth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Description  CGIC-oauth 自动配置类
 * @Author: charleyZZZZ
 * @Date: 2019/11/6 15:22
 * @Version 1.0
 */
@EnableEurekaClient
@EnableResourceServer
@MapperScan({"org.cgic.oauth.mapper"})
@ComponentScan({"org.cgic.oauth.config",
        "org.cgic.commons.config",
        "org.cgic.commons.handler",})
public class OauthAutoConfiguration {

    public OauthAutoConfiguration() {
    }

    /*这里可以写一些@bean**/
    /**
     * 配置AccessToken的存储方式：此处使用Redis存储
     * Token的可选存储方式
     * 1、InMemoryTokenStore
     * 2、JdbcTokenStore
     * 3、JwtTokenStore
     * 4、RedisTokenStore
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }




}
