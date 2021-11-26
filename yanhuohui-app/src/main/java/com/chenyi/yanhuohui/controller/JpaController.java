package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/jpa")
public class JpaController {

    @Autowired
    private ManagerRepository managerRepository;

    @PostMapping(value = "/find-all")
    public BaseResponse findAll(){
        List<Manager> list = managerRepository.findAll();
        System.out.println(list);
        return BaseResponse.SUCCESSFUL();
    }
}
