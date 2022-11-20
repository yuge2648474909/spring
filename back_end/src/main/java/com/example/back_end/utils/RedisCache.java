package com.example.back_end.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.jws.Oneway;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象， Integer,String,实体类等
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void setCacheObject(final String key, final T value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key
     * @param value
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @param <T>
     */
    public <T> void setCacheObjetc(final String key, final T value, final Integer timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除对象
     * @param key
     * @return
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合
     * @param collection
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     * @param key
     * @param dataList
     * @param <T>
     * @return
     */
    public <T> long setCacheList(final String key, final List<T> dataList){
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ?0 :count;
    }

    /**
     * 获取缓存list的所有对象
     * @param key
     * @param <T>
     * @return
     */
    public <T>  List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key
     * @param dataSet
     * @param <T>
     * @return
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key,final Set<T> dataSet){
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while(it.hasNext()){
            setOperations.add(it.next());
        }
        return setOperations;
    }

    /**
     * 获取缓存的Set
     * @param <T>
     * @return
     */
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     *  缓存map
     * @param key
     * @param dataMap
     * @param <T>
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if(dataMap != null){
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获取缓存的Map
     * @param key
     * @param <T>
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向Hash中存入键值
     * @param key
     * @param hkey
     * @param value
     * @param <T>
     */
    public <T> void serCacheMapValue(final String key, final String hkey, final T value){
        redisTemplate.opsForHash().put(key, hkey, value);
    }

    /**
     * 获取Hash中的数据
     * @param key
     * @param hKey
     * @param <T>
     * @return
     */
    public <T> T getChcheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     * @param key
     * @param heky
     */
    public void delCacheMapValue(final String key, final String heky){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, heky);
    }

    /**
     * 获取多个Hash中的数据
     * @param key
     * @param hKeys
     * @param <T>
     * @return
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys){
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return
     */
    public Collection<String> keys(final String pattern){
        return redisTemplate.keys(pattern);
    }

    public void setCacheObject(int i, String 登录成功, Map<String, String> map) {
    }
}
