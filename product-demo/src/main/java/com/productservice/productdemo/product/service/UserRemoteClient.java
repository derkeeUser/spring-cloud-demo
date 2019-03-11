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
 * @BelongsPackage: com.productservice.productdemo.product.service.impl
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-08 17:47
 * @Description: ...
 */
@FeignClient(name="USER-SERVICE")
public interface UserRemoteClient {

    @RequestMapping(value="/users",method = RequestMethod.GET)
    List<UserDto> findAll();

    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    UserDto load(@PathVariable("id") Long id);
}
