package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
//@Transactional(readOnly = true)
public class JpaTransactionService {
    @Autowired
    private ManagerRepository managerRepository;

    public Long saveNewManager(String name){
        List<Manager> list = managerRepository.findAll();
        System.out.println(list);
        //添加了@Transactional注解之后update和add操作是执行不了的
        return managerRepository.save(Manager.builder().name(name).build()).getId();
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public void call(){
        log.info("被调用方执行数据库操作。");
        managerRepository.save(Manager.builder().name("yiyi").build()).getId();
        //throw new SbcRuntimeException("被调用方抛出的错误。");
    }
}
