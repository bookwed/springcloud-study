package com.wei.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wei.service.GoodsService;
import com.wei.service.IUserRemoteService;
import com.wei.user.common.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("goods")
@RestController
public class GoodsController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("loadBalancer")
    private RestTemplate loadBalancer;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private IUserRemoteService userRemoteService;

    /**
     * 使用RestTemplate来调用其他服务
     * @param name
     * @return
     */
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


    // 实现轮询选择服务器
    int count = 0;

    /**
     * 手动实现负载均衡
     * @param name
     * @return
     */
    @GetMapping("loadBalance")
    public String loadBalance(String name){
        List<ServiceInstance> instanceList = discoveryClient.getInstances("user-service");
        ServiceInstance instance = instanceList.get(count % instanceList.size());
        count++;    //下一次要调用的服务器
        String host = instance.getHost();
        int port = instance.getPort();
        String s = restTemplate.getForObject("http://" + host + ":" + port + "/user/balance?name={1}", String.class, name);
        return "手动实现负载均衡，注意看端口的变化：" + s;
    }


    /**
     * 使用 @LoadBalanced 实现负载均衡
     * @param name
     * @return
     */
    @GetMapping("autoLoadBalancer")
    public String autoLoadBalancer(String name){
        String s = loadBalancer.getForObject("http://user-service/user/balance?name={1}", String.class, name);
        return "自动实现负载均衡，注意端口变化：" + s;
    }

    /**
     * 测试retry功能
     * @param num
     * @return
     * @throws Exception
     */
    @GetMapping("retry")
    public String testRetry(int num) throws Exception{
        int minNum = goodsService.getMinGoodsNum(num == 0 ? 1: num);
        logger.info("剩余的数量==="+minNum);
        return "测试重试功能：" + minNum;
    }

    /**
     * 使用feign的方式调用user-service提供的接口
     * @param name
     * @return
     */
    @GetMapping("helloByFeign")
    public String helloByFeign(String name){
        String result = userRemoteService.hello(name);
        return "使用Feign来调用user-service的hello接口，返回值是：" + result;
    }

    //*************************************************************不同类型的参数调用方式
    @PostMapping("addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){
        logger.info("goods-service调用了user-service的增加用户接口");
        String s = userRemoteService.addUser(userDTO);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PutMapping("updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO){
        String s = userRemoteService.updateUser(userDTO);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping("getUser")
    public ResponseEntity<String> getUserDTOByName(String name){
        String s = userRemoteService.getUserDTOByName(name);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @DeleteMapping("delUser/{id}")
    public ResponseEntity<String> getUserDTOByName(@PathVariable Integer id){
        String s = userRemoteService.deleteUserDTOById(id);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    /**
     * Ribbon中使用熔断器
     */
    @HystrixCommand(fallbackMethod = "testHystrixFallback")
    @GetMapping("testHystrix")
    public String testHystrix(String name){
        String s = loadBalancer.getForObject("http://user-service/user/balance?name={1}", String.class, name);
        return "测试熔断器的使用：" + s;
    }

    public String testHystrixFallback(String name){
     logger.info("熔断，默认回调函数");
     return "默认用户：name=张三";
    }

}
