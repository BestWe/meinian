package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.Result;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @DubboReference
    private UserService userService;

    @ResponseBody
    @RequestMapping("/getUsername")
    public Result getUsername() {
        User principal = null;
        try {
            principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, principal);
    }
}
