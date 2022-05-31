package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 下载文件的两种方法
 */
@RestController
@RequestMapping(value = "/multipartfile")
public class MultipartFileController {

    @Autowired
    private ExcelService excelService;

    /**
     * 第一种采用了Java中的File文件资源，然后通过写response的输出流，放回文件
     * @param response
     * @return
     */
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

    /**
     *  第二种是通过封装ResponseEntity，将文件流写入body中。这里注意一点，
     *  就是文件的格式需要根据具体文件的类型来设置，一般默认为application/octet-stream。
     *  文件头中设置缓存，以及文件的名字。文件的名字写入了，都可以避免出现文件随机产生名字，而不能识别的问题。
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/media", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(Long id)
            throws IOException {
        String filePath = "E:/" + id + ".rmvb";
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
