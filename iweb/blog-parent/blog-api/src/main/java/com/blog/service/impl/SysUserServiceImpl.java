package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.pojo.SysUser;
import com.blog.data.util.JWTUtils;
import com.blog.data.vo.ErrorCode;
import com.blog.data.vo.LoginUserVo;
import com.blog.data.vo.Result;
import com.blog.mapper.SysUserMapper;
import com.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;
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

    @Override
    public Result getUserInfoByToken(String token) {
        Map<String, Object> map = JWTUtils.checkToken(token);
        //token校验，注意token校验是否为空
        if(map==null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        //获取这个token的作用是 在redis中拿用户信息 用户信息是json
         String userJson = (String)redisTemplate.opsForValue().get("TOKEN_" + token);
        //可能登陆会过期，要判断是否为空
        if(userJson == null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        //逆向json 把json格式的用户转换为对象格式
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        //用户对象转化前端展示用户展示
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);
    }
}
