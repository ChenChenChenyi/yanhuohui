package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.CommonErrorCode;
import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.PorkInst;
import com.chenyi.yanhuohui.service.UserService;
import com.chenyi.yanhuohui.service.porktestservice.PorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 这个controller的业务是为了模拟使用链路思想来构建单元测试。
 */
@RestController
@RequestMapping("/unit-test")
public class UnitTestController {

    @Autowired
    private UserService userService;

    public void print(){

    }
}
