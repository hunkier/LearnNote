package cn.hunk.learn.web.page.servlet;

import cn.hunk.learn.web.page.entity.Employee;
import cn.hunk.learn.web.page.service.IEmployeeService;
import cn.hunk.learn.web.page.service.impl.EmployeeService;
import cn.hunk.learn.web.page.utils.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 4.控制层开发
 * Created by hunk on 2015/8/13.
 */
@WebServlet(name = "PageServlet", urlPatterns = "/page")
public class PageServlet extends HttpServlet {
    // 创建Service实例
    private IEmployeeService employeeService = new EmployeeService();
    // 跳转资源
    private String uri;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1.获取“当前页”参数： （第一次访问当前页为null）
            String currPage = request.getParameter("currentPage");
            // 判断
            if (null == currPage || "".equals(currPage)){
                currPage = "1";  // 第一次访问，设置当前页为1；
            }

            // 转换
            int currentPage = Integer.parseInt(currPage);

            // 2. 创建PageBean对象，设置当前页参数：传入service方法参数
            PageBean<Employee> pageBean = new PageBean<Employee>();
            pageBean.setCurrentPage(currentPage);

            // 3.调用service
            employeeService.getAll(pageBean);   // 【pagebean已经被dao填充了数据】


            // 4.保存pagebean对象，到request域中
            request.setAttribute("pageBean",pageBean);

            // 5.跳转
            uri = "/page/list.jsp";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // 出现错误，跳转到错误页面：给用户友好提示
            uri = "/error/error.jsp";
        }
        request.getRequestDispatcher(uri).forward(request,response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
