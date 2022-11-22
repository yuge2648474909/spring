package com.example.back_end.controller.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    // 权限集
    List<String> permissions;
    // 后台用户
    private User user;

    public LoginUser(User user, List<String> permissions){
        this.user = user;
        this.permissions = permissions;

    }

    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities;


    /**
     * 用户的权限集
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities != null) {
            return authorities;
        }

        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 认证是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
