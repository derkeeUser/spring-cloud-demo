package com.userservice.userdemo.config;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.netflix.loadbalancer.*;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.userservice.userdemo.config
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-15 10:20
 * @Description: ...
 */
@Configuration
public class UserRibbonConfiguration {
    String listOfServers = "http://192.168.0.12:2100, http://192.168.0.12:2110, http://192.168.0.16:2100";
    /**
     * 构建固定的服务器列表
     */
    @Bean
    public ServerList<Server> ribbonServerList() {
        List<Server> list = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(listOfServers)) {
            for (String s: listOfServers.split(", ")){
                list.add(new Server(s.trim()));
            }
        }
        return new StaticServerList<>(list.toArray(new Server[0]));
    }

    /**
     * 将服务器存活探测策略更改为通过URL来判定
     * @return
     */
    @Bean
    public IPing ribbonPing() {
        return new PingUrl(false, "/cs/hostRunning");
    }

    /**
     * 将负载均衡策略定义为区域感知策略
     * @return
     */
    @Bean
    public IRule ribbonRule() {
        return new ZoneAvoidanceRule();
    }
}
