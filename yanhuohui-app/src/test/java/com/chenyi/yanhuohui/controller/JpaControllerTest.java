package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class JpaControllerTest {

    @Autowired
    private ManagerRepository managerRepository;

    @InjectMocks
    private JpaController jpaController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
       // jpaController = new JpaController(managerRepository);
    }

    @Test
    void findAll() {
        String baseResponse = jpaController.findAll();
        Assertions.assertThat(baseResponse).isEqualTo("dfgdfgfd");
    }
}