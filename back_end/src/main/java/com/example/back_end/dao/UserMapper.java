package com.example.back_end.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_end.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
