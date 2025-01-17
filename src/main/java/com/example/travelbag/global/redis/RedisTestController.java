package com.example.travelbag.global.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/test-redis")
    public String testRedisConnection() {
        try {
            // Redis에 테스트 데이터 저장
            redisTemplate.opsForValue().set("testKey", "testValue");

            // Redis에서 데이터 가져오기
            String value = redisTemplate.opsForValue().get("testKey");

            if ("testValue".equals(value)) {
                return "Successfully connected to Redis! Key: testKey, Value: " + value;
            } else {
                return "Failed to retrieve the correct value from Redis.";
            }
        } catch (Exception e) {
            return "Error connecting to Redis: " + e.getMessage();
        }
    }
}
