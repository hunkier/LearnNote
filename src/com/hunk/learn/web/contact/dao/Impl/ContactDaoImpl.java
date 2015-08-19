package com.hunk.learn.web.contact.dao.Impl;

import com.hunk.learn.web.contact.dao.ContactDao;
import com.hunk.learn.web.contact.entity.Contact;
import com.hunk.learn.web.contact.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.imageio.stream.IIOByteBuffer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunk on 2015/8/3.
 */
public class ContactDaoImpl implements ContactDao {
    /**
     * 添加联系人
     * @param contact
     */
    @Override
    public void addContact(Contact contact) {
        try {
            File file = new File(XMLUtil.FILE_PATH);
            System.out.println(XMLUtil.FILE_PATH);
            Document doc = null;
            Element rootElem = null ;
            if (!file.exists()){
                /**
                 * 需求：把contact对象保存到xml文件中
                 */
                //如果没有xml文件，则创建xml文件
                doc = DocumentHelper.createDocument();
                // 创建根标签
                rootElem = doc.addElement("contactList");
            }else{
                // 如果有xml文件，则读取xml文件
                doc = XMLUtil.getDocument();
                //如果有xml文件，读取跟标签
                rootElem = doc.getRootElement();
            }

            Element contactEle = rootElem.addElement("contact");

            enCode(contact, contactEle);

            // 把Document写出到xml文件
            XMLUtil.write(doc);
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }

    }

    /**
     * 更新联系人信息
     * @param contact
     */
    @Override
    public void updateContact(Contact contact) {
        /**
         * 需求： 修改id值为2的联系人
         * 	1）查询id值为2的contact标签
         *  2）修改contact标签的内容
         */
        try {

            deleteContact(contact.getId());
            addContact(contact);

            /*
            // 1.读取xml文件
            Document doc = XMLUtil.getDocument();
            // 2.查询需要删除id的contact
            Element contactEle = ((Element) doc.selectSingleNode("//contact[@id='"+contact.getId()+"']"));

            enCode(contact,contactEle);

            XMLUtil.write(doc);
            */
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除联系人
     * @param id
     */
    @Override
    public void deleteContact(String id) {
        try {
            // 1.读取xml文件
            Document doc = XMLUtil.getDocument();
            // 2.查询需要删除id的contact
            Element contactEle = ((Element) doc.selectSingleNode("//contact[@id='"+id+"']"));
            // 删除标签
            if (null!=contactEle){
                contactEle.detach();
            }

            // 3.把Document写出到xml文件
            XMLUtil.write(doc);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所以联系人
     * @return
     */
    @Override
    public List<Contact> findAll() {
        try {
            //1.读取xml文件
            Document doc = XMLUtil.getDocument();
            // 2.创建List对象
            List<Contact> list = new ArrayList<Contact>();
            // 3.读取contact标签
            if (!doc.hasContent()){
                return  list ;
            }
            List<Element> conList = ((List<Element>) doc.selectNodes("//contact"));
            for (Element e : conList) {
                // 创建Contact对象
                Contact c = deCode(e);
                list.add(c);
            }
            return  list;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过id查询联系人信息
     * @param id
     * @return
     */
    @Override
    public Contact findById(String id) {
        try {
            Document doc = XMLUtil.getDocument();
            Element e = (Element)doc.selectSingleNode("//contact[@id='"+id+"']");
            Contact c = null;
            if (null!=e){
                c = deCode(e);
            }
        return c;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Contact deCode(Element e){
        Contact c = new Contact();
        c.setId(e.attributeValue("id"));
        c.setName(e.elementText("name"));
        c.setGender(e.elementText("gender"));
        c.setAge(Integer.parseInt(e.elementText("age")));
        c.setPhone(e.elementText("phone"));
        c.setEmail(e.elementText("email"));
        c.setQq(e.elementText("qq"));
        return  c;
    }

    private void enCode(Contact c, Element e){
        if (null == c || null ==e){
            return;
        }
        if (null==c.getId() || "".equals(c.getId())){
            /**
             * 由系统自动生成随机且唯一的ID值，赋值给联系人
             */
            String uuid = UUID.randomUUID().toString().replace("-","");
            c.setId(uuid);
        }
        e.addAttribute("id",c.getId());
        e.addElement("name").setText(c.getName());
        e.addElement("gender").setText(c.getGender());
        e.addElement("age").setText(c.getAge() + "");
        e.addElement("phone").setText(c.getPhone());
        e.addElement("email").setText(c.getEmail());
        e.addElement("qq").setText(c.getQq());

    }

    /**
     * 根据姓名查询是否重复
     *
     * @param name
     * @return true:重复<br/> false:不重复
     */
    @Override
    public boolean checkContact(String name) {
        // 查询name标签的内容和传入的那么值是否一致？
        Document doc = XMLUtil.getDocument();
        Element nameEle = (Element) doc.selectSingleNode("//name[text()='"+name+"']");
        return  null != nameEle;
    }
}
