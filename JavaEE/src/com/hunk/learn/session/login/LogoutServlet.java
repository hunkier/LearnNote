package com.hunk.learn.session.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/3.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 三、安全退出
         * 删除session对象中指定的loginName属性即可
         */
        // 1.得到session对象
        HttpSession session = request.getSession(false);
        if (null!=session){
            // 2.删除session
            session.removeAttribute("loginName");
        }

        // 2.回来登录页面
        response.sendRedirect(request.getContextPath()+"/session/login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
