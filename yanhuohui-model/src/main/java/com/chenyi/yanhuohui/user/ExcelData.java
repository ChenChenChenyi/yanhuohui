package com.chenyi.yanhuohui.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExcelData {
    private String[] head;
    private List<String[]> data;
    private String fileName;
}
