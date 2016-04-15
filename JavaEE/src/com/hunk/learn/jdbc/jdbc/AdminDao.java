package com.hunk.learn.jdbc.jdbc;

import java.util.List;

/**
 * Created by hunk on 2015/8/12.
 */
public class AdminDao extends  BaseDao {
    /**
     * 删除
     * @param id
     */
    public void delete(int id){
        String sql = "delete from admin where id=?";
        Object[] paramsValue = {id};
        super.update(sql,paramsValue);
    }

    /**
     * 插入
     * @param admin
     */
    public void save(Admin admin){
        String sql = "insert into admin(userName,pwd) values(?,?)";
        Object[] paramsValue = {admin.getUserName(),admin.getPwd()};
        super.update(sql,paramsValue);
    }

    /**
     * 查询全部
     * @return
     */
    public List<Admin> getAll(){
        String sql = "select * from admin";
        List<Admin> list = super.query(sql,null,Admin.class);
        return  list;
    }

    /**
     *  根据条件查询（主键）
     * @param id
     * @return
     */
    public Admin findById(int id){
        String sql = "select * from admin where id=?";
        List<Admin> list = super.query(sql,new Object[]{id},Admin.class);
        return (null==list||list.isEmpty()) ? null : list.get(0);
    }

}
