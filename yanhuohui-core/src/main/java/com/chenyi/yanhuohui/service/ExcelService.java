//package com.chenyi.yanhuohui.service;
//
//import com.chenyi.yanhuohui.user.ExcelData;
//import com.chenyi.yanhuohui.user.ExcelUser;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//public class ExcelService {
//    public Boolean exportExcel(HttpServletResponse response, String fileName, Integer pageNum, Integer pageSize) {
//        log.info("导出数据开始。。。。。。");
//        //查询数据并赋值给ExcelData
//        List<ExcelUser> userList = new ArrayList<>();
//        System.out.println(userList.size() + "size");
//        List<String[]> list = new ArrayList<String[]>();
//        for (ExcelUser user : userList) {
//            String[] arrs = new String[4];
//            arrs[0] = String.valueOf(user.getId());
//            arrs[1] = user.getUserName();
//            arrs[2] = user.getPassword();
//            arrs[3] = String.valueOf(user.getEnable());
//            list.add(arrs);
//        }
//        //表头赋值
//        String[] head = {"序列", "用户名", "密码", "状态"};
//        ExcelData data = new ExcelData();
//        data.setHead(head);
//        data.setData(list);
//        data.setFileName(fileName);
//        //实现导出
//        try {
//            ExcelUtil.exportExcel(response, data);
//            log.info("导出数据结束。。。。。。");
//            return true;
//        } catch (Exception e) {
//            log.info("导出数据失败。。。。。。");
//            return false;
//        }
//    }
//
//}
