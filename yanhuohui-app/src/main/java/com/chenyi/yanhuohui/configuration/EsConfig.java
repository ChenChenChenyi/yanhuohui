package com.chenyi.yanhuohui.configuration;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class EsConfig {
    /**
     * 这是应为data-elasticsearch中与Netty之间产生冲突
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
