package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.common.base.entity.CommonErrorCode;
import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.provider.HelloProvider;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class HelloController implements HelloProvider {
    @Override
    public BaseResponse helloWorld(String name) throws InterruptedException {
//        if("chenyi".equals(name)){
//            throw new SbcRuntimeException(CommonErrorCode.FAILED,"我自己抛出的错误");
//        }
        Thread.sleep(3000);
        System.out.println("Hello " + name + "!");
        //Consumer
        //throw new SbcRuntimeException(CommonErrorCode.FAILED,"我自己抛出的错误");
        return BaseResponse.SUCCESSFUL();
    }
}
