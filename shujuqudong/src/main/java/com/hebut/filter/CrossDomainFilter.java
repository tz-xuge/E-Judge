package com.hebut.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局跨域处理 springboot需要加@Component
 */
@Component
@WebFilter(filterName = "CrossDomainFilter",urlPatterns="/*")
public class CrossDomainFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;


		System.out.println("------------------CrossFilter过滤器-------------");
		/*
		 * Access-Control-Allow-Origin 响应头指定了该响应的资源是否被允许与给定的origin共享。
		 * 对于不需具备凭证（credentials）的请求，服务器会以“*”作为通配符，从而允许所有域都具有访问资源的权限。
		 * 可以指定一个可以访问资源的URI。
		 *
		 * CORS和缓存 如果服务器未使用“*”，而是指定了一个域，那么为了向客户端表明服务器的返回会根据Origin请求头而有所不同，
		 * 必须在Vary响应头中包含Origin。 例如： Access-Control-Allow-Origin:
		 * https://developer.mozilla.org Vary: Origin
		 */
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

		/*
		 * 请求首部 Access-Control-Request-Method 出现于 preflight request （预检请求）中，
		 * 用于通知服务器在真正的请求中会采用哪种 HTTP 方法。 因为预检请求所使用的方法总是 OPTIONS
		 * ，与实际请求所使用的方法不一样，所以这个首部是必要的。
		 */
		response.setHeader("Access-Control-Allow-Method", "*");

		/*
		 * The Access-Control-Max-Age 这个响应首部表示 preflight request（预检请求）的返回结果 （即
		 * Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息）
		 * 可以被缓存多久
		 */
		response.setHeader("Access-Control-Max-Age", "3600");

		/*
		 * 响应首部 Access-Control-Allow-Headers 用于 preflight request （预检请求）中
		 * 列出了将会在正式请求的 Access-Control-Request-Headers 字段中出现的首部信息
		 *
		 * 注意以下这些特定的首部是一直允许的： Accept, Accept-Language,
		 * Content-Language,Content-Type 其中 Content-Type （只在其值属于 MIME 类型
		 * application/x-www-form-urlencoded, multipart/form-data 或
		 * text/plain中的一种）。 这些被称作simple headers，你无需特意声明它们。
		 */
		// response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept, Access-Token,XXX");
		// Origin, X-Requested-With, Content-Type, Accept,Access-Token

		/*
		 * 响应首部 Access-Control-Expose-Headers 列出了哪些首部可以作为响应的一部分暴露给外部
		 *
		 * 默认情况下，只有六种 simple response headers （简单响应首部）可以暴露给外部： Cache-Control
		 * Content-Language Content-Type Expires Last-Modified Pragma
		 * 如果想要让客户端可以访问到其他的首部信息，可以将它们在 Access-Control-Expose-Headers 里面列出来
		 */
		response.setHeader("Access-Control-Expose-Headers", "checkTokenResult, message, Authorization");

		/*
		 * 允许cookie跨域
		 */
		// 指示的请求的响应是否可以暴露于该页面。当返回true时他可以被暴露Credentials
		response.setHeader("Access-Control-Allow-Credentials", "true");

		/*
		 * Credentials no-cache： 在发布缓存副本之前，强制要求缓存把请求提交给原始服务器进行验证。
		 */
		response.setHeader("Cache-Control", "no-cache");

		// response.setHeader("content-type","application:json;charset=utf8");

		// 把请求传回过滤链

		chain.doFilter(request, response);
		return;

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
