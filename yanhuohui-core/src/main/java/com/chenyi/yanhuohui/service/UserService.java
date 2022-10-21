package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.service.myinterface.MyFuntionalInterface;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import request.User;

@Service
public class UserService {
    public String addUser(User user) {
        // 直接编写业务逻辑
        return "success";
    }

    public void print(String s, MyFuntionalInterface funtion){
        funtion.print(s);
    }
}
