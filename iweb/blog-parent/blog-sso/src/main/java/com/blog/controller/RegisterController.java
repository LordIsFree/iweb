package com.blog.controller;

import com.blog.data.param.LoginParams;
import com.blog.data.pojo.SysUser;
import com.blog.data.vo.LoginUserVo;
import com.blog.data.vo.Result;
import com.blog.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    RegisterService registerServiceImpl;
    @PostMapping
    public Result register(@RequestBody LoginParams loginParams){
        return registerServiceImpl.register(loginParams);
    }
}
