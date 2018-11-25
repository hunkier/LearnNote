package cn.hunk.learn.web.contact.service;

import cn.hunk.learn.web.contact.exception.NameRepeatException;
import cn.hunk.learn.web.contact.entity.Contact;
import cn.hunk.learn.web.contact.exception.NameRepeatException;

import java.util.List;

/**
 * Created by hunk on 2015/8/5.
 */
public interface ContactService {
    /**
     * 添加联系人
     * @param contact
     */
    void addContact(Contact contact) throws NameRepeatException;//

    /**
     * 修改联系人
     * @param contact
     */
    void updateContact(Contact contact);

    /**
     * 删除联系人
     * @param id
     */
    void deleteContact(String id);

    /**
     * 查询所有联系人
     * @return
     */
    List<Contact> findAll();

    /**
     * 根据编号查询联系人
     * @param id
     * @return
     */
    Contact findById(String id);
}
