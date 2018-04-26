package com.hunk.learn.jdbc.tx;

import org.junit.Test;

/**
 * database：day18
 * Created by hunk on 2015/8/11.
 */
public class App {
    @Test
    public void testname() throws Exception{
        // 转账
        AccountDao accountDao = new AccountDao();
//        accountDao.trans();
        accountDao.trans1();
    }
}
