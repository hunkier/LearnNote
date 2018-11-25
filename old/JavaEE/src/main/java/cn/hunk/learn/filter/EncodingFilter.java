package cn.hunk.learn.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 编码处理统一写到这里(servlet中不需要再处理编码)
 * Created by hunk on 2015/8/13.
 */
  @WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
    // 过滤器业务处理方法：处理的公用的业务逻辑操作
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 转型
        final HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)resp;

        // 一、处理公用业务
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpServletRequest proxy = (HttpServletRequest) Proxy.newProxyInstance(
                request.getClass().getClassLoader(),        // 指定当前使用的类加载器
                new Class[]{HttpServletRequest.class},      // 对目标对象实现的接口类型
                new InvocationHandler() {                   // 事件处理
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 定义返回值
                        Object returnValue = null;
                        // 获取方法名
                        String methodName = method.getName();
                        // 判断：对getParameter方法进行GET提交中文处理
                        if ("getParameter".equals(methodName)){
                            // 获取请求数据值      【<input type="text" name="userName">】
                            String value = request.getParameter(args[0].toString());    // 调用目标对象的方法

                            // 获取提交方式
                            String methodSubit = request.getMethod();   //直接调用目标对象的方法

                            // 判断如果是GET提交，需要对数据进行处理(POST提交已经处理过了)
                            if ("GET".equals(methodSubit)){
                                if (null!=value && !"".equals(value.trim())){
                                    // 处理GET中文
//                                    value = new String(value.getBytes("ISO8859-1"),"UTF-8");
                                }
                            }
                            return  value;
                        }else {
                            // 执行request对象的其他方法
                            returnValue = method.invoke(request,args);
                        }
                        return returnValue;
                    }
                }
        );

        // 二、放行 (执行下一个过滤器或者servlet)
        chain.doFilter(proxy, resp);      //  传入代理对象
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }


}
