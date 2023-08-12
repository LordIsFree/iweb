package com.blog.service.impl;

import com.blog.data.vo.ErrorCode;
import com.blog.data.vo.Result;
import com.blog.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Result logout(String token) {
        //判断token 是否接收到 是否没有
//        if(token==null){
//            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
//        }

        //这里不做判断 因为用户不登陆就不能退出(不能发送该请求) 不用判断空
        //退出登陆 把json用户 从redis移除
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
