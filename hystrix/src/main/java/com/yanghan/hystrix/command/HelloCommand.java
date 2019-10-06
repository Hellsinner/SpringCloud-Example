package com.yanghan.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by yanghan on 2019-10-05.
 */
public class HelloCommand extends HystrixCommand<String> {
    private final String name;

    public HelloCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "hello" + name;
    }
}
