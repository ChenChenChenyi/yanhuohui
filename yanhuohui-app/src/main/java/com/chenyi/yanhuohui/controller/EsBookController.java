package com.chenyi.yanhuohui.controller;

import com.chenyi.yanhuohui.bean.BookDTO;
import com.chenyi.yanhuohui.common.base.entity.BaseResponse;
import com.chenyi.yanhuohui.esbook.Book;
import com.chenyi.yanhuohui.request.EsBookQueryPageRequest;
import com.chenyi.yanhuohui.request.EsBookSaveRequest;
import com.chenyi.yanhuohui.request.EsBookUpdateRequest;
import com.chenyi.yanhuohui.service.EsBookService;
import com.chenyi.yanhuohui.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chenyi.yanhuohui.request.EsUserSaveRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/esbook")
@Slf4j
public class EsBookController {
    @Autowired
    private EsBookService esBookService;

    //使用JPA提供的接口对es进行crud
    @RequestMapping(value = "/save-new-book")
    public void saveNewBook(@RequestBody EsBookSaveRequest esBookSaveRequest){
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(esBookSaveRequest,bookDTO);
        esBookService.save(bookDTO);
    }

    @RequestMapping(value = "/update-book")
    public void updateBook(@RequestBody EsBookUpdateRequest esBookUpdateRequest){
        BookDTO bookVO = new BookDTO();
        BeanUtils.copyProperties(esBookUpdateRequest,bookVO);
        esBookService.update(bookVO);
    }

    @RequestMapping(value = "/find-all")
    public BaseResponse<List<Book>> queryByName(@RequestBody EsBookQueryPageRequest esBookUpdateRequest){
        Pageable pageable = PageRequest.of(esBookUpdateRequest.getPageNum(),esBookUpdateRequest.getPageSize());
        List<Book> books = esBookService.findAll(pageable);
        return BaseResponse.success(books);
    }

    @RequestMapping(value = "/query-by-name")
    public BaseResponse<List<Book>> queryByName(@RequestBody EsBookUpdateRequest esBookUpdateRequest){
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(esBookUpdateRequest,bookDTO);
        List<Book> books = esBookService.queryByName(bookDTO);
        //BeanUtils拷贝List不成功，以后再研究
//        List<BookVO> bookVOS = new ArrayList<>();
//        BeanUtils.copyProperties(books,bookVOS);
        return BaseResponse.success(books);
    }

    @RequestMapping(value = "/query-by-condition")
    public BaseResponse<List<Book>> queryByCondition(@RequestBody EsBookUpdateRequest esBookUpdateRequest){
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(esBookUpdateRequest,bookDTO);
        List<Book> books = esBookService.queryBuilder(bookDTO);
        return BaseResponse.success(books);
    }


}
