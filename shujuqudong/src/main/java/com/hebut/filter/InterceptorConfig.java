package com.hebut.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new JWTInterceptor())
                //添加拦截的路径
                .addPathPatterns("/**")
                //排除不拦截
                .excludePathPatterns(
                        "/static/images/*/**","/user/login","/user/find-pwd",
                        "/user/register","/user/send-code","/user/get-profile","/user/put-file");

    }

    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/static/*").addResourceLocations("classpath:/static/");

    }
}
