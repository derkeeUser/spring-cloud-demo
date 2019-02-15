package com.productservice.productdemo.product.service;

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
//@FeignClient("USER-SERVICE")
public interface UserService {

    /*@RequestMapping(value="/users",method = RequestMethod.GET)
    List<UserDto> findAll();

    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    UserDto load(@PathVariable("id") Long id);
    */

    List<UserDto> findAll();

    UserDto load(Long id);
}
