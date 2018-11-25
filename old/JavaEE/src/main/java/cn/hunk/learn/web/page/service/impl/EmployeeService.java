package cn.hunk.learn.web.page.service.impl;

import cn.hunk.learn.web.page.dao.IEmployeeDao;
import cn.hunk.learn.web.page.dao.impl.EmployeeDao;
import cn.hunk.learn.web.page.entity.Employee;
import cn.hunk.learn.web.page.service.IEmployeeService;
import cn.hunk.learn.web.page.utils.PageBean;

/**
 * 3.业务逻辑层，实现
 * Created by hunk on 2015/8/13.
 */
public class EmployeeService implements IEmployeeService{
        // 创建Dao实例
        private IEmployeeDao employeeDao = new EmployeeDao();
    /**
     * 分页数据查询
     *
     * @param pb
     */
    @Override
    public void getAll(PageBean<Employee> pb) {
        try {
            employeeDao.getAll(pb);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
