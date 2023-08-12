package com.blog.service;

import com.blog.data.param.LoginParams;
import com.blog.data.vo.Result;

public interface RegisterService {
    Result register(LoginParams loginParams);
}
