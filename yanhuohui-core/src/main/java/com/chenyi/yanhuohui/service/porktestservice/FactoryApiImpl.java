package com.chenyi.yanhuohui.service.porktestservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 猪肉工厂类的实现类
 */
@Service
@Slf4j
public class FactoryApiImpl implements FactoryApi{
    @Override
    public void supplyPork(Long weight) {
        log.info("call real factory to supply pork, weight: {}", weight);
    }
}
