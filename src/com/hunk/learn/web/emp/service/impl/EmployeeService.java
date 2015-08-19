package com.hunk.learn.web.emp.service.impl;

import com.hunk.learn.web.emp.dao.IEmployeeDao;
import com.hunk.learn.web.emp.dao.impl.EmployeeDao;
import com.hunk.learn.web.emp.entity.Employee;
import com.hunk.learn.web.emp.service.IEmployeeService;

import java.util.List;

/**
 * Created by hunkier on 15/8/13.
 */
public class EmployeeService implements IEmployeeService{
    IEmployeeDao employeeDao = new EmployeeDao();
    /**
     * 查询所有的员工
     *
     * @return
     */
    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }
}
