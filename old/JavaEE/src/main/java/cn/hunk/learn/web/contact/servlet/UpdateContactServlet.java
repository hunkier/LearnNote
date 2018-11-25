package cn.hunk.learn.web.contact.servlet;

import cn.hunk.learn.web.contact.dao.ContactDao;
import cn.hunk.learn.web.contact.dao.Impl.ContactDaoImpl;
import cn.hunk.learn.web.contact.entity.Contact;
import cn.hunk.learn.web.contact.service.ContactService;
import cn.hunk.learn.web.contact.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/4.
 */
@WebServlet(name = "UpdateContactServlet", urlPatterns = "/UpdateContactServlet")
public class UpdateContactServlet extends HttpServlet {
    //    private ContactDao entity ;
    private ContactService contactService;
    @Override
    public void init() throws ServletException {
//        entity = new ContactDaoImpl();
        contactService = new ContactServiceImpl();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Contact contact = new Contact();
        contact.setId(request.getParameter("id"));
        contact.setEmail(request.getParameter("email"));
        contact.setGender(request.getParameter("gender"));
        contact.setPhone(request.getParameter("phone"));
        contact.setQq(request.getParameter("qq"));
        contact.setName(request.getParameter("name"));
        contact.setAge(Integer.parseInt(request.getParameter("age")));
//        entity.updateContact(contact);
        contactService.updateContact(contact);
        response.sendRedirect(request.getContextPath() + "/ListContactServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
