package com.kyh;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import java.lang.reflect.Method;

/**
 * @author kongyunhui on 2018/4/26.
 *
 * 使用spring注解的方式完成redis缓存
 *
 * 1、添加包依赖
 * 2、application.properties 之 redis配置
 * 3、@EnableCaching 开启缓存
 * 4、@Cacheable/@CachePut/@CacheEvict 操作缓存（查、增、删）
 *
 * 参考文档：http://wiselyman.iteye.com/blog/2184884
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 缓存管理器。此处设置了固定的expire。
     *
     * spring目前在@Cacheable等注解上不支持缓存时效设置，如有必要可以重写该注解。
     * 另外，还有两种方式可以修改TTL：1、@Cacheable(cacheManager="cacheManager2"); 2、redisTemplate.expire()
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间 (秒)
        redisCacheManager.setDefaultExpiration(60);
        return redisCacheManager;
    }

    /**
     * 自定义StringRedisTemplate，并使用jackson2JsonRedisSerializer处理value部分。
     *
     * 注1：为什么选用StringRedisTemplate？
     * 因为：RedsTemplate默认使用的是JdkSerializationRedisSerializer，存入数据时会转化成字节数组存入，可读性不好。
     *
     * 注2：为什么修改了value、hashValue的序列化类
     * 因为：默认的StringRedisTemplate仅支持字符串的序列化，由于我们需要存取复杂对象，因此必须修改序列化类。
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}  