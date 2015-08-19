package com.hunk.learn.session.history.servlet;

import com.hunk.learn.session.history.Dao.ProductDao;
import com.hunk.learn.session.history.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by hunk on 2015/7/31.
 */
@WebServlet(name = "ListServlet", urlPatterns = "/ListServlet")
public class ListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //1.读取数据
        ProductDao dao = new ProductDao();
        List<Product> list = dao.findAll();

        //2.把商品显示到浏览器
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>");
        writer.println("显示商品列表");
        writer.println("</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<table border='1' align='center' width='600px'>");
        writer.println("<tr>");
        writer.println("<th>编号</th><th>商品名称</th><th>商品型号</th><th>商品价格</th>");
        writer.println("</tr>");
        // 遍历商品
        for (Product p : list){
            writer.println("<tr>");
            writer.println("<th>");
            writer.println(""+p.getId());
            writer.println("</th>");
            writer.println("<th>");
            writer.println("<a href='"+request.getContextPath()+"/DetailServlet?id="+p.getId()+"'>");
            writer.println(p.getProName());
            writer.println("</a>");
            writer.println("</th>");
            writer.println("<th>");
            writer.println(p.getProType());
            writer.println("</th>");
            writer.println("<th>");
            writer.println(""+p.getPrice());
            writer.println("</th>");
            writer.println("</tr>");
        }
        writer.println("");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
