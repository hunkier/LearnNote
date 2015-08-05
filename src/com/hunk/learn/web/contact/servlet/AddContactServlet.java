package com.hunk.learn.web.contact.servlet;


import com.hunk.learn.web.contact.dao.ContactDao;
import com.hunk.learn.web.contact.dao.Impl.ContactDaoImpl;
import com.hunk.learn.web.contact.entity.Contact;
import com.hunk.learn.web.contact.exception.NameRepeatException;
import com.hunk.learn.web.contact.service.ContactService;
import com.hunk.learn.web.contact.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 添加联系人的逻辑
 * Created by hunk on 2015/8/4.
 */
@WebServlet(name = "AddContactServlet", urlPatterns = "/AddContactServlet")
public class AddContactServlet extends HttpServlet {
//    private ContactDao dao ;
    private ContactService contactService;
    @Override
    public void init() throws ServletException {
//        dao = new ContactDaoImpl();
        contactService = new ContactServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 1.接受参数
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String qq = request.getParameter("qq");

        // 封装成对象
        Contact contact = new Contact();
        contact.setName(name);
        contact.setGender(gender);
        contact.setPhone(phone);
        contact.setAge(Integer.parseInt(age));
        contact.setEmail(email);
        contact.setQq(qq);

        // 2.调用dao类的添加联系人方法
//        dao.addContact(contact);
        try {
            contactService.addContact(contact);
        }catch (NameRepeatException e){
            // 处理自定义业务异常
            request.setAttribute("msg",e.getMessage());
            request.setAttribute("contact", contact);
            request.getRequestDispatcher("/jsp/addContact.jsp").forward(request, response);
            return;
        }

        // 3.跳转到查询联系人的页面
        response.sendRedirect(request.getContextPath() + "/ListContactServlet");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
