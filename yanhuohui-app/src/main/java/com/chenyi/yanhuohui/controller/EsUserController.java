package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.esUser.EsUser;
import com.chenyi.yanhuohui.service.EsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chenyi.yanhuohui.request.EsUserSaveRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/esuser")
@Slf4j
public class EsUserController {
    @Autowired
    private EsUserService esUserService;

    @RequestMapping(value = "/save-new-user")
    public void saveNewUser(@RequestBody EsUserSaveRequest esUserSaveRequest){
        EsUser esUser = new EsUser();
        esUser.setUsername(esUserSaveRequest.getName());
        esUser.setRealname(esUserSaveRequest.getName());
        esUser.setPassword("123456");
        esUser.setBirth(LocalDateTime.now());
        esUser.setAge(esUserSaveRequest.getAge());
        esUserService.saveNewEsUser(esUser);
    }
}
