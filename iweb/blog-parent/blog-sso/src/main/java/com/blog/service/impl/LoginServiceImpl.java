package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.blog.data.param.LoginParams;
import com.blog.data.pojo.SysUser;
import com.blog.data.util.JWTUtils;
import com.blog.data.vo.ErrorCode;
import com.blog.data.vo.Result;
import com.blog.service.SysUserService;
import com.blog.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    //加密盐值
    private static final String slat = "mszlu!@###";
    @Autowired
    private SysUserService sysUserServiceImpl;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Result login(LoginParams loginParams) {
        //获取用户登陆参数，检测参数
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            //返回错误信息  错误信息为枚举类的内容
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode()
                    ,ErrorCode.PARAMS_ERROR.getMsg());
        }
        //密码加密后,校验密码
        String pwd = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserServiceImpl.fingUser(account,pwd);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode()
                    ,ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //发放token令牌，存入redis中
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

}
