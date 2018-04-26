package com.hunk.learn.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 案例-用户上次访问时间
 * Created by dell on 2015/7/31.
 */
@WebServlet(name = "HistServlet",urlPatterns = "/HistServlet")
public class HistServlet extends javax.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String cruTime = format.format(new Date());

        //取得cookies
        Cookie[] cookies = request.getCookies();
        String lastTime = null ;
        PrintWriter writer = response.getWriter();
        if(cookies!=null){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("lastTime")){
                // 有lastTime的cookies的，已经是第n次访问
                lastTime = cookie.getValue();//上次访问的时间
                    // 第n次访问
                    //1.把上次显示的时间显示到浏览器
                    writer.println("欢迎回来，你上次访问的时间为: " + lastTime);
                    //2.更新cookies
                    cookie.setValue(cruTime);
                    cookie.setMaxAge(60*60);
                    //3.把更新后的cookie发送到浏览器
                    response.addCookie(cookie);
                    break;
                }
            }

        }
        /**
         * 第一次访问（没有cookie 或 有cookie，但没有名为lastTime的cookie）
         */
        if(null==cookies || lastTime==null){
            //1.显示当前时间到浏览器
            writer.println("你是首次访问本网站，当前时间为：" + cruTime);
            //2.创建cookie对象
            Cookie cookie = new Cookie("lastTime",cruTime);
            cookie.setMaxAge(60*60);// 保存一个小时
            //3.把cookie发送到浏览器保存
            response.addCookie(cookie);

        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
