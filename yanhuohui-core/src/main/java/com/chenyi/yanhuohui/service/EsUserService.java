package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.esUser.EsUser;
import com.chenyi.yanhuohui.esUser.EsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsUserService {

    @Autowired
    private EsUserRepository esUserRepository;

    public void saveNewEsUser(EsUser esUser){
        esUserRepository.save(esUser);
    }
}
