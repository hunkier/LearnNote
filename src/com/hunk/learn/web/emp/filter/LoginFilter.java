package com.hunk.learn.web.emp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录验证过滤器
 * http://localhost:8080/JavaEE/emp/login.jsp       可以直接访问
 * http://localhost:8080/JavaEE/emp/login.jsp       可以直接访问
 * http://localhost:8080/JavaEE/emp/index           不可以直接访问
 * http://localhost:8080/JavaEE/emp/list.jsp        不可以直接访问
 * Created by hunk on 2015/8/13.
 */
@WebFilter(filterName = "LoginFilterT3s", urlPatterns = {"/emp/*","/emp/list.jsp"})
public class LoginFilter implements Filter {
    private String uri;
    public void destroy() {
    }

    /**
     * 分析：
     *      1.先指定放行的资源，哪些资源不需要拦截：
     *          login.jsp   +   /long   (request对象可以获取）
     *      2.获取session，从session中获取登录用户
     *      3.判断是否为空：
     *            为空，说明没有登录，跳到登录
     *            不为空，已经登录，放行
     */

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 0.转换
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 1.获取请求资源，截取
        String uri = request.getRequestURI();
        // System.out.println(uri);
        // 截取 【login.jsp或login】
        String requestPath = uri.substring(uri.lastIndexOf("/")+1,uri.length());

        // 2.判断：先放行一些资源：/login.jsp、/login
        if ("login".equals(requestPath) || "login.jsp".equals(requestPath)){
            chain.doFilter(request,response);
            return;
        }else{
            // 3.  对其他资源进行拦截
            // 3.1 先去Session，获取session中登录用户（loginInfo)
            HttpSession session = request.getSession(false);
            // 判断
            if (null != session){
                Object obj = session.getAttribute("loginInfo");
                // 3.2 如果获取的内容不为空，说明已经登录，放行
                if (null != obj){
                    // 放行
                    chain.doFilter(request,response);
                    return;
                }else {
                    // 3.3 如果获取内容为空，说明没有登录；跳转到登录
                    uri = "/emp/login.jsp";
                }
            }else {
                uri = "/emp/login.jsp";
            }

            request.getRequestDispatcher(uri).forward(request,response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
