package com.wei;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //使用 @LoadBalanced 实现负载均衡
    @Bean
    @LoadBalanced
    RestTemplate loadBalancer(){
        return new RestTemplate();
    }
}
