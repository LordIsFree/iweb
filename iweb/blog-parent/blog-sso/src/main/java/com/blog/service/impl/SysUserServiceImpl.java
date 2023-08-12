package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.data.pojo.SysUser;
import com.blog.data.vo.ErrorCode;
import com.blog.data.vo.Result;
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

    public SysUser fingUser(String account,String password){
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        //判断账号密码是否相等
        queryWrapper.eq(SysUser::getAccount,account)
                .eq(SysUser::getPassword,password);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public void save(SysUser sysUser) {
        //插入注册用户 id自动生成
        sysUserMapper.insert(sysUser);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }
}
