package com.chenyi.yanhuohui.service;

import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.chenyi.yanhuohui.service.myinterface.MyFuntionalInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chenyi.yanhuohui.request.User;

@Service
public class UserService {

    @Autowired
    private ManagerRepository managerRepository;

    public String addUser(User user) {
        // 直接编写业务逻辑
        return "success";
    }

    public void print(String s, MyFuntionalInterface funtion){
        funtion.print(s);
    }
}
