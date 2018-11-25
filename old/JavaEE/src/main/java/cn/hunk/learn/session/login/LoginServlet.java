package cn.hunk.learn.session.login;

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
@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接受参数
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

        // 2.判断逻辑
        if("hunk".equals(userName) && "123456".equals(userPwd)){
            // 登录成功
            /**
             * 分析：
             * 	  context域对象：不合适，可能会覆盖数据。
             *    request域对象： 不合适，整个网站必须得使用转发技术来跳转页面
             *    session域对象：合适。
             */
			/*
			request.setAttribute("loginName", userName);
			//request.getRequestDispatcher("/IndexServlet").forward(request, response);
			response.sendRedirect(request.getContextPath()+"/IndexServlet");
			*/

            /**
             * 一、登录成功后，把用户数据保存在session对象中
             */
        // 1.创建session对象
            HttpSession session = request.getSession();
            // 2.把数据保存到session域中
            session.setAttribute("loginName", userName);
            // 3.跳转到用户主页
            response.sendRedirect(request.getContextPath() + "/IndexServlet");
        }else{
            // 登录失败
            // 请求重定向
            response.sendRedirect(request.getContextPath() +"/session/fail.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
