package com.chenyi.yanhuohui.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsBookUpdateRequest implements Serializable {
    private static final long serialVersionUID = 2719706914335518933L;

    private  String id; //编号

    private String bookName;  //书名

    private String content;     //内容主题

    private int pagecount;      //多少页
}
