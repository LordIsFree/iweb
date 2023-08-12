package com.blog.service;

import com.blog.data.vo.Result;

public interface LogoutService {
    Result logout(String token);
}
