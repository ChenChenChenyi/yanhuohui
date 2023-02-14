package com.chenyi.yanhuohui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class UploadFileController {

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file){
        return saveFile(file);
    }


    @PostMapping("/multiUpload")
    public Object multiUpload(@RequestParam("file")MultipartFile[] files){
        System.out.println("文件的个数:"+files.length);
        for (MultipartFile f : files){
            saveFile(f);
        }
        return "ok";
    }


    private Object saveFile(MultipartFile file){
        if (file.isEmpty()){
            return "未选择文件";
        }
        String filename = file.getOriginalFilename(); //获取上传文件原来的名称
        String filePath = "D:\\temp\\";
        File temp = new File(filePath);
        if (!temp.exists()){
            temp.mkdirs();
        }

        File localFile = new File(filePath+filename);
        try {
            file.transferTo(localFile); //把上传的文件保存至本地
            System.out.println(file.getOriginalFilename()+" 上传成功");
        }catch (IOException e){
            e.printStackTrace();
            return "上传失败";
        }

        return "ok";
    }
}
