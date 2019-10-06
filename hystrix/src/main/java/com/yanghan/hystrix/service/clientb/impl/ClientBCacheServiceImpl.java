package com.yanghan.hystrix.service.clientb.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.yanghan.hystrix.model.User;
import com.yanghan.hystrix.service.clientb.ClientBCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager.*;

/**
 * Created by yanghan on 2019-10-06.
 */
@Component
public class ClientBCacheServiceImpl implements ClientBCacheService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand
    @CacheResult(cacheKeyMethod = "idCacheKeyMethod")
    public String get(@CacheKey String id) {
        System.out.println("no from get");
        return restTemplate.getForObject("http://localhost:8081/hystrix/cache/" + id, String.class);
    }

    @Override
    @HystrixCommand
    @CacheRemove(commandKey = "get", cacheKeyMethod = "idCacheKeyMethod")
    public void update(@CacheKey String id) {
        System.out.println("clear cache");
    }

    @Override
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,
                    value = "2000")})
    @CacheResult
    public User getUser(String name) {
        System.out.println("no from getUser cache");
        User user = new User();
        user.setName(name);
        user.setAge(10);
        return user;
    }

    @Override
    @HystrixCommand(threadPoolProperties = {
            @HystrixProperty(name = CORE_SIZE, value = "5"),
            @HystrixProperty(name = MAX_QUEUE_SIZE, value = "20")})
    @CacheRemove(commandKey = "getUser")
    public void updateUser(@CacheKey("name") User user) {
        System.out.println("clear user cache");
    }

    private String idCacheKeyMethod(String id) {
        return id;
    }
}
