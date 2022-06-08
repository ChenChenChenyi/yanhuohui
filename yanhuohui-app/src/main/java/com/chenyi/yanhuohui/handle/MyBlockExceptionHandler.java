package com.chenyi.yanhuohui.handle;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.info("UrlBlockHandler.....................................");
        // 自定义封装的类，可以定义
        BaseResponse<T> restObject = null;

        if (e instanceof FlowException) {
            // 限流
            restObject = BaseResponse.info("5001","sentinel接口限流");
        } else if (e instanceof DegradeException) {
            // 降级
            restObject = BaseResponse.info("5002","sentinel服务降级");
        } else if (e instanceof ParamFlowException) {
            // 热点参数
            restObject = BaseResponse.info("5003","sentinel热点参数限流");
        } else if (e instanceof SystemBlockException) {
            // 系统保护
            restObject = BaseResponse.info("5004","sentinel触发系统保护规则");
        } else if (e instanceof AuthorityException) {
            // 授权规则
            restObject = BaseResponse.info("5005","sentinel授权规则不通过");
        }

        //返回json数据
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //springmvc 的一个json转换类 （jackson）
        new ObjectMapper().writeValue(response.getWriter(), restObject);
        //重定向
        //response.sendRedirect("http://www.baidu.com");
    }
}
