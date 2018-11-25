package cn.hunk.learn.jdbc.jdbc;

import org.junit.Test;

import java.util.List;

/**
 * Created by hunk on 2015/8/12.
 */
public class AdminDaoTest {

    @Test
    public void testUpdate()throws  Exception{
        AdminDao adminDao = new AdminDao();

        // 测试查询
        List<Admin> list = adminDao.getAll();
        System.out.println(list);
    }
}
