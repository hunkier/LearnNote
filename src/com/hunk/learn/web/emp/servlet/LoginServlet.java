package com.hunk.learn.web.emp.servlet;

import com.hunk.learn.web.emp.entity.Admin;
import com.hunk.learn.web.emp.service.IAdminService;
import com.hunk.learn.web.emp.service.impl.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理登录请求
 * Created by hunk on 2015/8/13.
 */
@WebServlet(name = "Login23Servlet", urlPatterns = "/emp/login")
public class LoginServlet extends HttpServlet {
    // Service实例
    private IAdminService service = new AdminService();
    // 跳转资源
    private  String uri;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取参数
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        // 封装
        Admin admin = new Admin();
        admin.setUserName(userName);
        admin.setPwd(pwd);

        try {
            // 2.调用service处理业务
            Admin loginInfo = service.findByNameAndPwd(admin);

            // 判断：
            if (null == loginInfo){
                // 登录失败
                uri = "/emp/login.jsp";
            }else{
                // 登录成功
                // 先，保存数据到session
                request.getSession().setAttribute("loginInfo",loginInfo);
                // 再，跳转到首页显示servlet(/index)
                uri = "/emp/index";
            }
        } catch (Exception e) {
            // 测试
            e.printStackTrace();
            uri = "/error/error.jsp";
        }
        // 3.跳转
        request.getRequestDispatcher(uri).forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}