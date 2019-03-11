package com.productservice.productdemo.config;

import com.userservice.userdemo.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.productservice.productdemo.config
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-08 16:53
 * @Description: ...
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host:}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.timeout:0}")
    private int timeout;
    @Value("${spring.redis.pool.max-active:100}")
    private int maxTotal = 100;
    @Value("${spring.redis.pool.max-idle:20}")
    private int maxIdle = 20;
    @Value("${spring.redis.pool.max-wait-millis:15000}")
    private long maxWaitMillis = 15000;

    @Bean
    public JedisConnectionFactory reJedisConnectionFactory() {
        //构建Redis连接池属性配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(this.maxTotal);
        poolConfig.setMaxIdle(this.maxIdle);
        poolConfig.setMaxWaitMillis(this.maxWaitMillis);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        //通过上面的配置属性，使用Jedis构建连接工厂
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        if (null != this.password && this.password.length() > 0) {
            factory.setPassword(this.password);
        }
        factory.setTimeout(timeout);
        factory.setUsePool(true);
        factory.setPoolConfig(poolConfig);
        return factory;
    }

    protected RedisTemplate buildRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //key的序列化处理方式，直接使用字符串
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "userRedisTemplate")
    public RedisTemplate<String, UserDto> userDtoRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        return this.buildRedisTemplate(redisConnectionFactory);
    }
}
