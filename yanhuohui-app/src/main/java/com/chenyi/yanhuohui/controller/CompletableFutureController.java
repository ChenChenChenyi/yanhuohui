package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.service.ThreadPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class CompletableFutureController {


    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @PostMapping("/threadpool/completableFuture1")
    public List<String> callDoSomeThingAsync() {

        List<String> strList = Arrays.asList("dd","ff");
        //复杂一点的情况，将strList拆成n个字符串列表List<List<String>> strLstList
        List<List<String>> strLstList = splitList(strList,10);

        // 分成n个循环调用异步函数
        CompletableFuture[] completableFutureArr = new CompletableFuture[strLstList.size()];

        strLstList.forEach(strLst -> {
            CompletableFuture<List<String>> strLstFuture = threadPoolService.doSomeThingAsync(strLst);
            // 获取返回结果
            completableFutureArr[strLstList.indexOf(strLst)] = strLstFuture;
        });

        //join() 的作用：让“主线程”等待“子线程”结束之后才能继续运行
        CompletableFuture.allOf(completableFutureArr).join();
        return strList;
    }

    @PostMapping("/threadpool/completableFuture2")
    public void callDoSomeThingAsync2() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("即将拨打10086");
            return 10086;
        },taskExecutor);
        future.whenComplete((result, error) -> {
            log.info("拨打"+result);
            error.printStackTrace();
        });
    }

    private List<List<String>> splitList(List<String> messagesList, int groupSize) {
        int length = messagesList.size();
        // 计算可以分成多少组
        int num = (length + groupSize - 1) / groupSize;
        List<List<String>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = Math.min((i + 1) * groupSize, length);
            newList.add(messagesList.subList(fromIndex, toIndex));
        }
        return newList;
    }

}
