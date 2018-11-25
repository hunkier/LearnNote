package cn.hunk.learn.web.emp.dao;

import cn.hunk.learn.web.emp.entity.Employee;
import cn.hunk.learn.web.emp.entity.Employee;

import java.util.List;

/**
 * 2.员工数据访问层接口设计
 * Created by hunk on 2015/8/13.
 */
public interface IEmployeeDao {
    /**
     * 查询所有的员工
     * @return
     */
    public List<Employee> getAll();
}
