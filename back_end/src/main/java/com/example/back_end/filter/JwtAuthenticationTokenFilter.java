package com.example.back_end.filter;

import com.example.back_end.common.Result;
import com.example.back_end.controller.dto.LoginUser;
import com.example.back_end.utils.JwtUtil;
import com.example.back_end.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 每次后端发送请求，进行拦截进行权限验证
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取header 中的token
//        System.out.println("token");
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)) {
            //放行，去重新生成token
            filterChain.doFilter(request, response);
            return;
        }

        //2 解析token
        String userId;
        try{

            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
//            System.out.println("userId" + userId);
        }catch (Exception e){
            //抛出运行时异常
            throw new RuntimeException("token不合法2");
        }

        // 3 获取UserId，redis获取用户信息

        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("当前用户未登录");
        }

        // 4封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                    = new UsernamePasswordAuthenticationToken(loginUser, null, null);

        // 5 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // 放行，让后面的过滤器执行
        filterChain.doFilter(request, response);
    }
}
