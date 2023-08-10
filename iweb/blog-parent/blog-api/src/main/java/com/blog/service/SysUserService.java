package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.pojo.SysUser;

public interface SysUserService {
    String findNickNameByAuthorId(Long authorId);
}
