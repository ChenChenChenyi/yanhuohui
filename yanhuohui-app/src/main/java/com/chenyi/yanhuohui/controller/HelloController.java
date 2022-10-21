package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.annotation.PermissionsAnnotation;
import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.provider.HelloProvider;
import com.chenyi.yanhuohui.service.ThreadPoolService;
import com.chenyi.yanhuohui.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@Slf4j
public class HelloController implements HelloProvider {

    @Autowired
    @Qualifier(value = "taskExecutor")
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private UserService userService;

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
                //调用的函数和异步函数不能处于同一个实现类中
                //调用的函数和异步函数不能处于同一个实现类中
                //调用的函数和异步函数不能处于同一个实现类中
                //调用的函数和异步函数不能处于同一个实现类中
                //调用的函数和异步函数不能处于同一个实现类中
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

    @Override
    public BaseResponse myprint(String name){
        userService.print(name,s->log.info("函数式接口打印名字"+name));
        return BaseResponse.SUCCESSFUL();
    }
}
