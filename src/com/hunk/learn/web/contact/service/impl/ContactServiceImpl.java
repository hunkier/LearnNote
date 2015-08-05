package com.hunk.learn.web.contact.service.impl;

import com.hunk.learn.web.contact.dao.ContactDao;
import com.hunk.learn.web.contact.dao.Impl.ContactDaoImpl;
import com.hunk.learn.web.contact.entity.Contact;
import com.hunk.learn.web.contact.exception.NameRepeatException;
import com.hunk.learn.web.contact.service.ContactService;

import java.util.List;

/**
 * 业务逻辑层
 * 处理项目中出现的业务逻辑
 * Created by hunk on 2015/8/5.
 */
public class ContactServiceImpl implements ContactService {
    private ContactDao dao = new ContactDaoImpl();

    /**
     * 添加联系人
     *
     * @param contact
     */
    @Override
    public void addContact(Contact contact) throws NameRepeatException {
        // 执行业务逻辑判断
        if (dao.checkContact(contact.getName())){
            // 重复
            /**
             * 注意：如果业务层方法出现任何异常，则返回标记（自定义异常）daoservlet
             */
            throw  new NameRepeatException("姓名重复，不可使用");
        }
        // 如果不重复，才执行添加方法
        dao.addContact(contact);
    }

    /**
     * 修改联系人
     *
     * @param contact
     */
    @Override
    public void updateContact(Contact contact) {
        dao.updateContact(contact);
    }

    /**
     * 删除联系人
     *
     * @param id
     */
    @Override
    public void deleteContact(String id) {
        dao.deleteContact(id);
    }

    /**
     * 查询所有联系人
     *
     * @return
     */
    @Override
    public List<Contact> findAll() {
        return dao.findAll();
    }

    /**
     * 根据编号查询联系人
     *
     * @param id
     * @return
     */
    @Override
    public Contact findById(String id) {
        return dao.findById(id);
    }
}
