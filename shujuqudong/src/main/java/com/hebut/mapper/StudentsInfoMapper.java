package com.hebut.mapper;

import com.hebut.dto.StudentsInfoDTO;
import com.hebut.entity.BJSJ;
import com.hebut.entity.User;
import com.hebut.entity.XYSJ;
import com.hebut.entity.ZYSJ;
import com.hebut.vo.StudentsClassVO;
import com.hebut.vo.StudentsInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentsInfoMapper {

    List<StudentsInfoDTO> selectBy(StudentsInfoVO studentsInfoVO);

    User profile(User user);

    List<XYSJ> selectXY();

    List<String> selectNJ();

    List<ZYSJ> selectZY(ZYSJ zysj);

    List<BJSJ> selectBJ(StudentsClassVO studentsClassVO);




}
