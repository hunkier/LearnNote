package com.hunk.learn.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 过滤器，测试
 * Created by hunk on 2015/8/13.
 */

/*
@WebFilter(filterName = "HelloFilter", urlPatterns="/page",initParams = {
        @WebInitParam(name = "encoding",value = "utf-8"),
        @WebInitParam(name = "key",value = "VALUE"),
        @WebInitParam(name = "name",value = "value"),
})
*/
public class HelloFilter implements Filter {
    /**
     * 创建实例
     */
    public HelloFilter() {
        System.out.println("1.创建了过滤器实例");
    }

    public void destroy() {
        System.out.println("6.销毁过滤器实例");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("3.执行过滤器业务处理方法");
        // 放行(去到servlet)
        // 如果有下一个过滤器，进入下一个过滤器，否则就执行访问servlet
        chain.doFilter(req, resp);
        System.out.println("5.servlet处理完毕，又回到过滤器");
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("2.执行过滤器初始化方法");
        // 获得过滤器在web.xml中配置的初始化参数
        String encoding = config.getInitParameter("encoding");
        System.out.println(encoding);

        // 获取过滤器在web.xml中配置的初始化参数的名称
        Enumeration<String> names = config.getInitParameterNames();
        while (names.hasMoreElements()){
            // 获取所有参数的名称
            String name = names.nextElement();
            // 获取名称对应值
            String value = config.getInitParameter(name);
            System.out.println(name + "\t" + value);
        }
    }

}
