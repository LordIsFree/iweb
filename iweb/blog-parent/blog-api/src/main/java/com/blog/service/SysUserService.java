package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.pojo.SysUser;
import com.blog.data.vo.Result;

public interface SysUserService {
    String findNickNameByAuthorId(Long authorId);

    Result getUserInfoByToken(String token);
}
