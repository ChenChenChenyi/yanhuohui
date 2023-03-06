package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.manager.PorkInst;

import java.util.Map;

/**
 * 猪肉仓库接口
 */
public interface WareHouseApi {

    /**
     * 查询仓库猪肉总量
     * @return
     */
    Long queryPorkSum();

    /**
     *
     * @param weight
     * @param params
     * @return
     */
    PorkInst packagePork(Long weight, Map<String, Object> params);
}
