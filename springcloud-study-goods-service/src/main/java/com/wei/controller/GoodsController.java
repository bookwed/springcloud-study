package com.wei.controller;


import com.wei.service.GoodsService;
import com.wei.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private UserService userService;

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
        String result = userService.hello(name);
        return "使用Feign来调用user-service的hello接口，返回值是：" + result;
    }

}
