package com.example.back_end.handler;

import com.alibaba.fastjson.JSON;
import com.example.back_end.common.Result;
import com.example.back_end.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 给前端Result 的json
        Result result= Result.error(Integer.toString(HttpStatus.UNAUTHORIZED.value()), "登陆认证失败了，请重新登陆！");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}
