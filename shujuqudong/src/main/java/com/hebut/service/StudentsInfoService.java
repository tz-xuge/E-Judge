package com.hebut.service;

import com.github.pagehelper.PageInfo;
import com.hebut.dto.StudentsInfoDTO;
import com.hebut.entity.BJSJ;
import com.hebut.entity.User;
import com.hebut.entity.XYSJ;
import com.hebut.entity.ZYSJ;
import com.hebut.vo.StudentsClassVO;
import com.hebut.vo.StudentsInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentsInfoService {

    PageInfo<StudentsInfoDTO> selectBy(StudentsInfoVO studentsInfoVO, Integer pageNum, Integer pageSize);

    User profile(User user);

    PageInfo<XYSJ> selectXY(Integer pageNum, Integer pageSize);

    PageInfo<String> selectNJ(Integer pageNum, Integer pageSize);

    PageInfo<ZYSJ> selectZY(ZYSJ zysj, Integer pageNum, Integer pageSize);

    PageInfo<BJSJ> selectBJ(StudentsClassVO studentssClassVO, Integer pageNum, Integer pageSize);


}
