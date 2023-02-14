package com.chenyi.yanhuohui;

import com.chenyi.yanhuohui.common.base.starter.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@EnableCaching
@Slf4j
@ServletComponentScan
public class YanhuohuiApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(YanhuohuiApplication.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        //String[] beanNames = ctx.getBeanNamesForAnnotation(RestController.class);//所有添加该注解的bean
//        log.info("bean总数:{}", ctx.getBeanDefinitionCount());
//        int i = 0;
//        for (String str : beanNames) {
//            log.info("{},beanName:{}", ++i, str);
//        }
    }
}
