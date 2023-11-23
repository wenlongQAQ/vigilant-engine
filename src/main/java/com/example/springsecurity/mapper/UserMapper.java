package com.example.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springsecurity.entity.TUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<TUser> {
}
