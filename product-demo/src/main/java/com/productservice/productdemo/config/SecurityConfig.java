package com.productservice.productdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.config
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-14 17:32
 * @Description: ...
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 定义用户信息Bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception{
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("zhangsan")
                .password("123456").roles("USER").and()
                .withUser("superAdmin").password("admin")
                .roles("USER", "ADMIN");
    }
}
