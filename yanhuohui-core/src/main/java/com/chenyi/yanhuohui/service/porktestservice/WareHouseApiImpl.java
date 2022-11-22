package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.chenyi.yanhuohui.manager.PorkInst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class WareHouseApiImpl implements WareHouseApi{

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Long queryPorkSum() {
        return Long.valueOf(managerRepository.queryPork());
    }

    @Override
    public PorkInst packagePork(Long weight, Map<String, Object> params) {
        log.info("call real warehouse to package, weight: {}", weight);
        return PorkInst.builder().weight(weight).paramsMap(params).build();
    }
}
