package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.chenyi.yanhuohui.manager.PorkInst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PorkServiceImpl implements PorkService{

    @Autowired
    private FactoryApi factoryApi;

    @Autowired
    private WareHouseApi wareHouseApi;

    @Override
    public PorkInst getPork(Long weight, Map<String, Object> params) {
        Long sum = wareHouseApi.queryPorkSum();
        if(weight.compareTo(sum)>0){
            factoryApi.supplyPork(100L);
            throw new SbcRuntimeException("库存不足，已通知仓库进货，请稍后重新购买！");
        }else {
            return wareHouseApi.packagePork(weight,params);
        }

    }
}
