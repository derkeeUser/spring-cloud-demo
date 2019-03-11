package com.productservice.productdemo.product.repository;

import com.netflix.discovery.converters.Auto;
import com.userservice.userdemo.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.product.repository
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-08 17:24
 * @Description: ...
 */
@Repository
public class UserRedisRepository {
    /**
     * 定义User对象保存key的前缀
     */
    public static final String USER_KEY_PRE = "user:";

    /**
     * 将定义的RedisTemplate织入进来
     */
    @Autowired
    @Qualifier("userRedisTemplate")
    private RedisTemplate<String,UserDto> userDtoRedisTemplate;

    private ValueOperations<String,UserDto> operations;

    /**
     * 获取RedisTemplate中对值处理的对象
     */
    @PostConstruct
    private void init(){
        this.operations = this.userDtoRedisTemplate.opsForValue();
    }

    /**
     * 将UserDto对象保存到Redis中
     * @param userDto
     */
    public void saveUser(UserDto userDto) {
        this.operations.set(this.buildkey(userDto.getId()),userDto);
    }

    /**
     * 根据UserDto的ID进行查询
     * @param userId
     * @return
     */
    public UserDto findOne(Long userId) {
        String key = this.buildkey(userId);
        if (!this.userDtoRedisTemplate.hasKey(key)) {
            return null;
        }
        return this.operations.get(key);
    }

    /**
     * 从Redis删除指定的UserDto
     * @param userId
     */
    public void delete(Long userId) {
        this.userDtoRedisTemplate.delete(this.buildkey(userId));
    }

    /**
     * 将构建用户存储的key独立为一个方法
     * @param userId
     * @return
     */
    private String buildkey(Long userId) {
        return USER_KEY_PRE + String.valueOf(userId);
    }
}
