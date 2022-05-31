package com.chenyi.yanhuohui.user;

import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.index.qual.LowerBoundUnknown;

@Data
@Builder
public class ExcelUser {

    private String id;

    private String userName;

    private String password;

    private Integer enable;
}
