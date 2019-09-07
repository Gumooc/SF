package com.run.filter;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class sffilter implements Filter {

	@Override
	public void destroy() {
		//System.out.println("destroy");

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials","true");
		
		boolean flag = false;
		/*
		Cookie[] cookies = request.getCookies();
		//System.out.println("Start_Cookie");
		if (cookies!=null) {
			for (Cookie cookie:cookies) {
				System.out.println(cookie.getName()+":"+cookie.getValue());
			}
		}
		//System.out.println("End_Cookie");
		//HttpSession session = request.getSession();
		*/
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

		//System.out.println("init");
		
	}

}
