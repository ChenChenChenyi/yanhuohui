package com.chenyi.yanhuohui.service.porktestservice;

/**
 * 猪肉工厂类接口
 */
public interface FactoryApi {
    /**
     * 提供猪肉，仓库数量不够时，调用次接口进货来增加库存。
     * @param weight
     */
    void supplyPork(Long weight);
}
