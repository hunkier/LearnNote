package com.hunk.learn.jdbc.batch;

/**
 * 实体类封装数据
 * Created by hunk on 2015/8/11.
 */
public class Admin {
    private String userName;
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
