package com.yanghan.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * Created by yanghan on 2019-10-05.
 */
public class CacheCommand extends HystrixCommand<Boolean> {

    private int value;

    public CacheCommand(int value) {
        super(HystrixCommandGroupKey.Factory.asKey("get"));
        this.value = value;
    }

    @Override
    protected Boolean run() throws Exception {
        return value == 0 || value % 2 == 0;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            CacheCommand command1 = new CacheCommand(2);
            CacheCommand command2 = new CacheCommand(2);
            CacheCommand command3 = new CacheCommand(0);

            Boolean execute = command1.execute();
            System.out.println(execute.booleanValue());
            System.out.println(command2.isResponseFromCache());

            Boolean execute1 = command2.execute();
            System.out.println(execute1);
            System.out.println(command2.isResponseFromCache());

            Boolean execute2 = command3.execute();
            System.out.println(execute2);
            System.out.println(command3.isResponseFromCache());
        }finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();
        try {
            CacheCommand command1 = new CacheCommand(2);
            Boolean execute = command1.execute();

            System.out.println(execute);
            System.out.println(command1.isResponseFromCache());
        }finally {
            context.shutdown();
        }
    }
}
