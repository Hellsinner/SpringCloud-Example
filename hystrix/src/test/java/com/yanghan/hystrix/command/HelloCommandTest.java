package com.yanghan.hystrix.command;

import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by yanghan on 2019-10-05.
 */
public class HelloCommandTest {
    @Test
    public void run() throws ExecutionException, InterruptedException {
        HelloCommand command1 = new HelloCommand("aaa");
        HelloCommand command2 = new HelloCommand("bbb");
        HelloCommand command3 = new HelloCommand("ccc");

        String execute = command1.execute();
        System.out.println(execute);

        Future<String> queue = command2.queue();
        System.out.println(queue.get());

        Observable<String> observe = command3.observe();
        // 阻塞的
        String s = observe.toBlocking().toFuture().get();
        System.out.println(s);

        // 非阻塞
        observe.subscribe(System.out::println,
                Throwable::printStackTrace);
    }
}