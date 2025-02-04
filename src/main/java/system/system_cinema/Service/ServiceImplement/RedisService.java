package system.system_cinema.Service.ServiceImplement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import system.system_cinema.Service.IRedisService;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisService implements IRedisService {
    RedisTemplate<String, Object> redisTemplate;
    ObjectMapper objectMapper;

    @Override
    public void saveObject(String key, String field, Object object) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(object);
        redisTemplate.opsForHash().put(key, field, jsonString);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }

    @Override
    public <T> T getObject(String key, String field, Class<T> clazz) throws JsonProcessingException {
        Object jsonString = redisTemplate.opsForHash().get(key, field);
        if (jsonString != null) {
            return objectMapper.readValue(jsonString.toString(), clazz);
        }
        return null;
    }

    @Override
    public void deleteObject(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    @Override
    public String acquireLock(String key,String fields, long ttl) {
        String uniqueId = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForHash().putIfAbsent(key, fields, 1);
        return Boolean.TRUE.equals(success) ? uniqueId : null;
    }

    @Override
    public boolean releaseLock(String key, String value) {
        return false;
    }

}
