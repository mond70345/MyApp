package com.mchen2.myapp;

import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableSpringUtil
@EnableFeignClients(basePackages = "com.mchen2")
@ComponentScan(basePackages = {"com.mchen2"})
@EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy = true)
public class Application {

    static {
        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static void main(String[] args) {
        try {
            ApplicationContext appContext = SpringApplication.run(Application.class, args);
            log.info(appContext.getEnvironment().getProperty("spring.jackson.date-format"));
        } catch (Throwable e) {
            log.error("fail to start application.", e);
        }
    }

}
