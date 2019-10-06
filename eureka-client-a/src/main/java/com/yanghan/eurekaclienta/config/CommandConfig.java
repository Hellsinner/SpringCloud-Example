package com.yanghan.eurekaclienta.config;

import com.yanghan.eurekaclienta.clientb.IndexCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by yanghan on 2019-10-05.
 */
@Configuration
public class CommandConfig {

    @Bean
    public IndexCommand indexCommand(RestTemplate restTemplate) {
        IndexCommand index = new IndexCommand("index");
        index.setRestTemplate(restTemplate);
        return index;
    }
}
