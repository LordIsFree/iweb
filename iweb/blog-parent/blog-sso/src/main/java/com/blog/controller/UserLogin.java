package com.blog.controller;

import com.blog.data.vo.Result;
import com.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.data.param.LoginParams;

@RestController
@RequestMapping
public class UserLogin {
    @Autowired
    private LoginService loginServiceImpl;
    @PostMapping("login")
    public Result login(@RequestBody LoginParams loginParams){
        return loginServiceImpl.login(loginParams);
    }
}
