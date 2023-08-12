package com.blog.service;

import com.blog.data.pojo.SysUser;

public interface SysUserService {
    String findNickNameByAuthorId(Long authorId);
    SysUser fingUser(String account, String password);
}
