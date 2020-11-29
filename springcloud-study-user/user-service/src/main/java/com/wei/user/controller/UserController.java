package com.wei.user.controller;


import com.wei.user.common.dto.UserDTO;
import com.wei.user.domain.entity.RegUser;
import com.wei.user.service.IRegUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private IRegUserService regUserService;

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
        System.out.println("getUser："+port);
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(name);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delUser/{id}")
    public ResponseEntity<Integer> deleteUserDTOById(@PathVariable Integer id) {
        logger.info("user-service提供了deleteUserDTOById服务");
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }

    //******整理架构之后，测试调用是否成功

    /**
     * 注意点：
     *  1、参数不加任何注解，后台接收不到参数
     *  2、POST请求，单个参数传递，单个参数接收，postman发送请求时，选择form-data
     *  3、POST请求，json格式传递，实体接收，postman发送请求时，选择raw和json格式，参考：updateUser2
     *  4、GET请求，单个参数接收，路径格式：xxx?id=1&password=asdf，参考：updateUser3
     *  5、GET请求，实体接收，路径格式：xxx?id=1&password=asdf，参考：updateUser4
     *
     *  6、POST请求，map接收，前端传json数据，参考：updateUser5
     *  7、GET请求，map接收，路径格式：xxx?id=1&password=asdf，参考：updateUser6
     *
     *  8、POST请求，List<Object>，以集合的方式接收前端传过来的多个对象，参考：updateUser7
     *  9、POST请求，List<Map>,以集合的方式接收前端传过来的多个对象，参考：updateUser8
     *  10、POST请求，List<Integer>，以集合的方式接收前端传过来的多个值，参考：updateUser9
     *
     *
     *
     * @param id
     * @param password
     * @return
     */
    @PostMapping("updateUser")
    public String updateUser(@RequestParam("id") Long id, @RequestParam("password") String password){
        return regUserService.updateUser(id, password) + "";
    }

    /**
     *
     * @param user
     * @return
     */
    @PostMapping("updateUser2")
    public String updateUser2(@RequestBody RegUser user){
        return regUserService.updateUser(user.getId(), user.getPassword()) + "";
    }

    /**
     * GET请求，映射单个参数
     * @param id
     * @param password
     * @return
     */
    @GetMapping("updateUser3")
    public String updateUser3(@RequestParam("id") Long id, @RequestParam("password") String password){
        return regUserService.updateUser(id, password) + "";
    }

    /**
     * GET请求，映射后台实体
     * @param user
     * @return
     */
    @GetMapping("updateUser4")
    public String updateUser4(RegUser user){
        return regUserService.updateUser(user.getId(), user.getPassword()) + "";
    }

    /**
     * POST请求，接收json数据，map接收
     * @param map
     * @return
     */
    @PostMapping("updateUser5")
    public String updateUser5(@RequestBody Map<String,String> map){
        return regUserService.updateUser(Long.valueOf(map.get("id")), map.get("password")) + "";
    }

    /**
     * GET请求，地址路径中传递，map接收
     * @param map
     * @return
     */
    @GetMapping("updateUser6")
    public String updateUser6(@RequestParam Map<String,String> map){
        return regUserService.updateUser(Long.valueOf(map.get("id")), map.get("password")) + "";
    }

    /**
     * POST请求，json传递多个对象，后台用List<Object>接收
     * json格式：
     * [{
     * 	"id": 1,
     * 	"password": "aaaaa"
     * },{
     * 	"id": 2,
     * 	"password": "bbbb"
     * }]
     * @param users
     * @return
     */
    @PostMapping("updateUser7")
    public String updateUser7(@RequestBody List<RegUser> users){
        System.out.println(users);
        return "1";
    }

    /**
     * 同updateUser7，只是list内部是map对象
     * @param users
     * @return
     */
    @PostMapping("updateUser8")
    public String updateUser8(@RequestBody List<Map<String,String>> users){
        System.out.println(users);
        return "1";
    }

    /**
     * 同updateUser7，只是list内部是基本数据类型，不再是对象
     * 格式如：[1,2,3,4]
     * @param list
     * @return
     */
    @PostMapping("updateUser9")
    public String updateUser9(@RequestBody List<Integer> list){
        System.out.println(list);
        return "1";
    }

    /**
     * 格式同：updateUser7
     * @param regUsers
     * @return
     */
    @PostMapping("updateUser10")
    public String updateUser10(@RequestBody RegUser[] regUsers){
        System.out.println(regUsers);
        return "1";
    }

    /**
     * 格式同：updateUser7
     * @param map
     * @return
     */
    @PostMapping("updateUser11")
    public String updateUser11(@RequestBody Map[] map){
        System.out.println(map);
        return "1";
    }

    /**
     * 同updateUser7，只是list内部是基本数据类型，不再是对象
     * @param ints
     * @return
     */
    @PostMapping("updateUser12")
    public String updateUser12(@RequestBody Integer[] ints){
        System.out.println(ints);
        return "1";
    }

}
