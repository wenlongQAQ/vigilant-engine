package com.example.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class TUser {
    private Long id;
    private String username;
    private String password;
}
