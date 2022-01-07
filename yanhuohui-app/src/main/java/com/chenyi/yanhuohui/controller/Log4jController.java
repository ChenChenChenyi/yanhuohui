package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.common.base.entity.CommonErrorCode;
import com.chenyi.yanhuohui.common.base.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/log")
public class Log4jController {

    @RequestMapping("/log-level")
    public void logLevel() {
        log.debug("debug级别的日志。");
        log.info("info级别的日志。");
        log.warn("warn级别的日志。");
        log.error("error级别的日志。");
    }
}
