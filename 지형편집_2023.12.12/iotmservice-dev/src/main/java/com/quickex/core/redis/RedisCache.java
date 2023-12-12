package com.quickex.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring redis Tool class
 *
 * @author .thero
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Resource(name = "redisTemplate")
    public RedisTemplate redisTemplate;

    /**
     * Cache basic objects, such as integer, string, entity class, etc
     *
     * @param key   Cached key values
     * @param value Cached values
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Cache basic objects, such as integer, string, entity class, etc
     *
     * @param key      Cached key values
     * @param value    Cached values
     * @param timeout  time
     * @param timeUnit Time granularity
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Set effective time
     *
     * @param key     Redis key
     * @param timeout Timeout
     * @return true=；false=
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Set effective time
     *
     * @param key     Redis key
     * @param timeout Timeout
     * @param unit    Time unit
     * @return true；false
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Gets the cached base object.
     *
     * @param key Cache key value
     * @return Cache data corresponding to key value
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Delete a single object
     *
     * @param key
     */
    public void deleteObject(final String key) {
         redisTemplate.delete(key);
    }

    /**
     * Delete collection object
     *
     * @param collection Multiple objects
     * @return
     */
    public void deleteObject(final Collection collection) {
         redisTemplate.delete(collection);
    }

    /**
     * Cache list data
     *
     * @param key      cached key values
     * @param dataList List data to be cached
     * @return Cached objects
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * Get cached list object
     *
     * @param key Cached key values
     * @return Cache data corresponding to key value
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * Cache set
     *
     * @param key     Cache key value
     * @param dataSet Cached data
     * @return Objects that cache data
     */
    public <T> long setCacheSet(final String key, final Set<T> dataSet) {
        Long count = redisTemplate.opsForSet().add(key, dataSet);
        return count == null ? 0 : count;
    }

    /**
     * Get cached set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Cache map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * Get cached map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Store data into hash
     *
     * @param key   Redis key
     * @param hKey  Hash key
     * @param value value
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * get Hash data
     *
     * @param key  Redis key
     * @param hKey Hash key
     * @return Hash obj
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * Get data from multiple hashes
     *
     * @param key   Redis key
     * @param hKeys Hash keys
     * @return Hash object collection
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * Get a list of cached base objects
     *
     * @param pattern String prefix
     * @return Object list
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * Atomic self accretion 1
     * @param keyEnum keyword
     */
    public Long autoIncr(final String keyEnum) {
        long val;
        try{
            val = redisTemplate.opsForValue().increment(keyEnum, 1L);
            if(val >= Long.MAX_VALUE) {
                redisTemplate.opsForValue().set(keyEnum, "1");
                val = 0L;
            }
        } catch(Exception e) {
            return null;
        }
        return val;
    }
}
