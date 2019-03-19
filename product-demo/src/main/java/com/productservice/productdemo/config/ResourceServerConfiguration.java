package com.productservice.productdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.config
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-15 18:26
 * @Description: ...
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }
}
