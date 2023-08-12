package com.blog.service;

import com.blog.data.param.LoginParams;
import com.blog.data.pojo.SysUser;
import com.blog.data.vo.Result;

public interface LoginService {
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);
}
