package com.kyh.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 基于RedisTemplate，封装redis的基本操作
 *
 * @author kongyunhui 2018/4/25
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置字符串
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除给定的KEY
     *
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 添加集合元素
     *
     * @param key
     * @param mem
     */
    public void sadd(Object key, Object... mem) {
        redisTemplate.opsForSet().add(key, mem);
    }

    /**
     * 获取全部集合元素
     *
     * @param key
     * @return
     */
    public Set smembers(Object key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 删除集合元素
     *
     * @param key
     * @param mem
     */
    public void srem(Object key, Object... mem) {
        redisTemplate.opsForSet().remove(key, mem);
    }

    /**
     * 添加哈希
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(Object key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 添加哈希，并设置生存周期
     *
     * @param key
     * @param field
     * @param value
     * @param seconds
     */
    public void hsetByTTL(Object key, Object field, Object value, int seconds) {
        this.hset(key, field, value);
        this.expire(key, seconds);
    }

    /**
     * 添加哈希,存在则不保存
     *
     * @param key
     * @param field
     * @param value
     */
    public void hsetIfNotExists(Object key, Object field, Object value) {
        redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * 获取哈希值
     *
     * @param key
     * @param field
     * @return
     */
    public Object hget(Object key, Object field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取全部哈希值
     *
     * @param key
     * @return
     */
    public List hvals(Object key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 删除哈希
     *
     * @param key
     * @param field
     */
    public void hdel(Object key, Object... field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 获取keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 判断某个key是否存在
     *
     * @param key
     * @return
     */
    public Boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取剩余生存时间(seconds)
     *
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置生命周期
     *
     * @param key
     * @param seconds
     */
    public void expire(Object key, int seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 批量设置生命周期
     *
     * @param pattern
     * @param seconds
     */
    public void batchExpire(String pattern, int seconds) {
        Set<String> keys = this.keys(pattern);
        Iterator<String> keysIterator = keys.iterator();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            if (seconds != 0 && redisTemplate.hasKey(key) && redisTemplate.getExpire(key) == -1) {
                this.expire(key, seconds);
            }
        }
    }
}
