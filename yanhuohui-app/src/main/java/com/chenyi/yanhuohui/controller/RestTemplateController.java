package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.manager.Manager;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public class RestTemplateController {
    /**
     * 使用https访问接口
     */
    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    /**
     * 使用https访问接口,并忽略ssl证书验证
     */
    @Resource(name = "restTemplateIgnoreSSL")
    private RestTemplate restTemplateIgnoreSSL;

    @RequestMapping("/getUserInfo")
    public Manager getUserInfo() {
        Manager data = restTemplate.getForObject("https://localhost:9090/ssl-service/getUserInfo", Manager.class);
        return data;
    }

    @RequestMapping("/getUserInfoIgnoreSSL")
    public Manager getUserInfoIgnoreSSL() {
        Manager data = restTemplateIgnoreSSL.getForObject("https://localhost:9090/ssl-service/getUserInfo", Manager.class);
        return data;
    }
}
