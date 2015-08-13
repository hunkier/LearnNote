package com.hunk.learn.web.page.service;

import com.hunk.learn.web.page.entity.Employee;
import com.hunk.learn.web.page.utils.PageBean;

/**
 * 3.逻辑业务接口设计
 * Created by hunk on 2015/8/13.
 */
public interface IEmployeeService {
    /**
     * 分页数据查询
     * @param pb
     */
    public void getAll(PageBean<Employee> pb);
}
