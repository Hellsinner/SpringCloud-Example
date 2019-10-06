package com.yanghan.hystrix.command;

import org.junit.Test;
import rx.Observable;

/**
 * Created by yanghan on 2019-10-05.
 */
public class HelloObservableCommandTest {
    @Test
    public void run() {
        HelloObservableCommand command1 = new HelloObservableCommand("aaa");
        HelloObservableCommand command2 = new HelloObservableCommand("bbb");
        // 命令已经执行
        Observable<String> observe = command1.observe();

        observe.subscribe(System.out::println, Throwable::printStackTrace);
        // 命令并没有执行，在进行subscribe时才会执行
        Observable<String> observable = command2.toObservable();

        observable.subscribe(System.out::println, Throwable::printStackTrace);
    }
}