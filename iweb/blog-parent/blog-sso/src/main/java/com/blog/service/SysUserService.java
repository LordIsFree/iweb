package com.blog.service;

import com.blog.data.pojo.SysUser;
import com.blog.data.vo.Result;

public interface SysUserService {
    String findNickNameByAuthorId(Long authorId);
    SysUser fingUser(String account, String password);

    void save(SysUser sysUser);

    SysUser findUserByAccount(String account);
}
