package com.hunk.learn.listener.session;
/**
 * 监听此对象绑定到session上的过程，需要实现session特定接口
 * Created by hunk on 2015/8/14.
 */

import javax.servlet.http.*;

// @WebListener() 不需要注册
public class Admin implements HttpSessionBindingListener {

    private  int id;
    private String name;

    public Admin() {
    }

    public Admin(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("Admin对象已经放入session");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("Admin对象从session中移除");
    }
}
