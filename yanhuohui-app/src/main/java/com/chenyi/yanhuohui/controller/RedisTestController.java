package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 接口缓存一般写在service上，这里写在controller只是示例
     */
    @RequestMapping("/getString")
    @Cacheable(cacheManager = "redisCacheManager", value = "springbootredis",key = "#root.methodName")
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
        String str1= (String) redisTemplate.opsForValue().get("myName");//返回的是chenyi
        // String str2= (String) redisTemplate.opsForValue().get("yourName");
        String str3=stringRedisTemplate.opsForValue().get("myName");  //返回的是"chenyi"
        String str4=stringRedisTemplate.opsForValue().get("yourName");
        log.info(str1+"\n");
        log.info(str3);
        return "验证取值的问题，str1="+str1+"===,str2="+"  "+"===,str3="+str3+"===,str4="+str4;
    }

    /**
     这个是查询接口，cacheManager中的缓存设置了失效时间，并通过CacheLoader设置失效后的返回值，避免缓存击穿；
     还需要配合如@CachePut等注解的修改接口来更新该缓存的值，CacheLoader是这样使用的。
     */
    @RequestMapping("/getCaffeineValue")
    @Cacheable(cacheNames = "outLimit",key = "#name",sync = true,cacheManager = "caffeineCacheManager")
    public String getCaffeineServiceTest(@RequestParam String name, @RequestParam Integer age){
        String value = name + " nihao "+ age;
        log.info("getCaffeineServiceTest value = {}",value);
        return value;
    }
}
