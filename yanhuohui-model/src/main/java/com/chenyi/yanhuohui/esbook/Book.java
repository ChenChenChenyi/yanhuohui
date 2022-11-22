package com.chenyi.yanhuohui.esbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "yanhuohui-book", type="book", createIndex = false) //indexName设置索引库的名称  type设置文档的类型
public class Book {
    //标识主键
    @Id
    @Field(type = FieldType.Keyword)
    private  String id; //编号

    @Field(type = FieldType.Keyword)
    private  String myId; //编号

    @Field(type = FieldType.Text)
    private String bookName;  //书名

    @Field(type = FieldType.Text)
    private String content;     //内容主题

    @Field(type = FieldType.Integer)
    private Integer pagecount;      //多少页

    @Field(type = FieldType.Double,index = false)
    private BigDecimal price;
}
