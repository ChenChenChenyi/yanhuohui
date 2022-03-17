package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.annotation.PermissionsAnnotation;
import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.provider.HelloProvider;
import com.chenyi.yanhuohui.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController implements HelloProvider {

    @Autowired
    @Qualifier(value = "taskExecutor")
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Override
    @PermissionsAnnotation("super")
    public BaseResponse<Object> helloWorld(@RequestParam(value = "name") String name) throws InterruptedException {
//        if("chenyi".equals(name)){
//            throw new SbcRuntimeException(CommonErrorCode.FAILED,"我自己抛出的错误");
//        }
        System.out.println("Hello " + name + "!");
        Class<Manager> clazz = Manager.class;
        List<Field> list = new ArrayList<>();
        list.addAll(Arrays.asList(clazz.getDeclaredFields()));

        poolTaskExecutor.execute(()->{
            try {
                Future<String> result = threadPoolService.Test1(name);
                result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        //throw new SbcRuntimeException(CommonErrorCode.FAILED,"我自己抛出的错误");
        Map<Integer, Integer> numsMap = new HashMap<>();
        return BaseResponse.SUCCESSFUL();
    }
}
