package com.hebut.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hebut.dto.StudentsInfoDTO;
import com.hebut.entity.BJSJ;
import com.hebut.entity.User;
import com.hebut.entity.XYSJ;
import com.hebut.entity.ZYSJ;
import com.hebut.mapper.StudentsInfoMapper;
import com.hebut.service.StudentsInfoService;
import com.hebut.vo.StudentsClassVO;
import com.hebut.vo.StudentsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsInfoServiceImpl implements StudentsInfoService {

    @Autowired
    private StudentsInfoMapper studentsInfoMapper;


    @Override
    public PageInfo<StudentsInfoDTO> selectBy(StudentsInfoVO studentsInfoVO, Integer pageNum, Integer pageSize) {
        // pageNum代表页码值，pageSize代表每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 使用PageInfo来封装查询的数据
        PageInfo<StudentsInfoDTO> pageInfo = new PageInfo<StudentsInfoDTO>(studentsInfoMapper.selectBy(studentsInfoVO));
        return pageInfo;
    }

    @Override
    public User profile(User user) {
        return studentsInfoMapper.profile(user);
    }

    @Override
    public PageInfo<XYSJ> selectXY(Integer pageNum, Integer pageSize) {
        // pageNum代表页码值，pageSize代表每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 使用PageInfo来封装查询的数据
        PageInfo<XYSJ> pageInfo = new PageInfo<XYSJ>(studentsInfoMapper.selectXY());
        return pageInfo;
    }

    @Override
    public PageInfo<String> selectNJ(Integer pageNum, Integer pageSize) {
        // pageNum代表页码值，pageSize代表每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 使用PageInfo来封装查询的数据
        PageInfo<String> pageInfo = new PageInfo<String>(studentsInfoMapper.selectNJ());
        return pageInfo;
    }

    @Override
    public PageInfo<ZYSJ> selectZY(ZYSJ zysj, Integer pageNum, Integer pageSize) {
        // pageNum代表页码值，pageSize代表每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 使用PageInfo来封装查询的数据
        PageInfo<ZYSJ> pageInfo = new PageInfo<ZYSJ>(studentsInfoMapper.selectZY(zysj));
        return pageInfo;
    }

    @Override
    public PageInfo<BJSJ> selectBJ(StudentsClassVO studentsClassVO, Integer pageNum, Integer pageSize) {
        // pageNum代表页码值，pageSize代表每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 使用PageInfo来封装查询的数据s
        PageInfo<BJSJ> pageInfo = new PageInfo<BJSJ>(studentsInfoMapper.selectBJ(studentsClassVO));
        return pageInfo;
    }


}
