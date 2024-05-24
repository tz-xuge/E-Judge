package com.hebut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BJSJ {

    private String BJMC; //班级名称

    private String BJDM; //班级代码

    private String NJ;  // 年纪

    private String ZYDM; // 专业代码

}
