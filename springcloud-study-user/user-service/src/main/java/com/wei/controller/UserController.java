package com.wei.controller;


import com.wei.user.common.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    //*************************************************************不同类型的参数调用方式

    @PostMapping("addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        logger.info("user-service的addUser方法执行了");
        //TODO 调用本地service执行真正的添加用户操作
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PutMapping("updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO){
        logger.info("user-service的updateUser方法执行了");
        Map<String,Object> map = new HashMap<>();
        map.put("name",userDTO.getNickname());
        map.put("id", userDTO.getId());
        return new ResponseEntity<Object>(map,HttpStatus.OK);
    }

    @GetMapping("getUser")
    public ResponseEntity<UserDTO> getUserDTOByName(@RequestParam String name) {
        logger.info("user-service提供了getUserDTOByName服务");
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(name);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delUser/{id}")
    public ResponseEntity<Integer> deleteUserDTOById(@PathVariable Integer id) {
        logger.info("user-service提供了deleteUserDTOById服务");
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }


}
