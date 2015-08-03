package com.hunk.learn.web.contact.dao;

import com.hunk.learn.web.contact.entity.Contact;

import java.util.List;

/**
 * 联系人DAO接口
 * Created by hunk on 2015/8/3.
 */
public interface ContactDao {
    void addContact(Contact contact);// 添加联系人
    void updateContact(Contact contact);// 修改联系人
    void deleteContact(String id);//  删除联系人
    List<Contact> findAll();// 查询所有的联系人
    Contact findById(String id);// 根据编号查询
}
