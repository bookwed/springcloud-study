package com.wei;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableRetry    //启用重试功能
@EnableFeignClients //开启Feign支持
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //使用 @LoadBalanced 实现负载均衡，默认采用轮询策略
    @Bean
    @LoadBalanced
    RestTemplate loadBalancer(){
        return new RestTemplate();
    }

    // 如果希望采用其它策略，则指定IRule实现
    // 这里是采用了随机策略
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
