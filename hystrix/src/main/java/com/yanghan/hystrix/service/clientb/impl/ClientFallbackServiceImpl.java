package com.yanghan.hystrix.service.clientb.impl;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yanghan.hystrix.service.clientb.ClientFallbackService;
import org.springframework.stereotype.Component;

/**
 * Created by yanghan on 2019-10-06.
 */
@Component
    @DefaultProperties(defaultFallback = "defaultFallback")
public class ClientFallbackServiceImpl implements ClientFallbackService {
    @Override
    @HystrixCommand
    public String fallback() {
        throw new RuntimeException("123");
    }

    @Override
    @HystrixCommand
    public String fallbackPlus() {
        throw new RuntimeException("234");
    }

    private String defaultFallback() {
        return "defaultFallback";
    }
}
