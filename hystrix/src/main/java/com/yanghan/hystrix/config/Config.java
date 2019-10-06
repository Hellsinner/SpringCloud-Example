package com.yanghan.hystrix.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCacheAspect;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by yanghan on 2019-10-06.
 */
@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

    @Bean
    public HystrixCacheAspect hystrixCacheAspect() {
        return new HystrixCacheAspect();
    }

    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> servletRegistrationBean() {
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean =
                new ServletRegistrationBean<>();
        registrationBean.setServlet(new HystrixMetricsStreamServlet());
        registrationBean.setName("hystrixMetricsStreamServlet");
        registrationBean.addUrlMappings("/hystrix/stream");
        return registrationBean;
    }
}
