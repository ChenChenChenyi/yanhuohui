package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.manager.PorkInst;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface PorkService {
    /**
     * 获取猪肉打包实例
     *
     * @param weight 重量
     * @param params 额外信息
     * @return {@link PorkInst} - 指定数量的猪肉实例
     * @throws BaseBusinessException 如果猪肉库存不足，返回异常，同时后台告知工厂
     */
    //getPork里的具体实现逻辑是：
    //查询库存数量，若满足则调用仓库接口打包出库，不满足则跑出异常然后调用工厂接口进货
    //查询库存用的是managerRepository模拟的
    PorkInst getPork(Long weight, Map<String, Object> params);
}
