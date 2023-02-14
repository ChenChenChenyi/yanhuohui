package com.chenyi.yanhuohui.constant;

public final class ExcelConstants {
    private ExcelConstants(){}

    //每个sheet写入100万条数据
    public final static Integer PER_SHEET_ROW_COUNT = 10;

    //每批次写入20万条数据
    public final static Integer PER_WRITE_ROW_COUNT = 2;

    //一次往数据库写入10000条数据
    public final static Integer GENERAL_ONCE_SAVE_TO_DB_ROWS = 1;
}
