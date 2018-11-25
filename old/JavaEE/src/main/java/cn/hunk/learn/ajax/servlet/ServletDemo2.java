package cn.hunk.learn.ajax.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/17.
 */
@WebServlet(name = "ServletDemo2", urlPatterns = "/ajax/demo2")
public class ServletDemo2 extends HttpServlet{
    private String usernames[] = {"admin","hunk"};
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        System.out.println(this.getClass().getName() +" 执行了！");
        String username = req.getParameter("username");
        boolean b = false;
        for (String s : usernames){
            if (s.equals(username)){
                b = true;
                break;
            }
        }
        String msg = null;
        if (b){
            msg = "<font color='red'>用户名不可用</font>";
        }else{
            msg = "<font color='green'>用户可用</font>";
        }
        resp.getWriter().write(msg);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
