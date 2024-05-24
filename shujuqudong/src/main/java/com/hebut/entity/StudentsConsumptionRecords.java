package com.hebut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsConsumptionRecords {

    private String XH; // 学号

    private String XFDD; // 消费地点

    private String XFJE;  //消费金额

    private String XFSJ; // 消费时间

    private Integer ID;  // 消费ID

}
