package com.yanghan.eurekaclienta.clientb;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Created by yanghan on 2019-10-05.
 */
public class IndexCommand extends HystrixCommand<String> {

    private final String name;

    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IndexCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://EUREKA-CLIENT-B/hystrix", String.class);
    }

    @Override
    protected String getFallback() {
        return "clientb hystrix";
    }
}
