package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.manager.PorkInst;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PorkServiceImpl implements PorkService{
    @Override
    public PorkInst getPork(Long weight, Map<String, Object> params) {
        //里面会去调用工厂，仓库的一些方法
        return null;
    }
}
