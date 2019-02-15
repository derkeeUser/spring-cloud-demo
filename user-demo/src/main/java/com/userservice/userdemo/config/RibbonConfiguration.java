package com.userservice.userdemo.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.userservice.userdemo.config
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-15 10:19
 * @Description: ...
 */
@Configuration
@RibbonClient(name="user-service",configuration = UserRibbonConfiguration.class)
public class RibbonConfiguration {
}
