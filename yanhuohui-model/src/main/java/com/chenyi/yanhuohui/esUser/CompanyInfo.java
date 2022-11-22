package com.chenyi.yanhuohui.esUser;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class CompanyInfo {

    @Field(type = FieldType.Long)
    private Long companyId;

    @Field(type = FieldType.Keyword)
    private String companyName;

}
