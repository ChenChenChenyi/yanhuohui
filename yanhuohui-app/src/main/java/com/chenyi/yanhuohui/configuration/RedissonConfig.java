package com.chenyi.yanhuohui.configuration;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissonConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient;
        Config config = new Config();
        String url = "redis://" + redisProperties.getHost()
                + ":" + redisProperties.getPort();
        config.useSingleServer().setAddress(url) //单机
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());
        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        //集群
        //config.useClusterServers().addNodeAddress(new String[]{"",""}).setPassword("");
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            log.error("RedissonClient init redis url :[{}]",url,e.getMessage());
            return null;
        }
    }
}
