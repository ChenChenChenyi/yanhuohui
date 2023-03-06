package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.chenyi.yanhuohui.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {UnitTestControllerTest.class})
@ExtendWith(MockitoExtension.class)
class UnitTestControllerTest {

    @InjectMocks
    private UnitTestController unitTestController;

    @Mock
    ManagerRepository managerRepository;

    @Spy
    @Autowired
    UserService userService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(userService, "managerRepository", managerRepository);
    }

    @org.junit.jupiter.api.Test
    void sdfsfe() throws Exception {
        Assertions.assertThrows(SbcRuntimeException.class,() -> unitTestController.print());
    }

    }