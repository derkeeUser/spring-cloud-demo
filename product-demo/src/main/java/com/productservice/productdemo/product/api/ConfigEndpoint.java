package com.productservice.productdemo.product.api;

import com.productservice.productdemo.util.ApplicationContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.config.configserver.api
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-26 17:02
 * @Description: ...
 */
@RestController
@RequestMapping("/config")
public class ConfigEndpoint {
    //@Value("${foo}")
    //private String foo;

    @RequestMapping("/foo")
    public String foo(){
        String foo = ApplicationContextHolder.getApplicationContext()
                        .getEnvironment().getProperty("foo");
        return "Hi," + foo + "!";
    }
}
