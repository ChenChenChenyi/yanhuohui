package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.util.redis.RedisUtil;
import com.chenyi.yanhuohui.util.redisson.RedissionLockUtil;
import com.chenyi.yanhuohui.util.redisson.ResissonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis-lock")
@Slf4j
public class RedisLockController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedissionLockUtil lockUtil;
    @Autowired
    private ResissonUtil resissonUtil;

    @GetMapping("/test")
    public void testLock() throws IOException {

        resissonUtil.getRedissonConfig();

        for (int i = 0;i < 5;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean lockStatus = lockUtil.lock("LOCK");
                }
            }).start();
        }
    }

    @RequestMapping("/deduct-stock")
    public String deductStock() throws Exception{
        RLock lock = redissonClient.getFairLock("stock_lock");
        try {
            //lock.lock(10L,TimeUnit.SECONDS);//加时间会导致续期失效
            boolean result = lock.tryLock();
            if(!result){
                return "正在运行中，请稍后重试！";
            }
            int stock = Integer.parseInt(redisUtil.get("stock").toString()) ;
            log.info("开始睡眠，赶紧停掉服务！！！！");
            TimeUnit.SECONDS.sleep(60);
            if(stock>0){
                int realStock = stock - 1;
                redisUtil.set("stock",realStock+"");
                log.info("扣减库存成功，剩余库存："+realStock+"。");
            }else {
                log.info("扣减库存失败，库存不足！");
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock.isHeldByCurrentThread()){
                log.info("锁释放。");
                lock.unlock();
            }
        }
        return "end";
    }
}
