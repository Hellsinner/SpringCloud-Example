package com.yanghan.hystrix.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.yanghan.hystrix.model.User;
import com.yanghan.hystrix.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yanghan on 2019-10-06.
 */
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;

    @GetMapping("/index")
    public String index() {
        return indexService.index();
    }

    @GetMapping("/index/async")
    public String async() {
        return indexService.asyncIndex();
    }

    @GetMapping("/index/fallback")
    public String fallback() {
        return indexService.fallback();
    }

    @GetMapping("/index/fallback/plus")
    public String plus() {
        return indexService.fallbackPlus();
    }

    @GetMapping("/index/exception")
    public String exception() {
        return indexService.exception();
    }

    @GetMapping("/index/cache/{id}")
    public String cache(@PathVariable String id) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            String cache = indexService.cache(id);
        } finally {
            context.shutdown();
        }
        return "success";
    }

    @GetMapping("/index/cache/user/{name}")
    public User cacheUser(@PathVariable String name) {
        return indexService.cacheUser(name);
    }
}
