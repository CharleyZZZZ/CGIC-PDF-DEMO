package org.cgic.platform.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/11/6 16:13
 * @Version 1.0
 */
@EnableEurekaClient
//@EnableResourceServer
@Configuration
@MapperScan({"org.cgic.platform.menu.mapper"})
@ComponentScan({"org.cgic.commons.config",
        "org.cgic.platform.config"})
public class PlatformAutoConfiguration {

    public PlatformAutoConfiguration() {
    }


}
