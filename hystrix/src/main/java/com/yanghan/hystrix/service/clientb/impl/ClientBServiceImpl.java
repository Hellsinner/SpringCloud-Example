package com.yanghan.hystrix.service.clientb.impl;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.yanghan.hystrix.service.clientb.ClientBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

import static com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager.*;

/**
 * Created by yanghan on 2019-10-06.
 */
@DefaultProperties(threadPoolKey = "GET_CLIENT_B",
        threadPoolProperties = {
                @HystrixProperty(name = CORE_SIZE, value = "20")},
        commandProperties = {
                @HystrixProperty(name = EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "3000"),
                @HystrixProperty(name = CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
                @HystrixProperty(name = CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "10000")})
@Component
public class ClientBServiceImpl implements ClientBService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "defaultGetClientB")
    public String getClientB() {
        return restTemplate.getForObject("http://localhost:8081/hystrix", String.class);
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultGetClientB")
    public Future<String> asyncGetClientB() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://localhost:8081/hystrix", String.class);
            }
        };
    }

    @HystrixCommand(fallbackMethod = "lll")
    private String defaultGetClientB(Throwable e) {
        throw new RuntimeException("123");
    }

    private String lll() {
        return "clientb fallback again";
    }
}
