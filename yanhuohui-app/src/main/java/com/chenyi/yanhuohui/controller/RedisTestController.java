package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/getString")
    @Cacheable(value = "springbootredis",key = "#root.methodName")
    public String getString(){
        String str="hello redis";
        System.out.println("没有读取缓存，进入方法返回的值");
        return str;
    }

    @RequestMapping("/setValue")
    public String setValue(){
        redisTemplate.opsForValue().set("myName","chenyi");
        stringRedisTemplate.opsForValue().set("yourName","lisi");
        redisUtil.set("chenyi","dianshanpingtai");
        return "这里分别用redisTemplate和stringRedisTemplate设置了两个键值，方便后面验证一个问题";
    }

    @RequestMapping("/getValue")
    public String getValue(){
        String str1= (String) redisTemplate.opsForValue().get("myName");
        // String str2= (String) redisTemplate.opsForValue().get("yourName");
        String str3=stringRedisTemplate.opsForValue().get("myName");
        String str4=stringRedisTemplate.opsForValue().get("yourName");
        return "验证取值的问题，str1="+str1+"===,str2="+"  "+"===,str3="+str3+"===,str4="+str4;
    }
}
