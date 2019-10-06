package com.yanghan.hystrix.service.clientb;

/**
 * Created by yanghan on 2019-10-06.
 */
public interface ClientFallbackService {
    String fallback();

    String fallbackPlus();
}
