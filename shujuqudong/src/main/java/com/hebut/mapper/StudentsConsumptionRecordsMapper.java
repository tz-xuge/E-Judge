package com.hebut.mapper;

import com.hebut.dto.StudentsInfoDTO;
import com.hebut.entity.StudentsConsumptionRecords;
import com.hebut.vo.StudentsConsumptionRecordsVO;
import com.hebut.vo.StudentsInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentsConsumptionRecordsMapper {

    List<StudentsInfoDTO> selectBy(List<String> list);

    List<StudentsConsumptionRecords> abnormal(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    // 提取指定时间数据
    List<StudentsConsumptionRecords> selectByCondition(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    List<StudentsConsumptionRecords> selectByConditionAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    // 提取近7天数据
    List<StudentsConsumptionRecords> selectByWeek(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);

    List<StudentsConsumptionRecords> selectByWeekAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO);


}
