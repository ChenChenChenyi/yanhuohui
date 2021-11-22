package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.provider.HelloProvider;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloProvider {
    @Override
    public BaseResponse helloWorld(String name) {
        System.out.println("Hello " + name + "!");
        return BaseResponse.SUCCESSFUL();
    }
}
