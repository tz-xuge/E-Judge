package com.hebut.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token 工具类
 */
public class TokenUtil {
	/**
	 * 过期时间--7天
	 */
	private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

	/**
	 * token私钥
	 */
	private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";

	/**
	 * 校验token是否正确
	 *
	 * @param token
	 * @return boolean
	 */
	public static DecodedJWT verify(String token) {

			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			
			return verifier.verify(token);
	}

	/**
	 * 校验token是否正确
	 *
	 * @param token
	 * @param userId
	 * @return boolean
	 */
	public static boolean verify(String token, String userId) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			if (userId.equals(jwt.getClaim("userId").asString())) {
				return true;
			} else {
				return false;
			}
		} catch (JWTDecodeException e) {
			return false;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得用户名
	 *
	 * @return token
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 获取用户ID
	 *
	 * @param token
	 * @return
	 */
	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userId").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 获取JSESSIONId
	 *
	 * @param token
	 * @return
	 */
	public static String getJSESSIONID(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("JSESSIONID").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名,7天后过期
	 *
	 * @param username   用户名
	 * @param userId     用户ID
	 * @param JSESSIONID 会话ID
	 * @return 加密的token
	 */
	public static String sign(String username, String userId, String JSESSIONID) throws UnsupportedEncodingException {
		// 过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		// 私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		// 设置头部信息
		Map<String, Object> header = new HashMap<>(2);
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		// 附带username，userId，JSESSIONID信息，生成签名
		return JWT.create().withHeader(header).withClaim("username", username).withClaim("userId", userId)
				.withClaim("JSESSIONID", JSESSIONID).withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 生成签名,7天后过期
	 *
	 * @param username 用户名
	 * @param userId   用户ID
	 * @return 加密的token
	 */
	public static String sign(String username, String userId) throws UnsupportedEncodingException {
		// 过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		// 私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		// 设置头部信息
		Map<String, Object> header = new HashMap<>(2);
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		// 附带username，userId信息，生成签名
		return JWT.create().withHeader(header).withClaim("username", username).withClaim("userId", userId)
				.withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 生成签名,7天后过期
	 *
	 * @param userId 用户ID
	 * @return 加密的token
	 */
	public static String sign(String userId) throws UnsupportedEncodingException {
		// 过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		// 私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		// 设置头部信息
		Map<String, Object> header = new HashMap<>(2);
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		// 附带userId信息，生成签名
		return JWT.create().withHeader(header).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
	}

}
