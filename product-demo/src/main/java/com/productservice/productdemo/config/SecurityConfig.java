//package com.productservice.productdemo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
///**
// * @BelongsProject: scdemo
// * @BelongsPackage: com.productservice.productdemo.config
// * @Author: chenxiaoming
// * @CreateTime: 2019-03-14 17:32
// * @Description: ...
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 定义用户信息Bean
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("test")
//                .password("test").roles("USER").and()
//                .withUser("admin").password("admin")
//                .roles("USER", "ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //需要将csrf禁用，否则会报不能跨域访问的错误
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.DELETE, "/products/**")
//                .hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and().httpBasic();
//    }
//}
