package cn.hunk.learn.mybatis.dao;

import cn.hunk.learn.mybatis.po.User;

import java.util.List;

/**
 * 用户管理Dao
 * Created by hunk on 2015/8/18.
 */
public interface UserDao {
    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    public User findUserByid(int id) throws  Exception;

    /**
     * 添加用户
     * @param user
     * @throws Exception
     */
    public void insertUser(User user) throws  Exception;

    /**
     * 查询用户列表
     * @return
     * @throws Exception
     */
    public List<User> findUserList() throws  Exception;
}
