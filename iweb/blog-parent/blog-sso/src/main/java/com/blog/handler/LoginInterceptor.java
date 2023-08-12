package com.blog.handler;

import com.alibaba.fastjson.JSON;
import com.blog.data.pojo.SysUser;
import com.blog.data.util.UserThreadLocal;
import com.blog.data.vo.ErrorCode;
import com.blog.data.vo.Result;
import com.blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
//继承HandlerInterceptor的类就是拦截器  未登录拦截器
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
        if (!(handler instanceof HandlerMethod)){
            //handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
            return true;
        }
        //获取token 打印日志
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        //判断token 未登录没有token
        if (token == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //校验请求的是不是同一个用户  为什么放在登陆服务里面
        SysUser sysUser = loginService.checkToken(token);
        //验证redis的登陆数据是否过期
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //是登录状态，放行
        //我希望在controller中 直接获取用户的信息 怎么获取?
        //放入ThreadLocal中  不知它要做什么
        UserThreadLocal.put(sysUser);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}