package com.chenyi.yanhuohui.provider;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface HelloProvider {

    @PostMapping("/hello")
    BaseResponse helloWorld(@RequestParam("name") String name);
}
