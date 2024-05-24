package com.hebut.service.impl;


import com.hebut.entity.User;
import com.hebut.mapper.UserMapper;
import com.hebut.service.UserService;
import com.hebut.utils.EncryptUtil;
import com.hebut.utils.ServiceException;
import com.hebut.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * (User)表服务实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final String  BASE_RESOURCE_URL  = "http://localhost:8888";

	@Resource
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 通过登录查询单条数据
	 *
	 * @param username
	 * @param password
	 * @return 实例对象
	 */
	@Override
	public User queryByUsername(String username, String password) {
		// 设置userVo
		User userVo = new User();

		if (username.length() > 32) { // 如果用token登录，token不刷新
			userVo.setToken(username);

		} else { // token刷新
			// 特殊登录条件
			userVo.setWxopenid(username);
			userVo.setQqopenid(username);

			// 普通登录条件
			userVo.setEmail(username);
			userVo.setName(username);
			userVo.setPassword(password);
			userVo.setPassword(EncryptUtil.getMD5String(userVo.getPassword()));
		}

		// 获取用户信息
		User user = this.userMapper.selectByUsername(userVo);

		if (user != null) {
			try {
				if (username.length() > 32) {
					// 返回token
					response.setHeader("Authorization", user.getToken());
				} else {
					String token = TokenUtil.sign(user.getUid().toString());
					// 写入token
					user.setToken(token);
					userMapper.updateByPrimaryKeySelective(user);

					// 返回token
					response.setHeader("Authorization", token);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String uid = String.valueOf(user.getUid());
			redisTemplate.opsForValue().set("uid", uid);

		} else {
			throw new ServiceException(-1, "用户名或者密码错误");
		}

		// 隐藏密码
		user.setPassword(null);
//		user.setHeadimg(SourceUtil.getPictureUrl()+user.getHeadimg());
		return user;
	}



}
