package com.yanghan.eurekaclienta.clientb;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by yanghan on 2019-10-05.
 */
public class TestCommand extends HystrixCommand<String> {
    private final String name;

    public TestCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        if (this.name.equals("failed")){
            throw new RuntimeException("command is failed");
        }
        return "test";
    }

    @Override
    protected String getFallback() {
        return "hystrix";
    }
}
