package com.yanghan.hystrix.command;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yanghan on 2019-10-06.
 */
public class CollapserCommand extends HystrixCollapser<List<String>, String, Integer> {
    private int key;

    public CollapserCommand(int key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(
            Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(
            List<String> batchResponse,
            Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>> {
        private final Collection<CollapsedRequest<String, Integer>> requests;

        private BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            List<String> list = new ArrayList<>();

            for (CollapsedRequest<String, Integer> request : requests) {
                list.add("ValueForKey: " + request.getArgument());
            }

            return list;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<String> future1 = new CollapserCommand(1).queue();
        Future<String> future2 = new CollapserCommand(2).queue();
        Future<String> future3 = new CollapserCommand(3).queue();
        Future<String> future4 = new CollapserCommand(4).queue();

        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        System.out.println(future4.get());

        //System.out.println(HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        HystrixRequestLog currentRequest = HystrixRequestLog.getCurrentRequest();
        System.out.println(HystrixRequestLog.getCurrentRequest().getExecutedCommands().size());

        Optional<HystrixInvokableInfo<?>> first = currentRequest.getAllExecutedCommands().stream().findFirst();
        HystrixInvokableInfo<?> hystrixInvokableInfo = first.get();
        List<HystrixEventType> executionEvents = hystrixInvokableInfo.getExecutionEvents();
        context.shutdown();
    }
}
