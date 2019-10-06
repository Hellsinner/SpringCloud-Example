package com.yanghan.hystrix.service.impl;

import com.yanghan.hystrix.model.User;
import com.yanghan.hystrix.service.IndexService;
import com.yanghan.hystrix.service.clientb.ClientBCacheService;
import com.yanghan.hystrix.service.clientb.ClientBService;
import com.yanghan.hystrix.service.clientb.ClientExceptionService;
import com.yanghan.hystrix.service.clientb.ClientFallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by yanghan on 2019-10-06.
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private ClientBService clientBService;

    @Autowired
    private ClientFallbackService clientFallbackService;

    @Autowired
    private ClientExceptionService clientExceptionService;

    @Autowired
    private ClientBCacheService clientBCacheService;

    @Override
    public String index() {
        String clientB = clientBService.getClientB();
        System.out.println(clientB);
        return clientB;
    }

    @Override
    public String asyncIndex() {
        Future<String> future = clientBService.asyncGetClientB();
        try {
            return future.get();
        } catch (Exception e) {
            return "has exception";
        }
    }

    @Override
    public String fallback() {
        return clientFallbackService.fallback();
    }

    @Override
    public String fallbackPlus() {
        return clientFallbackService.fallbackPlus();
    }

    @Override
    public String exception() {
        try {
            return clientExceptionService.exception();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String cache(String id) {
        String cache = clientBCacheService.get(id);

        System.out.println("first: " + cache);

        String s = clientBCacheService.get(id);

        System.out.println("cache: " + s);

        clientBCacheService.update(id);

        String s1 = clientBCacheService.get(id);
        System.out.println("third: " + s1);
        return s1;
    }

    @Override
    public User cacheUser(String name) {
        User user = clientBCacheService.getUser(name);

        System.out.println("first: " + user);

        User user1 = clientBCacheService.getUser(name);

        System.out.println("cacheUser: " + user1);

        clientBCacheService.updateUser(user1);

        User user2 = clientBCacheService.getUser(name);
        System.out.println("third :" + user2);
        return user2;
    }
}
