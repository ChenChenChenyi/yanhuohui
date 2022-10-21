package com.chenyi.yanhuohui.provider;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("yanhuohui")
public interface HelloProvider {

    @PostMapping("/hello")
    BaseResponse helloWorld(String name) throws InterruptedException;

    @PostMapping("/print")
    BaseResponse myprint(String name);
}
