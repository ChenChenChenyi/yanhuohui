package com.chenyi.yanhuohui;

import com.chenyi.yanhuohui.common.base.starter.SpringContextHolder;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class YanhuohuiApplication {
    public static void main(String[] args) {
        SpringApplication.run(YanhuohuiApplication.class, args);
    }
}
