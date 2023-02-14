package com.chenyi.yanhuohui.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.chenyi.yanhuohui.constant.ExcelConstants;
import com.chenyi.yanhuohui.manager.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EasyExceGeneralDatalListener extends AnalysisEventListener<Map<Integer, String>>{
    /**
     * 处理业务逻辑的Service,也可以是Mapper
     */
    private ActResultLogService actResultLogService;

    /**
     * 用于存储读取的数据
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    public EasyExceGeneralDatalListener() {
    }

    public EasyExceGeneralDatalListener(ActResultLogService actResultLogService) {
        this.actResultLogService = actResultLogService;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        //数据add进入集合
        dataList.add(data);
        //size是否为100000条:这里其实就是分批.当数据等于10w的时候执行一次插入
        if (dataList.size() >= ExcelConstants.GENERAL_ONCE_SAVE_TO_DB_ROWS) {
            //存入数据库:数据小于1w条使用Mybatis的批量插入即可;
            saveData();
            //清理集合便于GC回收
            dataList.clear();
        }
    }

    /**
     * 保存数据到DB
     *
     * @param
     * @MethodName: saveData
     * @return: void
     */
    private void saveData() {
        actResultLogService.import2DBFromExcel10w(dataList);
        dataList.clear();
    }

    /**
     * Excel中所有数据解析完毕会调用此方法
     *
     * @param: context
     * @MethodName: doAfterAllAnalysed
     * @return: void
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        dataList.clear();
    }
}
