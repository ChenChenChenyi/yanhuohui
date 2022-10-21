package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/jpa")
//@AllArgsConstructor
public class JpaController {

    @Autowired
    private ManagerRepository managerRepository;

//    @Autowired
//    void setManagerRepository(ManagerRepository managerRepository) {
//        this.managerRepository = managerRepository;
//    }

    @PostMapping(value = "/find-all")
    public String findAll(){
        List<Manager> list = managerRepository.findAll();
        System.out.println(list);
        return list.toString();
        //return BaseResponse.SUCCESSFUL();
    }
}
