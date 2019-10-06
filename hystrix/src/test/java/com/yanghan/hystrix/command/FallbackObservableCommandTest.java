package com.yanghan.hystrix.command;

import org.junit.Test;
import rx.Observable;

import static org.junit.Assert.*;

/**
 * Created by yanghan on 2019-10-05.
 */
public class FallbackObservableCommandTest {
    @Test
    public void run(){
        FallbackObservableCommand command = new FallbackObservableCommand("aaa");

        Observable<String> observe = command.observe();

        observe.subscribe(System.out::println, Throwable::printStackTrace);
    }
}