package com.blog.controller;

import com.blog.data.vo.Result;
import com.blog.mapper.SysUserMapper;
import com.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@CrossOrigin
public class UserController {
    @Autowired
    SysUserService sysUserServiceImpl;
    //用户详情
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization")String token){
        return sysUserServiceImpl.getUserInfoByToken(token);
    }
}
