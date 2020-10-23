package com.wei.service;

import com.wei.user.common.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 创建UserService接口，用来消费user-service服务提供的接口
 */
//使用 @FeignClient(“user-service”) 注解将当前接口和 user-service 服务绑定， user-service 是服务名，可以忽略大小写
@FeignClient("user-service")
@RequestMapping("user")
public interface IUserRemoteService {

    //使用 SpringMVC 的 @GetMapping("hello") 注解将 hello 方法和 user-service 中的 hello 接口绑定在一起
    //需要注意的是，在 SpringMVC 中，在需要给参数设置默认值或者要求参数必填的情况下才需要用到 @RequestParam 注解，而在这里，这个注解一定要加
    @GetMapping("hello")
    String hello(@RequestParam("name") String name);

    //*************************************************************不同类型的参数调用方式

    @PostMapping("addUser")
    String addUser(@RequestBody UserDTO userDTO);

    @PutMapping("updateUser")
    String updateUser(@RequestBody UserDTO userDTO);

    @GetMapping("getUser")
    String getUserDTOByName(@RequestParam String name); //TODO 观察这儿的@RequestParam没有指定名称

    @DeleteMapping("/delUser/{id}")
    String deleteUserDTOById(@PathVariable Integer id); //TODO 观察这儿的@PathVariable没有指定名称
}
