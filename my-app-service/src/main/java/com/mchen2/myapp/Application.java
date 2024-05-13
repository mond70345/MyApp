package com.mchen2.myapp;

import cn.hutool.extra.spring.EnableSpringUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.mchen2"})
public class Application {
    public static void main(String[] args) {
        try {
            log.info("starting application...");
            ApplicationContext appContext = SpringApplication.run(Application.class, args);
            log.info("application started.");
        } catch (Throwable e) {
            log.error("fail to start application.", e);
        }
    }

}
