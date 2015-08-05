package com.hunk.learn.web.contact.servlet;

import com.hunk.learn.web.contact.dao.ContactDao;
import com.hunk.learn.web.contact.dao.Impl.ContactDaoImpl;
import com.hunk.learn.web.contact.entity.Contact;
import com.hunk.learn.web.contact.service.ContactService;
import com.hunk.learn.web.contact.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 显示所有联系人的逻辑
 * Created by hunk on 2015/8/4.
 */
@WebServlet(name = "ListContactServlet", urlPatterns = "/ListContactServlet")
public class ListContactServlet extends HttpServlet {

    //    private ContactDao dao ;
    private ContactService contactService;
    @Override
    public void init() throws ServletException {
//        dao = new ContactDaoImpl();
        contactService = new ContactServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 1.从xml中读取联系人数据
//        List<Contact> list = dao.findAll();
        List<Contact> list = contactService.findAll();

        // 2.显示到浏览器
        // 把结果保存到域对象中
        request.setAttribute("contacts",list);

        // 3.跳转到jsp页面
        request.getRequestDispatcher("/jsp/listContact.jsp").forward(request, response);

        /*
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
        writer.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
        writer.println("<head>");
        writer.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
        writer.println("<title>查询所有联系人</title>");
        writer.println("<style type='text/css'>");
        writer.println("	table td{");
        writer.println("		*//*文字居中*//*");
        writer.println("		text-align:center;");
        writer.println("	}");
        writer.println("	");
        writer.println("	*//*合并表格的边框*//*");
        writer.println("	table{");
        writer.println("		border-collapse:collapse;");
        writer.println("	}");
        writer.println("</style>");
        writer.println( "</head>");
        writer.println("<body>");
        writer.println("<center>");
        writer.println("<h3>");
        writer.println("查询所有联系人");
        writer.println("</h3>");
        writer.println("</center>");
        writer.println("<table align='center' border='1' width='800px'>");
        writer.println("<tr>");
        writer.println("    <th>编号</th>");
        writer.println("    <th>姓名</th>");
        writer.println("    <th>性别</th>");
        writer.println("    <th>年龄</th>");
        writer.println("    <th>电话</th>");
        writer.println("    <th>邮箱</th>");
        writer.println("    <th>QQ</th>");
        writer.println("    <th>操作</th>");
        writer.println("</tr>");
        if (null!=list){
            for (Contact contact : list) {
                writer.println("<tr>");
                writer.println("    <td>"+ contact.getId() + "</td>");
                writer.println("    <td>"+ contact.getName() + "</td>");
                writer.println("    <td>"+ contact.getGender() + "</td>");
                writer.println("    <td>"+ contact.getAge() + "</td>");
                writer.println("    <td>"+ contact.getPhone() + "</td>");
                writer.println("    <td>"+ contact.getEmail() + "</td>");
                writer.println("    <td>"+ contact.getQq() + "</td>");
                writer.println("    <td>");
                writer.println("            <a href='"+request.getContextPath() + "/QueryContactServlet?id=" + contact.getId() +"'>");
                writer.println("修改");
                writer.println("            </a>");
                writer.println("&nbsp;");
                writer.println("            <a href='"+request.getContextPath() + "/DeleteContactServlet?id=" + contact.getId() +"'>");
                writer.println("删除");
                writer.println("            </a>");
                writer.println("    </td>");
                writer.println("</tr>");
            }
        }
        writer.println("<tr>");
        writer.println("    <td colspan='8' align='center'>");
        writer.println("            <a href='"+request.getContextPath() + "/jsp/addContact.jsp'>");
        writer.println("[添加联系人]");
        writer.println("        </a>");
        writer.println("    </td>");
        writer.println("</tr>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");

*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
