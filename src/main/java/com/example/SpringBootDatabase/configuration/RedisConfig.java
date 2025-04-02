package com.example.SpringBootDatabase.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Configuration
public class RedisConfig {

    // Cấu hình RedisConnectionFactory để kết nối với Redis.
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("127.0.0.1", 6378);
        configuration.setUsername("default");
        configuration.setPassword("root");

        return new LettuceConnectionFactory(configuration);
    }

    // Cấu hình RedisTemplate để thao tác với Redis.
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // thiết lập kết nối Redis cho template
        template.setConnectionFactory(redisConnectionFactory());

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }


    // Kiểm tra kết nối Redis ngay khi Spring Boot khởi động
    @Bean
    public CommandLineRunner testRedisConnection(RedisTemplate<String, String> redisTemplate) {
        return args -> {
            try {
                // Set key trong Redis
                redisTemplate.opsForValue().set("testKey", "Hello, World!");
                // Lấy lại giá trị từ Redis
                String value = redisTemplate.opsForValue().get("testKey");

                if ("Hello, World!".equals(value)) {
                    System.out.println("✅ Redis connected successfully! Value: " + value);
                } else {
                    System.out.println("⚠ Redis connected but value mismatch!");
                }
            } catch (Exception e) {
                System.err.println("❌ Redis connection failed: " + e.getMessage());
            }
        };
    }

}
