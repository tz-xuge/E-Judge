package com.hebut.filter;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hebut.utils.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 获取请求头中的令牌
        String token = request.getHeader("Authorization");

        System.out.println(token);
        System.out.println("------------------JWT过滤器-------------");
        try {
            if(token==null){
                throw new Exception();
            }
            //验证令牌
            DecodedJWT verify = TokenUtil.verify(token);

            return true;
        } catch (SignatureVerificationException e){
            //e.printStackTrace();
            map.put("msg","无效签名");
        } catch (TokenExpiredException e){
            //e.printStackTrace();
            map.put("msg","token过期");
        } catch (AlgorithmMismatchException e){
            //e.printStackTrace();
            map.put("msg","token算法不一致");
        } catch (Exception e){
            //e.printStackTrace();
            map.put("msg","token无效");
        }
        map.put("state","flase");
        //将map转为json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
}
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
