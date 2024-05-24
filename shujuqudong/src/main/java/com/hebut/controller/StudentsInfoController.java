package com.hebut.controller;

import com.hebut.entity.User;
import com.hebut.entity.ZYSJ;
import com.hebut.service.StudentsInfoService;
import com.hebut.utils.ResultUtil;
import com.hebut.vo.StudentsClassVO;
import com.hebut.vo.StudentsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class StudentsInfoController {

    @Autowired
    private StudentsInfoService studentsInfoService;

    @GetMapping("/search-info")
    public ResultUtil info(StudentsInfoVO studentsInfoVO, Integer pageNum, Integer pageSize) {
        return ResultUtil.ok("查询成功", studentsInfoService.selectBy(studentsInfoVO, pageNum, pageSize));
    }

    @GetMapping("/profile")
    public ResultUtil profile(User user) {
        return ResultUtil.ok("查询成功", studentsInfoService.profile(user));
    }


    @GetMapping("/search-institute")
    public ResultUtil xueyuan(Integer pageNum, Integer pageSize) {
        return ResultUtil.ok("查询成功", studentsInfoService.selectXY(pageNum, pageSize));
    }

    @GetMapping("/search-enroll")
    public ResultUtil nianji(Integer pageNum, Integer pageSize) {
        return ResultUtil.ok("查询成功", studentsInfoService.selectNJ(pageNum, pageSize));
    }

    @GetMapping("/search-major")
    public ResultUtil zhuanye(ZYSJ zysj, Integer pageNum, Integer pageSize) {
        return ResultUtil.ok("查询成功", studentsInfoService.selectZY(zysj, pageNum, pageSize));
    }

    @GetMapping("/search-class")
    public ResultUtil banji(StudentsClassVO studentsClassVO, Integer pageNum, Integer pageSize) {
        return ResultUtil.ok("查询成功", studentsInfoService.selectBJ(studentsClassVO, pageNum, pageSize));
    }


}
