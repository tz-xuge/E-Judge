package com.hebut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsInfoDTO {

    private String XH; // 学号

    private String XM; // 姓名

    private String XB; // 性别

    private String JG; // 籍贯

    private String MZ; // 名族

    private String BJMC; // 班级名称

    private String ZYMC; //专业名称

    private String NJ; // 年纪

    private String XYMC; // 学院名称


}
