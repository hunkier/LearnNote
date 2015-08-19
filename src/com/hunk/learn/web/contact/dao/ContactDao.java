package com.hunk.learn.web.contact.dao;

import com.hunk.learn.web.contact.entity.Contact;

import java.util.List;

/**
 * 联系人DAO接口
 * Created by hunk on 2015/8/3.
 */
public interface ContactDao {
    /**
     * 添加联系人
     * @param contact
     */
    void addContact(Contact contact);// 添加联系人

    /**
     *  修改联系人
     * @param contact
     */
    void updateContact(Contact contact);// 修改联系人

    /**
     *  删除联系人
     * @param id
     */
    void deleteContact(String id);//  删除联系人

    /**
     * 查询所有的联系人
     * @return
     */
    List<Contact> findAll();// 查询所有的联系人

    /**
     * 根据编号查询
     * @param id
     * @return
     */
    Contact findById(String id);// 根据编号查询

    /**
     * 根据姓名查询是否重复
     * @param name
     * @return true:重复<br/> false:不重复
     */
    boolean checkContact(String name);
}
