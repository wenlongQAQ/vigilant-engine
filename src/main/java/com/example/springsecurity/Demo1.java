package com.example.springsecurity;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {
    public static void main(String[] args) {
        String fileName = "C:\\lwl\\work\\demo.xlsx";
        List<DomainDemo> demos = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            DomainDemo domainDemo = new DomainDemo();
            domainDemo.setText(""+i);
            domainDemo.setTitle("title"+i);
            demos.add(domainDemo);
        }

        EasyExcel.write(fileName, DomainDemo.class)
                .sheet("模板").doWrite(() ->{
                    return demos;
                });
    }
}
