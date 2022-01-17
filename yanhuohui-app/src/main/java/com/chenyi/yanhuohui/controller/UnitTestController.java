package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.CommonErrorCode;
import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import com.chenyi.yanhuohui.manager.PorkInst;
import com.chenyi.yanhuohui.service.porktestservice.PorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/unit-test")
public class UnitTestController {

    @Autowired
    private PorkService porkService;

    /**
     * 买猪肉的流程，设计的测试链路
     * @param weight
     * @param params
     * @return
     */
    @PostMapping("/buy")
    public ResponseEntity<PorkInst> buyPork(@RequestParam("weight") Long weight,
                                            @RequestBody Map<String,Object> params) {
        if (weight == null) {
            throw new SbcRuntimeException("invalid input: weight", CommonErrorCode.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(porkService.getPork(weight, params));
    }
}
