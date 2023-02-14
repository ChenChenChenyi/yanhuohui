package com.chenyi.yanhuohui.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.chenyi.yanhuohui.constant.ExcelConstants;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class DataTransportService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ActResultLogService actResultLogService;

    //导出逻辑代码
    public void dataExport300w(HttpServletResponse response) {
        {
            OutputStream outputStream = null;
            try {
                long startTime = System.currentTimeMillis();
                System.out.println("导出开始时间:" + startTime);

                outputStream = response.getOutputStream();
                ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);
                String fileName = new String(("excel100w").getBytes(), "UTF-8");

                //title
                Table table = new Table(1);
                List<List<String>> titles = new ArrayList<List<String>>();
                titles.add(Arrays.asList("ID"));
                titles.add(Arrays.asList("姓名"));
                titles.add(Arrays.asList("角色"));
                table.setHead(titles);

//                //模拟统计查询的数据数量这里模拟100w
//                int count = 3000001;
//                //记录总数:实际中需要根据查询条件进行统计即可
//                Integer totalCount = actResultLogMapper.findActResultLogByCondations(count);
                Integer totalCount = 19;
                //每一个Sheet存放100w条数据
                Integer sheetDataRows = ExcelConstants.PER_SHEET_ROW_COUNT;
                //每次写入的数据量20w
                Integer writeDataRows = ExcelConstants.PER_WRITE_ROW_COUNT;
                //计算需要的Sheet数量
                Integer sheetNum = totalCount % sheetDataRows == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
                //计算一般情况下每一个Sheet需要写入的次数(一般情况不包含最后一个sheet,因为最后一个sheet不确定会写入多少条数据)
                Integer oneSheetWriteCount = sheetDataRows / writeDataRows;
                //计算最后一个sheet需要写入的次数
                Integer lastSheetWriteCount = totalCount % sheetDataRows == 0 ? oneSheetWriteCount : (totalCount % sheetDataRows % writeDataRows == 0 ? (totalCount / sheetDataRows / writeDataRows) : (totalCount / sheetDataRows / writeDataRows + 1));

                //开始分批查询分次写入
                //注意这次的循环就需要进行嵌套循环了,外层循环是Sheet数目,内层循环是写入次数
                List<List<String>> dataList = new ArrayList<>();
                for (int i = 0; i < sheetNum; i++) {
                    //创建Sheet
                    Sheet sheet = new Sheet(i, 0);
                    sheet.setSheetName("测试Sheet1" + i);
                    //循环写入次数: j的自增条件是当不是最后一个Sheet的时候写入次数为正常的每个Sheet写入的次数,如果是最后一个就需要使用计算的次数lastSheetWriteCount
                    for (int j = 0; j < (i != sheetNum - 1 ? oneSheetWriteCount : lastSheetWriteCount); j++) {
                        //集合复用,便于GC清理
                        dataList.clear();
                        //分页查询一次20w
//                        PageHelper.startPage(j + 1 + oneSheetWriteCount * i, writeDataRows);
//                        List<Manager> reslultList = actResultLogMapper.findByPage100w();
                        Page<Manager> managers = managerRepository.findAll(PageRequest.of(j + 1 + oneSheetWriteCount * i, writeDataRows));
                        if (!CollectionUtils.isEmpty(managers.getContent())) {
                            managers.getContent().forEach(item -> {
                                dataList.add(Arrays.asList(item.getId().toString(), item.getName(), item.getRole()));
                            });
                        }
                        //写数据
                        writer.write0(dataList, sheet, table);
                    }
                }

                // 下载EXCEL
                response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO-8859-1") + ".xlsx");
                response.setContentType("multipart/form-data");
                response.setCharacterEncoding("utf-8");
                writer.finish();
                outputStream.flush();
                //导出时间结束
                long endTime = System.currentTimeMillis();
                System.out.println("导出结束时间:" + endTime + "ms");
                System.out.println("导出所用时间:" + (endTime - startTime) / 1000 + "秒");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //导入的代码逻辑
    public void import2DBFromExcel10w(){
        String fileName = "D:\\tmp\\文件导入测试文件.xlsx";
        //记录开始读取Excel时间,也是导入程序开始时间
        long startReadTime = System.currentTimeMillis();
        System.out.println("------开始读取Excel的Sheet时间(包括导入数据过程):" + startReadTime + "ms------");
        //读取所有Sheet的数据.每次读完一个Sheet就会调用这个方法
        EasyExcel.read(fileName,new EasyExceGeneralDatalListener(actResultLogService)).doReadAll();
        long endReadTime = System.currentTimeMillis();
        System.out.println("------结束读取Excel的Sheet时间(包括导入数据过程):" + endReadTime + "ms------");
    }
}
