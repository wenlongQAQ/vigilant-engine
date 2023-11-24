package com.example.springsecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springsecurity.entity.TUser;
import com.example.springsecurity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/index")

    public String index(){
        return "ok";
    }

    @GetMapping("/update")
    @Secured({"ROLE_sale","ROLE_manager"})
    public String update(){
        return "update";
    }
    @GetMapping("/delete")
    @PreAuthorize("hasAnyAuthority('admins')")
    public String delete(){
        return "delete";
    }
    @GetMapping("/post")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String post(){
        System.out.println("post");
        return "post";
    }
    @PreAuthorize("hasAuthority('admins')")
    @PostFilter("filterObject.username == '123'")
    @ResponseBody
    @GetMapping("/getAll")
    public List<TUser> getAllUser(){
        List<TUser> tUsers = userMapper.selectList(null);
        return tUsers;
    }
    @PreAuthorize("hasAuthority('admins')")
    @PreFilter("filterObject % 2 == 0")
    @ResponseBody
    @GetMapping("/ids")
    public List<TUser> getAllUser(List<Integer> ids){
        if (ids == null || ids.isEmpty())
            return new ArrayList<>();
        return userMapper.selectList(new LambdaQueryWrapper<TUser>().in(TUser::getId,ids));
    }

}
