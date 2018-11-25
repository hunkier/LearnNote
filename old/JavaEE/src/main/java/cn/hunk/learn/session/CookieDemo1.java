package cn.hunk.learn.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 第一个cookie的程序
 * Created by dell on 2015/7/31.
 */
@WebServlet(name = "CookieDemo1", urlPatterns="/CookieDemo1")
public class CookieDemo1 extends javax.servlet.http.HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.创建Cookie对象
        Cookie cookie1 = new Cookie("name","eric");
        /**
         * 1）设置cookie的有效路径。默认情况：有效路径在当前web应用下。
         */
        cookie1.setPath("/JavaEE/cookie1");
		/**
		 * 2)设置cookie的有效时间 正整数：表示cookie数据保存浏览器的缓存目录（硬盘中），数值表示保存的时间。
		 * 负整数：表示cookie数据保存浏览器的内存中。浏览器关闭cookie就丢失了！！ 零：表示删除同名的cookie数据
		 */
        cookie1.setMaxAge(10);//10秒，从最后不调用cookie开始计算
        //2.把cookie数据发送到浏览器（通过响应头发送： set-cookie名称）
        //response.setHeader("set-cookie", cookie.getName()+"="+cookie.getValue()+",email=eric@qq.com");
        //推荐使用这种方法，避免手动发送cookie信息
        response.addCookie(cookie1);
        //response.addCookie(cookie2);
        //response.addCookie(cookie1);

        //3.接收浏览器发送的cookie信息
		/*String name = request.getHeader("cookie");
		System.out.println(name);*/
        Cookie[] cookies = request.getCookies();
        PrintWriter writer = response.getWriter();
                //注意：判断null,否则空指针
        if(cookies!=null){
            //遍历
            for(Cookie c:cookies){
                String name = c.getName();
                String value = c.getValue();
                writer.println(name+"="+value);
            }
        }else{
            writer.println("没有接收cookie数据");
        }

        writer.println(this.getClass().getName());

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }
}
