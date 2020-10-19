package com.wei.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("hello")
    public String hello(String name){
        return "hello " + name;
    }

    @GetMapping("balance")
    public String balance(String name){
        System.out.println(port);
        return "hello " + name + "："+ port;
    }

}
