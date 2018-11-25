package cn.hunk.learn.listener.attr;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 监听sessoin对象属性的变化
 * Created by hunk on 2015/8/14.
 */
// @WebListener()
public class MySessionAttrListener implements  HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public MySessionAttrListener() {
    }




    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    /**
     * 属性添加
     * @param sbe
     */
    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
        // 先获取session对象
        HttpSession session = sbe.getSession();
        // 获取添加的属性
        Object obj = session.getAttribute("userName");
        // 测试
        System.out.println("添加的属性" + obj);
    }

    /**
     * 属性移除
     * @param sbe
     */
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        System.out.println("属性移除");
    }

    /**
     * 属性替换
     * @param sbe
     */
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
        // 先获取session对象
        HttpSession session = sbe.getSession();

        //获取替换前的值
        Object old = sbe.getValue();
        System.out.println("原来的值： " + old);

        // 获取新的值
        Object obj_new = session.getAttribute("userName");
        System.out.println("新值： " + obj_new);
    }
}
