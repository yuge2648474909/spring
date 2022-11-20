package com.example.back_end.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_end.controller.dto.LoginUser;
import com.example.back_end.dao.MenuMapper;
import com.example.back_end.dao.UserMapper;
import com.example.back_end.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根绝用户名获取系统用户
        System.out.println("username" + username);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(wrapper);

        // 查不到用户抛出异常
        if(Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }

        List<String> perms = menuMapper.selectPermsByUserId(user.getId());

        return new LoginUser(user, perms);
    }
}
