package cn.hunk.learn.jdbc.batch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunk on 2015/8/11.
 */
public class App {
    /**
     * 测试批处理草早
     * @throws Exception
     */
    @Test
    public void testBatch() throws  Exception{
        // 模拟数据
        List<Admin> list = new ArrayList<Admin>();
        for (int i = 1; i < 21; i++) {
            Admin admin = new Admin();
            admin.setUserName("Jack"+i);
            admin.setPwd("888"+i);
            list.add(admin);
        }

        // 保存
        AdminDao dao = new AdminDao();
        dao.save(list);
    }
}
