package com.yanghan.hystrix.service.clientb.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yanghan.hystrix.service.clientb.ClientExceptionService;
import org.springframework.stereotype.Component;

/**
 * Created by yanghan on 2019-10-06.
 */
@Component
public class ClientExceptionServiceImpl implements ClientExceptionService {

    @Override
    @HystrixCommand(ignoreExceptions = CustomException.class,
            fallbackMethod = "exfallback")
    public String exception() {
        throw new CustomException("123");
    }

    private String exfallback() {
        return "exfallback";
    }


    private static class CustomException extends RuntimeException {
        private String msg;

        public CustomException(String msg) {
            super(msg);
        }
    }
}
