package com.productservice.productdemo.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.util
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-14 15:22
 * @Description: ...
 */
public class ApplicationContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    @Override
    public void destroy() throws Exception {
        ApplicationContextHolder.clearHolder();
    }

    private static void clearHolder() {
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    //获取ApplicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
