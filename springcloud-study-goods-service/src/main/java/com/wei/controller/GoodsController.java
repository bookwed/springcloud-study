package com.wei.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("goods")
@RestController
public class GoodsController {

    @GetMapping("get")
    public String get(){
        return "这里需要调用用户服务";
    }
}
