package com.yanghan.eurekaclientb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by yanghan on 2019-10-04.
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "client-b";
    }

    @GetMapping("/hystrix")
    public String hystrix() {
        int i = new Random().nextInt(2000);
        System.out.println(i);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "client-b";
    }

    @GetMapping("/hystrix/cache/{id}")
    public String hystrix(@PathVariable String id) {
        return "client-b: " + id;
    }
}
