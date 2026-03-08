package com.actividad3.gutierrez.patronesapi.config;

import com.actividad3.gutierrez.patronesapi.model.PatronDiseno;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, PatronDiseno> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, PatronDiseno> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        JacksonJsonRedisSerializer<PatronDiseno> jsonRedisSerializer = new JacksonJsonRedisSerializer<>(PatronDiseno.class );

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jsonRedisSerializer);
        template.setValueSerializer(jsonRedisSerializer);

        return template;
    }
}
