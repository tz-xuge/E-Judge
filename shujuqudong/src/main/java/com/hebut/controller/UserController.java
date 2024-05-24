package com.hebut.controller;
import com.hebut.service.UserService;
import com.hebut.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * (User)表控制层
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    /**
     * 用户登录
     *
     * @param username,password
     * @return ResultVo
     */
    @PostMapping(value = "/login")
    public ResultUtil login(String username,String password) {
    	return ResultUtil.ok("登录成功", userService.queryByUsername(username, password));
    }


}
