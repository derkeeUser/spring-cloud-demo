package com.productservice.productdemo.product.service;

import com.productservice.productdemo.product.service.impl.UserServiceFallback;
import com.userservice.userdemo.user.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.product.service
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-14 15:30
 * @Description: ...
 */
//@FeignClient(name="USER-SERVICE",fallback = UserServiceFallback.class)
public interface UserService {

    /**
     * ------------------------------Feign方式的Hystrix----------------------------------
     * 需要打开类上面的@FeignClient注解，关闭该服务的UserServiceImpl类
     */

   /* @RequestMapping(value="/users",method = RequestMethod.GET)
    List<UserDto> findAll();

    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    UserDto load(@PathVariable("id") Long id);*/


    /**
     * ------------------------------RestTemplate方式的Hystrix----------------------------------
     * 需要关闭类上面的@FeignClient注解，解注该服务的UserServiceImpl类
     */

    List<UserDto> findAll();

    UserDto load(Long id);
}
