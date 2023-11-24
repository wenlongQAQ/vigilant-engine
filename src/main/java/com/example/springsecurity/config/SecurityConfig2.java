package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //生成表
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    private PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //退出
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/test/hello").permitAll();
        // 配置自定义403界面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.formLogin()
                .loginPage("/login.html") // 登录页面设置
                .loginProcessingUrl("/user/login")//登录访问的路径 由SpringSecurity判断登录是否成功 也就是实现的UserDetailService接口
                .defaultSuccessUrl("/success.html").permitAll() // 登录成功后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll() // 设置访问哪些路径可以直接访问 不需要认证
//                .antMatchers("/test/index").hasAuthority("admins") //当前登录的用户，只有具有admins权限才可以访问这个路径
//                .antMatchers("/test/index").hasAnyAuthority("admins,manager")
                .antMatchers("/test/index").hasRole("sale")
                .anyRequest().authenticated()
                .and()
                .rememberMe()// 设置记住我
                .tokenRepository(persistentTokenRepository())// 设置操作数据库的对象
                .tokenValiditySeconds(3600) // 设置token超时时间
                .userDetailsService(userDetailsService)
                .and().csrf().disable() // 关闭csrf防护
        ;
    }
}
