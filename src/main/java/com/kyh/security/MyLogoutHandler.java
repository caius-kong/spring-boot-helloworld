package com.kyh.security;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kongyunhui on 2017/5/18.
 */
@Component
public class MyLogoutHandler implements LogoutHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        logger.debug("注销时执行必要的清理...");
        if(auth != null) {
            String sessionId = request.getSession().getId();
            String key1 = "spring:session:sessions:" + sessionId;
            String key2 = "spring:session:sessions:expires:" + sessionId;
            redisTemplate.delete(Lists.newArrayList(key1, key2));
            logger.debug("clear redis key1:{},key2:{}", key1, key2);
        }
    }
}
