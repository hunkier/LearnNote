package com.hunk.learn.web.emp.servlet;

import com.hunk.learn.web.emp.service.IEmployeeService;
import com.hunk.learn.web.emp.service.impl.EmployeeService;
import com.hunk.learn.web.emp.entity.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by hunk on 2015/8/13.
 */
@WebServlet(name = "Index2Servlet", urlPatterns = "/emp/index")
public class IndexServlet extends HttpServlet {
    // Service实例
    private IEmployeeService employeeService = new EmployeeService();
    // 跳转资源
    private String uri;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 调用service查询所有
            List<Employee> list = employeeService.getAll();
            request.setAttribute("listEmp",list);
            // 进入首页jsp
            uri = "/emp/list.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher(uri).forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
