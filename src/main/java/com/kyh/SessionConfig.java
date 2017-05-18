package com.kyh;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 前言：HttpSession默认使用内存来管理session，我们可以通过集成Spring Session来配置redis存储。
 *
 * Spring Session 支持使用 Redis、Mongo、JDBC、Hazelcast 来存储Session。
 * 有2种配置方式：
 *    a. 最简单配置: spring.session.store-type=redis
 *    b. 编写SessionConfig (主要是@EnableRedisHttpSession)
 */
//@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30) // 30天（默认30分钟）
public class SessionConfig {

}