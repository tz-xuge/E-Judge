package com.hebut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsInfoVO {

    private String XH;   // 学号
    private String XM;   // 姓名
    private String XB;   // 性别
    private String JG;   // 籍贯
    private String MZ;   // 名族
    private String BJDM; // 班级代码
    private String BJMC; // 班级名称
    private String ZYMC; // 专业名称
    private String NJ;   // 年级
    private String XYMC; // 学院名称
    private String XYDM; // 学院代码
    private String ZYDM; // 专业代码


}
