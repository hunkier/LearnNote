package cn.hunk.learn.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 保存会话数据到session域对象
 * Created by hunk on 2015/7/31.
 */
@WebServlet(name = "SessionDemo1", urlPatterns = "/SessionDemo1")
public class SessionDemo1 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        // 1.创建session对象
        HttpSession session = request.getSession();
        //得到session编号
        writer.println("id = "+session.getId());

        /**
         * 修改session的有效时间
         */
        session.setMaxInactiveInterval(20);

        /**
         * 手动发送一个硬盘保存的cookie给浏览器
         */
        Cookie c = new Cookie("JSESSIONID", session.getId());
        c.setMaxAge(60*60);
        response.addCookie(c);

        // 2.保存回话数据
        session.setAttribute("name","rose");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
