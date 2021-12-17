package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.service.JpaTransactionService;
import com.chenyi.yanhuohui.service.JpaTransactionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BandCombineOp;
import java.util.List;

@RestController
@RequestMapping(value = "/jpaTransaction")
public class JpaTransactionController {

    @Autowired
    private JpaTransactionService jpaTransactionService;
    @Autowired
    private JpaTransactionService2 jpaTransactionService2;

    @RequestMapping(value = "/saveManager/{name}")
    public BaseResponse saveManager(@PathVariable String name){
        Long id = jpaTransactionService.saveNewManager(name);
        return BaseResponse.success(id);
    }

    @RequestMapping(value = "/test-required")
    public BaseResponse testRequired(){
        jpaTransactionService2.call();
        return BaseResponse.SUCCESSFUL();
    }
}
