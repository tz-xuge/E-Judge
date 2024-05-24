package com.hebut.service;

import com.github.pagehelper.PageInfo;
import com.hebut.dto.StudentsConsumeDTO;
import com.hebut.dto.StudentsInfoDTO;
import com.hebut.entity.StudentsConsumptionRecords;
import com.hebut.vo.StudentsConsumptionRecordsVO;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentsConsumptionRecordsService {

    PageInfo<StudentsInfoDTO> abnormal(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO, Integer pageNum, Integer pageSize);

    StudentsConsumeDTO selectByCondition(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    StudentsConsumeDTO selectByConditionAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    StudentsConsumeDTO selectByWeek(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    StudentsConsumeDTO selectByWeekAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);




}
