package com.chenyi.yanhuohui.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ThreadPoolService {

    @Async("taskExecutor")
    public Future<String> Test1(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        log.info(Thread.currentThread().getName());
        log.info("异步任务里面的输出：{}。",name);
        return new AsyncResult<>("success");
    }
}