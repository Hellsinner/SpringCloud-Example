package com.yanghan.hystrix.service;

import com.yanghan.hystrix.model.User;

/**
 * Created by yanghan on 2019-10-06.
 */
public interface IndexService {
    String index();

    String asyncIndex();

    String fallback();

    String fallbackPlus();

    String exception();

    String cache(String id);

    User cacheUser(String name);
}
