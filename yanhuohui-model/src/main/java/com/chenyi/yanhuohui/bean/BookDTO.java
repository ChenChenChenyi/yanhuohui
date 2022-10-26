package com.chenyi.yanhuohui.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private  String id; //编号

    private String bookName;  //书名

    private String content;     //内容主题

    private int pagecount;      //多少页
}
