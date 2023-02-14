package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.VO.ManagerVO;
import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.manager.Manager;
import com.chenyi.yanhuohui.manager.ManagerRepository;
import com.chenyi.yanhuohui.request.ManagerQueryRequest;
import com.chenyi.yanhuohui.service.DataTransportService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/jpa")
//@AllArgsConstructor
public class JpaController {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private DataTransportService dataTransportService;

//    @Autowired
//    void setManagerRepository(ManagerRepository managerRepository) {
//        this.managerRepository = managerRepository;
//    }

    @PostMapping(value = "/find-all")
    public String findAll(){
        List<Manager> list = managerRepository.findAll();
        System.out.println(list);
        return list.toString();
        //return BaseResponse.SUCCESSFUL();
    }

    @PostMapping(value = "/find-by-name")
    @Transactional
    public String findByName(@RequestParam("name") String name) throws InterruptedException {
        List<Manager> list = managerRepository.findByName(name);
        TimeUnit.SECONDS.sleep(60);
        System.out.println(list);
        return list.toString();
        //return BaseResponse.SUCCESSFUL();
    }

    @PostMapping(value = "/find-by-condition")
    public List<ManagerVO> findByCondition(@RequestBody ManagerQueryRequest managerQueryRequestmanagerQueryRequest){
        List<Manager> managers = managerRepository.findAll(managerQueryRequestmanagerQueryRequest.getWhereCriteria());
        List<ManagerVO> managerVOS = new ArrayList<>();
        for (Manager manager : managers) {
            ManagerVO managerVO = new ManagerVO();
            BeanUtils.copyProperties(manager, managerVO);
            managerVOS.add(managerVO);
        }
        return managerVOS;
    }

    @GetMapping("/export-manager")
    public String exportExcel() {
        System.out.println("开始导出manager表的数据......");
        dataTransportService.import2DBFromExcel10w();
        return null;
    }
}
