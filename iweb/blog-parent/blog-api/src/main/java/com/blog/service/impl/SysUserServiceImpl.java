package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.pojo.SysUser;
import com.blog.mapper.SysUserMapper;
import com.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public String findNickNameByAuthorId(Long authorId){
        if(authorId==null){
            return "佚名";
        }
        //
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId,authorId);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if(sysUser.getNickname()==null){
            return "佚名";
        }
        return sysUser.getNickname();
    }
}
