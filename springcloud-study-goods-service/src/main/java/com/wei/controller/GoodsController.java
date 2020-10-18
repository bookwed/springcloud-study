package com.wei.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("goods")
@RestController
public class GoodsController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("get")
    public String get(String name){
        //使用RestTemplate来调用UserService
        List<ServiceInstance> instanceList = discoveryClient.getInstances("user-service");
        ServiceInstance instance = instanceList.get(0);
        String host = instance.getHost();
        int port= instance.getPort();
        String s = restTemplate.getForObject("http://" + host + ":" + port + "/user/hello?name={1}", String.class, name);
        return "这里是调用user-service返回的结果：" + s;
    }
}
