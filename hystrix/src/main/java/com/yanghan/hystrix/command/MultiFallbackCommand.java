package com.yanghan.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanghan on 2019-10-06.
 */
public class MultiFallbackCommand extends HystrixCommand<String> {
    private final String name;

    public MultiFallbackCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getValueCommand")));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("exception");
    }

    @Override
    protected String getFallback() {
        return new FallbackViaNetwork(name).execute();
    }

    private static class FallbackViaNetwork extends HystrixCommand<String> {
        private final String name;

        private static final Map<String, String> cache = new HashMap<>();

        public FallbackViaNetwork(String name) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("getValueFallbackCommand")));
            this.name = name;
            cache.put("111", "get");
        }

        @Override
        protected String run() throws Exception {
            String s = cache.get(name);
            if (s == null){
                throw new RuntimeException("get no hit");
            }
            return s;
        }

        @Override
        protected String getFallback() {
            return "multi fallback";
        }
    }

    public static void main(String[] args) {
        MultiFallbackCommand command1 = new MultiFallbackCommand("222");
        MultiFallbackCommand command2 = new MultiFallbackCommand("111");

        String execute = command1.execute();
        System.out.println(execute.equals("multi fallback"));

        String execute1 = command2.execute();
        System.out.println(execute1.equals("get"));
    }
}
