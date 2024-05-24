package com.hebut.service;


import com.hebut.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * (User)表服务接口
 */
public interface UserService {

	/**
	 * 通过登录查询单条数据
	 *
	 * @param username
	 * @param password
	 * @return 实例对象
	 */
	User queryByUsername(String username, String password);
}
