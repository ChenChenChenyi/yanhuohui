package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 不同文件类型的访问和下载
 */
@RestController
@RequestMapping(value = "/multipartfile")
public class MultipartFileController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/export")
    public String exportExcel(HttpServletResponse response) {
        System.out.println("成功到达到处excel....");
        String fileName = "test.xls";
        if (fileName == null || "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls")) {
                Boolean isOk = excelService.exportExcel(response, fileName, 1, 10);
                if (isOk) {
                    return "导出成功！";
                } else {
                    return "导出失败！";
                }
            }
            return "文件格式有误！";
        }
    }
}
