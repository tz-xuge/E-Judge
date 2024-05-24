package com.hebut.filter;

import com.hebut.utils.ResponseWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName = "JsonFilter",urlPatterns="/*")
public class JsonFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		ResponseWrapper wrapper = new ResponseWrapper(res);
		chain.doFilter(request, wrapper);


		System.out.println("------------------JsonFilter过滤器-------------");
		// 获取response返回的内容并重新写入response
		String result = wrapper.getResponseData(response.getCharacterEncoding());
		response.getOutputStream().write(result.getBytes());
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
