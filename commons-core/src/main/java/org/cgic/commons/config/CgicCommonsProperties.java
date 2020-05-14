package org.cgic.commons.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/21 16:31
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "cgic.commons")
public class CgicCommonsProperties {

    private boolean jwtTokenFilter = false;

    private String jwtKey = "cgicjwt";

    public CgicCommonsProperties() {
    }

    public String getJwtKey() {
        return jwtKey;
    }

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public boolean isJwtTokenFilter() {
        return jwtTokenFilter;
    }

    public void setJwtTokenFilter(boolean jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }
}
