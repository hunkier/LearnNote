package com.hunk.learn.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * 请求数据的获取
 * Created by dell on 2015/7/30.
 */
@WebServlet("/request1" )
public class RequestDemo1  extends HttpServlet{
	/**
	 * 1)tomcat服务器接收到浏览器发送的请求数据，然后封装到HttpServetRequest对象
	 * 2）tomcat服务器调用doGet方法，然后把request对象传入到servlet中。
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	/**
		 * 3)从request对象取出请求数据。
		 */
        t1(req);
        t2(req);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	/**
		 * 3.3 请求的实体内容
		 */
    	InputStream in = req.getInputStream();
    	byte[] buf = new byte[1024];
    	int len = 0 ;
    	while( (len=in.read(buf))!=-1){
    		String str = new String(buf,0,len);
    		System.out.println(str);
    	}
    }

    private void t1(HttpServletRequest request){
        System.out.println("请求方式：" + request.getMethod());
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("URL: " + request.getRequestURL());
        System.out.println("Http协议版本： " + request.getProtocol());
    }

    private void t2(HttpServletRequest request){
        System.out.println(request.getHeader("Host"));
        Enumeration<String> enums = request.getHeaderNames();
        while (enums.hasMoreElements()){
            String headerName = enums.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + " : " + headerValue);
        }
    }
}
