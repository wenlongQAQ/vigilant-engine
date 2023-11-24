package com.example.springsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springsecurity.entity.TUser;
import com.example.springsecurity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailService")
public class MyUserServiceDetail implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> role = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sal");
        // 根据用户名查询数据库
        TUser tUser = userMapper.selectOne(new LambdaQueryWrapper<TUser>().eq(TUser::getUsername, s));
        if (tUser == null)
            throw new UsernameNotFoundException("用户名不存在");
        return new User(tUser.getUsername(), new BCryptPasswordEncoder().encode(tUser.getPassword()),role);
    }
}
