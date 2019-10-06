package com.yanghan.eurekaclienta.controller;

import com.yanghan.eurekaclienta.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yanghan on 2019-10-04.
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/index")
    public String index() {
        return "client=a";
    }

    @GetMapping("/client/b")
    public String clientb() {
        String object = indexService.clientb();
        System.out.println(object);
        return object;
    }

    public String defaultClientb() {
        return "fallback";
    }
}
