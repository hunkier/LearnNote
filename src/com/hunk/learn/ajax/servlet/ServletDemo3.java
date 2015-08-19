package com.hunk.learn.ajax.servlet;


import com.hunk.learn.ajax.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunk on 2015/8/17.
 */
@WebServlet(name = "ServletDemo3", urlPatterns = "/ajax/demo3")
public class ServletDemo3 extends HttpServlet{
    private List<Product> products = new ArrayList<Product>();
    public void init()throws ServletException{
        products.add(new Product(1,"充气筒",20));
        products.add(new Product(2,"金瓶梅",10));
        products.add(new Product(3,"袜子",10));
        products.add(new Product(4,"洗衣粉",10));
        products.add(new Product(5,"肥皂",7));
    }
    private String usernames[] = {"admin","hunk"};
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        System.out.println(this.getClass().getName() +" 执行了！");
       req.setAttribute("products",products);
        req.getRequestDispatcher("/ajax/listProducts.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
