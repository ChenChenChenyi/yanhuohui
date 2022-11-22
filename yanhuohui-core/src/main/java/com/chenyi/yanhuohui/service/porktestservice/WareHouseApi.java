package com.chenyi.yanhuohui.service.porktestservice;

import com.chenyi.yanhuohui.manager.PorkInst;

import java.util.Map;

/**
 * 猪肉仓库接口
 */
public interface WareHouseApi {

    Long queryPorkSum();

    PorkInst packagePork(Long weight, Map<String, Object> params);
}
