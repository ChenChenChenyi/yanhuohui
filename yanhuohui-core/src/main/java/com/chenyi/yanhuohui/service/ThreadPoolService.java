package com.chenyi.yanhuohui.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ThreadPoolService {

    @Async("taskExecutor")
    public Future<String> Test1(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info(Thread.currentThread().getName());
        log.info("异步任务test1里面的输出：{}。",name);
        return new AsyncResult<>("success");
    }

    @Async("taskExecutor")
    public Future<String> Test2(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info(Thread.currentThread().getName());
        log.info("异步任务test2里面的输出：{}。",name);
        return new AsyncResult<>("success");
    }

    @Async("taskExecutor")
    public CompletableFuture<List<String>> doSomeThingAsync(List<String> strList) {
        log.info("doSomeThingAsync");

        // do some thing ...

        return CompletableFuture.completedFuture(strList);
    }
}
