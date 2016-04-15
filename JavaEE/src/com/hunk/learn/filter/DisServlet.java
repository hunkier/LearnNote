package com.hunk.learn.filter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/13.
 */
@WebServlet(name = "DisServlet", urlPatterns = "/DisServlet")
public class DisServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // 获取请求数据
        String content = request.getParameter("content");
        // 保存到request
        request.setAttribute("content",content);
        // 转发
        request.getRequestDispatcher("/filter/dis.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
