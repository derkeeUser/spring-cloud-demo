package com.oauth.oauthserver.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.oauth.oauthserver.config
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-15 14:43
 * @Description: ...
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class OAuthWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("test")
                .password("test").roles("USER").and()
                .withUser("admin").password("admin")
                .roles("USER", "ADMIN");
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        //需要将csrf禁用，否则会报不能跨域访问的错误
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/oauth/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and().httpBasic();
    }*/
}
