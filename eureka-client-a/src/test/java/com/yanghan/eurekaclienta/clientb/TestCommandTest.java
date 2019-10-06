package com.yanghan.eurekaclienta.clientb;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by yanghan on 2019-10-05.
 */
public class TestCommandTest {
    @Test
    public void run() {
        TestCommand test = new TestCommand("test");
        TestCommand failed = new TestCommand("failed");

        Assert.assertEquals("test", test.execute());
        Assert.assertEquals("hystrix", failed.execute());
    }

    @Test
    public void testCache() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            TestCacheCommand command1 = new TestCacheCommand("111");
            TestCacheCommand command2 = new TestCacheCommand("222");

            Assert.assertEquals("test", command1.execute());
            Assert.assertFalse(command1.isResponseFromCache());

            Assert.assertEquals("test", command2.execute());
            Assert.assertTrue(command2.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();

        try {
            TestCacheCommand command3 = new TestCacheCommand("333");

            Assert.assertEquals("test", command3.execute());
            Assert.assertFalse(command3.isResponseFromCache());
        }finally {
            context.shutdown();;
        }
    }
}