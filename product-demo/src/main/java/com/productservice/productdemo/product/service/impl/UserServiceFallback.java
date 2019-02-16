package com.productservice.productdemo.product.service.impl;

import com.productservice.productdemo.product.service.UserService;
import com.userservice.userdemo.user.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.product.service.impl
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-16 09:15
 * @Description: ...
 */
@Component
public class UserServiceFallback implements UserService {

    @Override
    public List<UserDto> findAll() {
        return this.findAllFallback();
    }

    @Override
    public UserDto load(Long id) {
        return this.loadFallback(id);
    }

    /**
     * 具体用户列表回退方法
     * @return
     */
    protected List<UserDto> findAllFallback() {
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto(1L, "zhangSan_static", "/users/avatar/zhangsan.png"));
        userDtos.add(new UserDto(2L, "lisi_static", "/users/avatar/lisi.png"));
        userDtos.add(new UserDto(3L, "wangwu_static", "/users/avatar/wangwu.png"));
        userDtos.add(new UserDto(4L, "yanxiaoliu_static", "/users/avatar/yanxiaoliu.png"));
        return userDtos;
    }

    /**
     * 具体用户详情回退方法
     * @param id
     * @return
     */
    protected UserDto loadFallback(Long id) {
        return new UserDto(id, "Anonymous", "default.png");
    }
}
