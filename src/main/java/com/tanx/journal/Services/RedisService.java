package com.tanx.journal.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tanx.journal.Entity.JournalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    RedisService(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, JournalEntry value, Long ttl){
        try{
            String jsonVal = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonVal, ttl, TimeUnit.SECONDS);
        }
        catch(Exception e){
            log.error("Exception in setting value in redis : {}", e.getLocalizedMessage());
        }
    }
    public <T> T get(String key, Class<T> tClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            assert o != null;
            return mapper.readValue(o.toString(), tClass);
        }
        catch(Exception e){
            log.error("Exception in getting value from redis : {}", e.getLocalizedMessage());
            return null;
        }
    }
}
