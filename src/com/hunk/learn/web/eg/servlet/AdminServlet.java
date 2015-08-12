package com.hunk.learn.web.eg.servlet;

import com.hunk.learn.web.eg.entity.Admin;
import com.hunk.learn.web.eg.exception.UserExistException;
import com.hunk.learn.web.eg.service.IAdminService;
import com.hunk.learn.web.eg.service.impl.AdminService;
import com.hunk.learn.web.eg.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 4.控制层开发
 * Created by hunk on 2015/8/11.
 */
@WebServlet(name = "AdminServlet", urlPatterns = "/AdminServlet")
public class AdminServlet extends HttpServlet {
    // 调用的service
    private IAdminService adminService = new AdminService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取操作类型
        String method = request.getParameter("method");
        if ("regiester".equals(method)){
                register(request,response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * 注册方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /*
        // 1.获取请求参数
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        // 封装
        Admin admin = new Admin();
        admin.setUserName(userName);
        admin.setPwd(pwd);
        */

        // 使用BeanUtils组件处理请求数据的封装
        Admin admin = WebUtils.copyToBean(request,Admin.class);

        // 调用Service处理注册的业务逻辑
        try {
            adminService.register(admin);
            request.setAttribute("userName", admin.getUserName());
            request.getRequestDispatcher("/jdbc/success.jsp").forward(request,response);
        } catch (UserExistException e) {
            // 用户名存在，跳转到首页
            e.printStackTrace();
            request.setAttribute("message",e.getMessage());
            request.getRequestDispatcher("/jdbc/regiester.jsp").forward(request,response);
        }catch (Exception e){
            response.sendRedirect(request.getContextPath() + "/jdbc/error/error.jsp");
        }
    }
}
