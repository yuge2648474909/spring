package com.example.back_end.service.Impl;

import com.example.back_end.common.Result;
import com.example.back_end.controller.dto.LoginUser;
import com.example.back_end.entity.User;
import com.example.back_end.service.LoginService;
import com.example.back_end.utils.JwtUtil;
import com.example.back_end.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义接口的实现功能
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    /**
     * 登录功能实现，检验用户信息是否存在，然后将存在用户信息，保存在redis中。
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        //3 使用ProviderManager auth方法进行验证

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        // 调用UserDetails的loadUserByUsername，获取用户信息
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        if(Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误! ");
        }

        // 4自己生成jwt给前端
        LoginUser loginUser = (LoginUser) (authentication.getPrincipal());
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);

        // 将用户信息保存到redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        return Result.success(map);
    }

    /**
     *
     * 登出功能，主要是删除redis中保存的用户信息
     * @return
     */
    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("login:" + userId);
        return Result.success();
    }
}
