package com.chenyi.yanhuohui.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Spy
    private UserService userService;

    @Test
    void addUser() {
        MockitoAnnotations.initMocks(this);
        assertEquals(userService.addUser(any()),"success");
    }

    @Test
    void print() {
    }
}