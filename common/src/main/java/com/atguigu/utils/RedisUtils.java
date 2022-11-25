package com.atguigu.utils;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    public RedisTemplate<String, Object> template;

    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // json序列化配置
        GenericFastJsonRedisSerializer serializer = new GenericFastJsonRedisSerializer();
        //key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //hash的key也采用String 的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value的序列化方式采用jackson的方式
        redisTemplate.setValueSerializer(serializer);
        //hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(serializer);
        //根据传入的参数进行初始化配置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
