package com.wei.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建UserService接口，用来消费user-service服务提供的接口
 */
//使用 @FeignClient(“user-service”) 注解将当前接口和 user-service 服务绑定， user-service 是服务名，可以忽略大小写
@FeignClient("user-service")
public interface UserService {

    //使用 SpringMVC 的 @GetMapping("hello") 注解将 hello 方法和 user-service 中的 hello 接口绑定在一起
    //需要注意的是，在 SpringMVC 中，在需要给参数设置默认值或者要求参数必填的情况下才需要用到 @RequestParam 注解，而在这里，这个注解一定要加
    //注意请求路径
    @GetMapping("/user/hello")
    String hello(@RequestParam("name") String name);
}
