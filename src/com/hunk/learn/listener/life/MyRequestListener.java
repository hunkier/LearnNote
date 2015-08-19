package com.hunk.learn.listener.life;
/**
 * 监听request对象的创建和销毁
 * Created by hunk on 2015/8/14.
 */

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

// @WebListener()
public class MyRequestListener implements ServletRequestListener {

    // Public constructor is required by servlet spec
    public MyRequestListener() {
        System.out.println("创建  MyRequestListener");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("销毁  MyServletRequestListener.requestDestroyed(ServletRequestEvent servletRequestEvent)");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("初始化  MyRequestListener.requestInitalized(ServletRequestEvent servletRequestEvent)   " + ((HttpServletRequest) servletRequestEvent.getServletRequest()).getRequestURI());
    }
}
