package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.CommonErrorCode;
import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.service.GoodsImportService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/goods-import")
@Slf4j
public class GoodsImportController {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private GoodsImportService goodsImportService;

    @RequestMapping(value = "/import-goods-by-groups")
    public void importGoodsByGroups(){
        RLock rLock = redissonClient.getFairLock("goods_import_lock");
        try{
            boolean result = rLock.tryLock();
            if(!result){
                log.info("1.商品导入主线程正在运行中，请稍后再试！");
                return;
            }
            log.info("1.主流程开始=============================");

            //主业务逻辑
            TimeUnit.SECONDS.sleep(10);
            //先从数据库取出需要处理的数据总量，算出总页数
            //然后循环处理每一页
            //for(::){
                try{
                    //取出数据
                    //在这个地方先做数据的校验，剔除不符合格式要求的数据
                    List<String> inputs = new ArrayList<>();
                    goodsImportService.importByGroup(inputs);
                }catch (Exception e){
                    log.info("1.第{}组的数据处理发生异常！");
                }
            //}
            log.info("1.主流程结束=============================");
        }catch (Exception e){
            log.error("1.商品导入主线程运行发生错误，已终止！");
            throw new SbcRuntimeException(CommonErrorCode.FAILED);
        }finally {
            if(rLock.isHeldByCurrentThread()){
                rLock.unlock();
                log.info("1.商品导入redis锁释放！");
            }
        }

    }
}
