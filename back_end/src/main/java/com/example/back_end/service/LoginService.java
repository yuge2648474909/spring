package com.example.back_end.service;

import com.example.back_end.common.Result;
import com.example.back_end.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface LoginService{
    /**
     * 登录功能
     * @return
     */
    public Result login(User user);

    /**
     * 登出功能
     * @return
     */
    Result logout();

}
