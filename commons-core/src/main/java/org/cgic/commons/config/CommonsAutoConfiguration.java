package org.cgic.commons.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cgic.commons.filter.JwtTokenFilter;
import org.cgic.commons.oauth.CustomTokenConverter;
import org.cgic.commons.oauth.JwtTokenExtractor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/21 16:37
 * @Version 1.0
 */
@Configuration
@MapperScan({"org.cgic.commons.mapper"})
@ComponentScan({"org.cgic.commons.service", "org.cgic.commons.oauth"})
public class CommonsAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonsAutoConfiguration.class);

    private static final String key = "cgicjwt";

    public CommonsAutoConfiguration() {
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new JwtTokenFilter());
        bean.setFilter(new DelegatingFilterProxy("jwtTokenFilter"));
        bean.addUrlPatterns("/*");
        bean.setName("jwtTokenFilter");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JwtTokenExtractor jwtTokenExtractor() {
        return new JwtTokenExtractor();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenExtractor(), this.tokenServices());
    }

    @Bean
    public Signer jwtSigner(CgicCommonsProperties cgicCommonsProperties) {
        return new MacSigner(cgicCommonsProperties.getJwtKey());
    }

    private DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(this.tokenStore());
        return defaultTokenServices;
    }

    private TokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    private JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(new CustomTokenConverter());
        converter.setSigningKey(this.key);

        try {
            converter.afterPropertiesSet();
        } catch (Exception e) {
            LOGGER.error("key: {} after properties set failure", this.key, e);
        }

        return converter;
    }


}
