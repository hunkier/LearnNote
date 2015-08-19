package com.hunk.learn.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 从session域对象中取出会话数据
 * Created by hunk on 2015/7/31.
 */
@WebServlet(name = "SessionDemo2", urlPatterns = "/SessionDemo2")
public class SessionDemo2 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        // 1.得到session对象
        HttpSession session = request.getSession(false);
        if (null == session){
            writer.println("没有找到对应的session对象");
            return;
        }

        /**
         * 得到session编号
         */
        writer.println("id = " + session.getId());

        // 2.取出数据
        String name = (String) session.getAttribute("name");
        writer.println("name = " + name);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
