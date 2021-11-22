package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.provider.ParamValidProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import request.User;
import com.chenyi.yanhuohui.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/paramValid")
public class ParamValidController implements ParamValidProvider {

    @Autowired
    private UserService userService;

    /**
     * 添加@Valid注解，用于对入参进行校验
     * 这个地方添加了全局异常处理的，用于捕获MethodArgumentNotValidException异常，将异常包装后返回
     */
    @Override
    public BaseResponse addUser(@RequestBody @Valid User user) {
        return BaseResponse.success(userService.addUser(user));
    }

    /**
     *将返回值进行包装后返回给前端
     */
    @Override
    public BaseResponse<User> getUser() {
        User user = new User();
        user.setId(1L);
        user.setAccount("12345678");
        user.setPassword("12345678");
        user.setEmail("123@qq.com");
        return BaseResponse.success(user);
    }

    /**
     * 新建全局处理类实现ResponseBodyAdvice，可对controller中的返回值进行包装；
     * 如果懒得对每次接口返回值进行包装，可以用这种全局处理类省去这个过程；
     * 不过我的商城项目没用这个，所以先注释掉这个功能；
     * @return
     */
    @Override
    public User getUserByAdvice() {
        User user = new User();
        user.setId(1L);
        user.setAccount("12345678");
        user.setPassword("1234");
        user.setEmail("123@qq.com");
        return user;
    }
}
