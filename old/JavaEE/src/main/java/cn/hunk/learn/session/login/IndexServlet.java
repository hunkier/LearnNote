package cn.hunk.learn.session.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hunk on 2015/8/3.
 */
@WebServlet(name = "IndexServlet", urlPatterns = "/IndexServlet")
public class IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");


        /**
         * 接收request域对象的数据
         */
        /**
         * 二、在用户主页，判断session不为空且存在指定的属性才视为登录陈功！才能访问资源
         * 从session域中获取会话数据
         */
        // 1.得到session对象
        HttpSession session = request.getSession(false);
        if(null == session){
            // 没有登录成功，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/session/login.jsp");
            return;
        }
        // 2.取出会话数据
        String loginName = (String) session.getAttribute("loginName");
        if (null==loginName || "".equals(loginName)){
            // 没有登录成功，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/session/login.jsp");
            return;
        }


        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("欢迎回来，");
        writer.println(loginName);
        writer.println(",");
        writer.println("<a href='"+ request.getContextPath() + "/LogoutServlet"+"' >");
        writer.println("安全退出");
        writer.println("</a>");
        writer.println("</body>");
        writer.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
