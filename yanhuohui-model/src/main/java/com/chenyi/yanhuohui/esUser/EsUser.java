package com.chenyi.yanhuohui.esUser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(indexName = "yanhuohui", type = "user", refreshInterval = "0s",createIndex = false)
public class EsUser {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private  String myId; //编号

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Text)
    private String realname;

    private String password;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Integer)
    private Integer level;

    //这三个注解是为了前台序列化java8 LocalDateTime使用的，需要引入jsr310的包才可以使用
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
    @Field(index = false,type = FieldType.Date,format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime birth;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Long)
    private List<Long> authorites;

    @Field(type = FieldType.Nested)
    private List<CompanyInfo> companyInfos;
}
