package cn.hunk.learn.web.contact.test;

import cn.hunk.learn.web.contact.entity.Contact;
import cn.hunk.learn.web.contact.dao.ContactDao;
import cn.hunk.learn.web.contact.dao.Impl.ContactDaoImpl;
import cn.hunk.learn.web.contact.entity.Contact;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 联系人操作实现类的测试类
 * Created by hunk on 2015/8/3.
 */
public class TestContactOperatorImpl {
    ContactDao operator = null ;

    /**
     * 初始化对象实例
     */
    @Before
    public void init(){
        operator = new ContactDaoImpl();
    }

    @Test
    public void testAddContact(){
        Contact contact = new Contact();
        contact.setName("zhangsan");
        contact.setGender("男");
        contact.setAge(20);
        contact.setPhone("13888888888");
        contact.setEmail("hunk@qq.com");
        contact.setQq("324808319401");
        operator.addContact(contact);
    }

    @Test
    public void testFindAll(){
        List<Contact> all = operator.findAll();
        for (Contact contact : all) {
            System.out.println(contact);
        }
    }

    @Test
    public  void testFindById(){
        Contact c = operator.findById("6b92e6ecd11e4169b98e5465abdb4afe");
        System.out.println(c);
    }

    @Test
    public void testUpdate(){
        Contact c = operator.findById("6b92e6ecd11e4169b98e5465abdb4afe");
//       c.setQq("325256234");
        c.setGender("男");
        operator.updateContact(c);
        testFindById();

    }

    @Test
    public void testDeleteContact(){
        testFindAll();
        operator.deleteContact("dafdc7721e1c497d9950ff8d87dea3a6");

    }
}
