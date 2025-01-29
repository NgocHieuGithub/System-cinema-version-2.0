package system.system_cinema.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IRedisService {
    void saveObject(String key, String field, Object object) throws JsonProcessingException;
    <T> T getObject(String key, String field, Class<T> clazz) throws JsonProcessingException;
    void deleteObject(String key, String field);
    String acquireLock(String key,String fields, long ttl);
    boolean releaseLock(String key, String value);
}
