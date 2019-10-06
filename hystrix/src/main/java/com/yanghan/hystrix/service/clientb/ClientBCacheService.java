package com.yanghan.hystrix.service.clientb;

import com.yanghan.hystrix.model.User;

/**
 * Created by yanghan on 2019-10-06.
 */
public interface ClientBCacheService {
    String get(String id);

    void update(String id);

    User getUser(String name);

    void updateUser(User user);
}
