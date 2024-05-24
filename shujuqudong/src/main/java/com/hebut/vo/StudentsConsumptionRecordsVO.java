package com.hebut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsConsumptionRecordsVO {

    private String XH; // 学号

    private String TIME; // 系统当前时间

    private String BEGIN = ""; // 时间段起点

    private String END = ""; // 时间段终点

//    private String YEAR; // 年
//
//    private String MONTH; // 月
//
//    private String DAY; // 日

}
