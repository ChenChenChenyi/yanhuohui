package com.chenyi.yanhuohui.manager;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ExcelManager implements Serializable {
    private static final long serialVersionUID = 263847466609415156L;

    @ExcelProperty(value = "id")
    private Long id;

    @ExcelProperty(value = "名字")
    private String name;

    @ExcelProperty(value = "角色")
    private String role;
}
