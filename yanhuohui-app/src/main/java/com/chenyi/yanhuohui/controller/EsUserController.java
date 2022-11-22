package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.esUser.EsUser;
import com.chenyi.yanhuohui.request.EsUserSaveRequest;
import com.chenyi.yanhuohui.service.EsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/esuser")
@Slf4j
public class EsUserController {
    @Autowired
    private EsUserService esUserService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping(value = "/save-new-user")
    public void saveNewUser(@RequestBody EsUserSaveRequest esUserSaveRequest){
        EsUser esUser = new EsUser();
        esUser.setUsername(esUserSaveRequest.getName());
        esUser.setMyId("AD87314");
        esUser.setRealname(esUserSaveRequest.getName());
        esUser.setPassword("123456");
        esUser.setLevel(2);
        LocalDateTime localDateTime = LocalDateTime.of(1995,1,10,6,35,1);
        esUser.setAge(esUserSaveRequest.getAge());
        esUser.setBirth(localDateTime);
        if(!elasticsearchTemplate.indexExists(EsUser.class)){
            elasticsearchTemplate.createIndex(EsUser.class);
            elasticsearchTemplate.putMapping(EsUser.class);
        }
        esUserService.saveNewEsUser(esUser);
    }
}
