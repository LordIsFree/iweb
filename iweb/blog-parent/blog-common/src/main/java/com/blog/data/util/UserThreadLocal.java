package com.blog.data.util;

import com.blog.data.pojo.SysUser;
//单例模式 懒汉式
public class UserThreadLocal {
    //多线程环境下 隔离每个用户信息
    private static final ThreadLocal<SysUser> Local = new ThreadLocal<>();
    //放入用户信息
    public static void put(SysUser sysUser){
        Local.set(sysUser);
    }
    //获取用户信息
    public static SysUser get(){
       return Local.get();
    }
    //去除用户信息
    public static void remove(){
        Local.remove();
    }
}
