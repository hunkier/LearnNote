package cn.hunk.learn.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/13.
 * urlPatterns = {"*.jsp"}
 */
// @WebFilter(filterName = "HelloFilter2",servletNames = "ListContactServlet", dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class HelloFilter2 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("第二个过滤器");
        chain.doFilter(req, resp);
        System.out.println("第二个过滤器执行结束");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
