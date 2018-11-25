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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 修改前查询联系人的逻辑
 * Created by hunk on 2015/8/4.
 */
@WebServlet(name = "QueryContactServlet", urlPatterns = "/QueryContactServlet")
public class QueryContactServlet extends HttpServlet {
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

        // 1.接收id
        String id = request.getParameter("id");

        // 2.调用dao根据id查询联系人方法
//        Contact contact = entity.findById(id);
        Contact contact = contactService.findById(id);
        HttpSession session = request.getSession(true);
        session.setAttribute("contact",contact);
        response.sendRedirect(request.getContextPath()+"/jsp/modifContact.jsp");

        //
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
