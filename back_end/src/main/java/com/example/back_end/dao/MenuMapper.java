package com.example.back_end.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_end.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface MenuMapper  extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);
}
