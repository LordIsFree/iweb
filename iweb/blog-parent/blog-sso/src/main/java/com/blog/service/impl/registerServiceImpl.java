package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.param.LoginParams;
import com.blog.data.pojo.SysUser;
import com.blog.data.util.JWTUtils;
import com.blog.data.vo.ErrorCode;
import com.blog.data.vo.Result;
import com.blog.mapper.SysUserMapper;
import com.blog.service.RegisterService;
import com.blog.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class registerServiceImpl implements RegisterService {
    //加密盐值
    private static final String slat = "mszlu!@###";
    @Autowired
    SysUserService sysUserServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;

    //两部以上的sql语句，需要加事务
    @Override
    public Result register(LoginParams loginParams) {
        //注册用户信息 校验数据是否存在
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode()
                    ,ErrorCode.PARAMS_ERROR.getMsg());
        }
        //检测用户名的唯一性
        SysUser sysUser = sysUserServiceImpl.findUserByAccount(account);
        if(sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode()
                    ,ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        // 创建用户对象 生成必要参数
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        //存入数据库
        sysUserServiceImpl.save(sysUser);
        //生成token
        String token = JWTUtils.createToken(sysUser.getId());
        //放入redis
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        //返回token
        return Result.success(token);
    }
}
