package com.chenyi.yanhuohui.util.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedissionLockUtil {
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加锁
     * @param lockname 名称
     */
    public Boolean lock(String lockname){
        try {
            if(null == redissonClient){
                log.error("RedissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockname);
            lock.lock(30, TimeUnit.SECONDS); //30秒后释放锁，防止死锁
            log.info("Thread [{}] lock [{}] success",Thread.currentThread().getName(),lockname);
            return true;
        } catch (Exception e) {
            log.info("lock [{}] exception",lockname,e);
            return false;
        }
    }

    /**
     * 释放锁
     * @param lockname 名称
     */
    public Boolean unlock(String lockname){
        try {
            if(null == redissonClient){
                log.error("RedissonClient is null");
                return false;
            }
            RLock lock = redissonClient.getLock(lockname);
            lock.unlock();
            log.info("Thread [{}] unlock [{}] success",Thread.currentThread().getName(),lockname);
            return true;
        } catch (Exception e) {
            log.info("unlock [{}] exception",lockname,e);
            return false;
        }
    }
}
