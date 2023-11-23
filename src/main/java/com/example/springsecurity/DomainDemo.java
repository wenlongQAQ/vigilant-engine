package com.example.springsecurity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DomainDemo {
    @ExcelProperty("标题")
    private String title;
    @ExcelProperty("内容")
    private String text;
}
