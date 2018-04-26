package com.hunk.learn.mybatis.dao.impl;

import com.hunk.learn.mybatis.dao.UserDao;
import com.hunk.learn.mybatis.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * 用户管理dao实现类
 * Created by hunk on 2015/8/18.
 */
public class UserDaoImpl implements UserDao {
    /**
     * 注入SqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByid(int id) throws Exception {
        // 根据SqlsessionFactory创建SqlSesson
        SqlSession sqlSession = null;
        User user = null ;
        try {
            sqlSession = sqlSessionFactory.openSession();

            // 通过sqlSession查询用户信息（发起数据库操作）
            // 第一个参数statement：指定mapper映射文件statement的id，指定 时需要前边加上statement所属的命名空间
            // 第二个参数paramenter：指定 输入参数
            // selectOne返回的是单条记录，如果select返回多条记录（list集合），使用selectOne会报错
            // 根据映射文件中resultType指定输出类型
            user = sqlSession.selectOne("user.findUserById",id);
        } finally {
            if (null!=sqlSession){
                sqlSession.close();
            }
        }
        return user;
    }

    /**
     * 添加用户
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void insertUser(User user) throws Exception {
        // 根据SqlsessionFactory创建SqlSesson
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("user.insertUser",user);
            sqlSession.commit();
        } finally {
            if(null!=sqlSession){
                sqlSession.close();
            }
        }

    }

    /**
     * 查询用户列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<User> findUserList() throws Exception {
        // 根据SqlsessionFactory创建SqlSesson
        SqlSession sqlSession = null;
        List<User> list = null ;
        try {
            sqlSession = sqlSessionFactory.openSession();
           list  =  sqlSession.selectList("user.findUserList");
        } finally {
            if(null!=sqlSession){
                sqlSession.close();
            }
        }
        return list;
    }
}
