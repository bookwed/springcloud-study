package com.wei.service.impl;

import com.wei.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class GoodsServiceImpl implements GoodsService {
    private Logger logger = LoggerFactory.getLogger(getClass());


    //value值表示当哪些异常的时候触发重试，maxAttempts表示最大重试次数默认为3，delay表示重试的延迟时间，multiplier表示上一次延时时间是这一次的倍数
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    @Override
    public int getMinGoodsNum(int num) throws Exception{
        logger.info("getMinGoodsNum开始"+ LocalTime.now());
        if(num <= 0){
            throw new Exception("数量不对");
        }
        logger.info("getMinGoodsNum执行结束");
        return 0;
    }

    @Recover
    public int recover(Exception e){
        logger.warn("扣减库存失败");
        //TODO 这里可以记录错误日志到数据库
        return 10000;   //返回一个假数据
    }
}
