package com.yanghan.hystrix.service.clientb;

import java.util.concurrent.Future;

/**
 * Created by yanghan on 2019-10-06.
 */
public interface ClientBService {
    String getClientB();

    Future<String> asyncGetClientB();
}
