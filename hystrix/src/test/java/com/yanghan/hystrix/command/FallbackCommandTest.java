package com.yanghan.hystrix.command;

import org.junit.Test;

/**
 * Created by yanghan on 2019-10-05.
 */
public class FallbackCommandTest {
    @Test
    public void run() {
        FallbackCommand command1 = new FallbackCommand("aaa");

        String execute = command1.execute();

        System.out.println(execute);
    }
}