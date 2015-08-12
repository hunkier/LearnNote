package com.hunk.learn.web.page.dao;

import com.hunk.learn.web.page.entity.Employee;
import com.hunk.learn.web.page.utils.PageBean;

/**
 * 2.数据访问层，接口设计
 * Created by hunkier on 15/8/12.
 */
public interface IEmployeeDao {
    /**
     * 分页查询数据
     * @param pb
     */
    public void getAll(PageBean<Employee> pb);

    /**
     * 查询总记录数
     * @return
     */
    public int getTotalCount();
}
