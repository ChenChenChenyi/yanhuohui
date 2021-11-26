package com.chenyi.yanhuohui.provider;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.User;

import javax.validation.Valid;

@RequestMapping(value = "/paramValid")
public interface ParamValidProvider {

    @PostMapping("/addUser")
    BaseResponse addUser(@RequestBody @Valid User user);

    @GetMapping("/getUser")
    BaseResponse<User> getUser();

    @GetMapping("/getUserByAdvice")
    User getUserByAdvice();

    @GetMapping("/getUserRes")
    ResponseEntity<User> getUserRes();
}
