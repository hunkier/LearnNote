package com.hunk.learn.web.contact.servlet;

import com.hunk.learn.web.contact.dao.ContactDao;
import com.hunk.learn.web.contact.dao.Impl.ContactDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/4.
 */
@WebServlet(name = "DeleteContactServlet", urlPatterns = "/DeleteContactServlet")
public class DeleteContactServlet extends HttpServlet {
    private ContactDao dao ;
    @Override
    public void init() throws ServletException {
        dao = new ContactDaoImpl();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        dao.deleteContact(request.getParameter("id"));
        response.sendRedirect(request.getContextPath() + "/ListContactServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
