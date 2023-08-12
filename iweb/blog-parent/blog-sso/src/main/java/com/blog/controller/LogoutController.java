package com.blog.controller;

import com.blog.data.vo.Result;
import com.blog.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    LogoutService logoutService;
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return logoutService.logout(token);
    }
}
