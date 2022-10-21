package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.util.redis.RedissonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/redissen")
public class RedissenController {

    @Autowired
    private RedissonUtil redissonUtil;
    @Autowired
    private RedissonClient redissonClient;

    //Redisson的          分布式集合        的一些操作
    @RequestMapping("/getString")
    public void getString(){
        redissonUtil.setStr("redisson_test_key1","chenchenchen");

        redissonUtil.setStr("redisson_test_key2", "myBucketIsCy");
        RBuckets buckets = redissonUtil.getBuckets();
        Map<String, String> foundBuckets = buckets.get("redisson_test_key*");
        Map<String, Object> map = new HashMap<>();
        map.put("redisson_test_key3", "huyaoyao");
        map.put("redisson_test_key4", 30L);
        // 同时保存全部通用对象桶。
        buckets.set(map);
        Map<String, String> loadedBuckets = buckets.get("redisson_test_key1", "redisson_test_key2");
        log.info("跨桶String 测试数据：{}", loadedBuckets);

        //哈希
        // 通过key获取
        RMap<String, String> studentRMap = redissonClient.getMap("redisson_test_studentMap");
        // 设置map中key-value
        studentRMap.put("id", "123");studentRMap.put("name", "赵四");studentRMap.put("age", "50");
        // 设置key有效期
        studentRMap.expire(300, TimeUnit.SECONDS);
        // 通过key获取value
        String mapName = (String) redissonClient.getMap("redisson_test_studentMap").get("name");
        log.info("哈希Map中取值 测试数据：{}", mapName);

        //列表
        RList<String> studentRList = redissonClient.getList("redisson_test_studentList");
        studentRList.add("zhudekun");studentRList.add("wujiangtao");
        // 设置有效期
        studentRList.expire(300, TimeUnit.SECONDS);
        // 通过key获取value
        redissonClient.getList("studentList").get(0);

        //集合
        RSet<String> studentRSet = redissonClient.getSet("redisson_test_studentSet");
        studentRSet.add("chenkaiyu");studentRSet.add("xuyin");
        // 设置有效期
        studentRSet.expire(300, TimeUnit.SECONDS);
        // 通过key获取value
        redissonClient.getSet("studentSet");

        //有序集合
        RSortedSet<String> studentSortedSet = redissonClient.getSortedSet("redisson_test_studentSortedSet");
        studentSortedSet.add("jack");studentSortedSet.add("tom");
        redissonClient.getSortedSet("studentSortedSet");

        //布隆过滤器
        RBloomFilter seqIdBloomFilter = redissonClient.getBloomFilter("seqId");
        // 初始化预期插入的数据量为10000000和期望误差率为0.01
        seqIdBloomFilter.tryInit(10000000, 0.01);
        // 插入部分数据
        seqIdBloomFilter.add("123");seqIdBloomFilter.add("456");seqIdBloomFilter.add("789");
        // 判断是否存在
        System.out.println(seqIdBloomFilter.contains("123"));
        System.out.println(seqIdBloomFilter.contains("789"));
        System.out.println(seqIdBloomFilter.contains("100"));

        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("redisson_test_Atomickey");
        rAtomicLong.set(1223L);
        rAtomicLong.expire(300,TimeUnit.SECONDS);


    }
}
