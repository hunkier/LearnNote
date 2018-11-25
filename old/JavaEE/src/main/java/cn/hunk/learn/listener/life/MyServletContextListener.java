package cn.hunk.learn.listener.life;
/**
 * Created by hunk on 2015/8/14.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听ServletContextListener对象创建和销毁
 */
// @WebListener()
public class MyServletContextListener implements ServletContextListener{

    // Public constructor is required by servlet spec
    public MyServletContextListener() {
        System.out.println("创建ServletContextListener");
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        System.out.println("初始化 ServletContextListener.contextInitialized(ServletContextEvent sce)");
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        System.out.println("销毁  ServletContextListenter.contextDestroyed(ServletContextEvent sce)");
    }


}
