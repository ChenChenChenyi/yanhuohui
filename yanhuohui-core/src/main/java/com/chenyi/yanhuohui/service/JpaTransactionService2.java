package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class JpaTransactionService2 {
    @Autowired
    private JpaTransactionService jpaTransactionService;
    @Autowired
    private ManagerRepository managerRepository;

    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public void call(){
        try {
            managerRepository.save(Manager.builder().name("chenchen").build()).getId();
            jpaTransactionService.call();
            //throw new SbcRuntimeException("自己抛出的错误。");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
