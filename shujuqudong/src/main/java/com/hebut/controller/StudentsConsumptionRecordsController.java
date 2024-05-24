package com.hebut.controller;

import cn.hutool.core.date.DateUtil;
import com.hebut.service.StudentsConsumptionRecordsService;
import com.hebut.utils.ResultUtil;
import com.hebut.vo.StudentsConsumptionRecordsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class StudentsConsumptionRecordsController {

    @Autowired
    private StudentsConsumptionRecordsService studentsConsumptionRecordsService;

    @GetMapping("/search-abnormal")
    public ResultUtil abnormal(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO, Integer pageNum, Integer pageSize) {
        return ResultUtil.ok("查询成功", studentsConsumptionRecordsService.abnormal(studentsConsumptionRecordsVO, pageNum, pageSize));
    }

    @GetMapping("/search-condition")
    public ResultUtil selectByCondition(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        return ResultUtil.ok("查询成功", studentsConsumptionRecordsService.selectByCondition(studentsConsumptionRecordsVO));
    }

    @GetMapping("/search-condition-all")
    public ResultUtil selectByConditionAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        return ResultUtil.ok("查询成功", studentsConsumptionRecordsService.selectByConditionAll(studentsConsumptionRecordsVO));
    }

    @GetMapping("/search-week")
    public ResultUtil selectByWeek(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        if(studentsConsumptionRecordsVO.getEND().equals("") || studentsConsumptionRecordsVO.getEND() == null){
            System.out.println("hello");
            String today = DateUtil.today();
            studentsConsumptionRecordsVO.setBEGIN(today);
            return ResultUtil.ok("查询成功", studentsConsumptionRecordsService.selectByWeek(studentsConsumptionRecordsVO));
        }
        else{
            System.out.println("world");
            return ResultUtil.ok("查询成功", studentsConsumptionRecordsService.selectByCondition(studentsConsumptionRecordsVO));
        }
    }

    @GetMapping("/search-week-all")
    public ResultUtil selectByWeekAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        return ResultUtil.ok("查询成功", studentsConsumptionRecordsService.selectByWeekAll(studentsConsumptionRecordsVO));
    }

}
