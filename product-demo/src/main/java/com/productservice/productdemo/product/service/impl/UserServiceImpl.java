
package com.productservice.productdemo.product.service.impl;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.productservice.productdemo.product.repository.UserRedisRepository;
import com.productservice.productdemo.product.service.UserRemoteClient;
import com.productservice.productdemo.product.service.UserService;
import com.userservice.userdemo.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.product.service.impl
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-15 18:28
 * @Description: RestTemplate方式回退
 */

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(value = "restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    protected UserRemoteClient userRemoteClient;

    @Autowired
    protected UserRedisRepository userRedisRepository;


    /**
     * 通过restTemplate获取全部用户信息，并声明降级方法 findAllFallback
     *
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "findAllFallback")
    public List<UserDto> findAll() {
        //RestTemplate方式
        /*UserDto[] userDtos =
                this.restTemplate.getForEntity("http://USER-SERVICE/users/", UserDto[].class).getBody();
        return Arrays.asList(userDtos);*/

        //Feign方式
        List<UserDto> userDtos = userRemoteClient.findAll();
        return userDtos;
    }

    @Override
    @HystrixCommand(fallbackMethod = "loadFallback")
    public UserDto load(Long id) {
        //先从Redis中获取数据
        UserDto userDto = this.userRedisRepository.findOne(id);
        if (null != userDto) {
            LOGGER.info("已从Redis缓存中获取到用户：{}的信息",id);
            return userDto;
        }
        //如果Redis中不存在就从远程获取
        LOGGER.info("Redis缓存中不存在用户：{}的信息，尝试从远程进行加载", id);

        //RestTemplate方式
        //userDto = this.restTemplate.getForEntity("http://USER-SERVICE/users/{id}", UserDto.class, id).getBody();

        //Feign方式
        userDto = userRemoteClient.load(id);
        if (null != userDto) {
            //获取后将用户信息进行缓存
            this.userRedisRepository.saveUser(userDto);
        }
        return userDto;
    }


    /**
     * 该方法是findAll方法的降级方法，当restTemplate无法访问到USER-SERVICE时
     * 调用该方法返回一组固定的用户信息
     *
     * @return
     */

    protected List<UserDto> findAllFallback() {
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto(1L, "zhangSan_static", "/users/avatar/zhangsan.png"));
        return userDtos;
    }


    /**
     * 该方法是load方法的降级方法，当restTemplate无法访问到USER-SERVICE时
     * 调用该方法返回一个匿名用户信息
     *
     * @param id
     * @return
     */

    protected UserDto loadFallback(Long id) {
        return new UserDto(id, "Anonymous", "default.png");
    }
}

