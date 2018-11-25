package cn.hunk.learn.web.contact.dao.Impl;

import cn.hunk.learn.web.contact.entity.Contact;
import cn.hunk.learn.web.contact.util.JdbcUtil;
import cn.hunk.learn.web.contact.dao.ContactDao;
import cn.hunk.learn.web.contact.entity.Contact;
import cn.hunk.learn.web.contact.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hunk on 2015/8/10.
 */
public class ContactDaoMySQLImpl implements ContactDao{
    /**
     * 添加联系人
     *
     * @param contact
     */
    @Override
    public void addContact(Contact contact) {
        Connection conn = null;
        PreparedStatement stmt = null ;

        try {
            // 获取连接
            conn = JdbcUtil.getConnection();

            // sql语句
            String sql = "insert into contact(id,name,gender,age,phone,email,qq) values(?,?,?,?,?,?,?)";

            // 创建PreparedStatement
            stmt = conn.prepareStatement(sql);

            String id = UUID.randomUUID().toString().replace("-","");
            // 设置参数
            stmt.setString(1,id);
            stmt.setString(2,contact.getName());
            stmt.setString(3,contact.getGender());
            stmt.setInt(4, contact.getAge());
            stmt.setString(5,contact.getPhone());
            stmt.setString(6,contact.getEmail());
            stmt.setString(7,contact.getQq());

            // 执行
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            JdbcUtil.close(conn,stmt);
        }
    }

    /**
     * 修改联系人
     *
     * @param contact
     */
    @Override
    public void updateContact(Contact contact) {
        Connection conn = null;
        PreparedStatement stmt = null ;

        try {
            // 获取连接
            conn = JdbcUtil.getConnection();

            // sql语句
            String sql = "update contact set id=?,name=?,gender=?,age=?,phone=?,email=?,qq=? where id=?";

            // 创建PreparedStatement
            stmt = conn.prepareStatement(sql);

            // 设置参数
            stmt.setString(1,contact.getId());
            stmt.setString(2,contact.getName());
            stmt.setString(3,contact.getGender());
            stmt.setInt(4, contact.getAge());
            stmt.setString(5, contact.getPhone());
            stmt.setString(6,contact.getEmail());
            stmt.setString(7,contact.getQq());
            stmt.setString(8,contact.getId());

            // 执行
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            JdbcUtil.close(conn,stmt);
        }
    }

    /**
     * 删除联系人
     *
     * @param id
     */
    @Override
    public void deleteContact(String id) {
        Connection conn = null;
        PreparedStatement stmt = null ;

        try {
            // 获取连接
            conn = JdbcUtil.getConnection();

            // sql语句
            String sql = "delete from contact where id=?";

            // 创建PreparedStatement
            stmt = conn.prepareStatement(sql);

            // 设置参数
            stmt.setString(1,id);

            // 执行
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            JdbcUtil.close(conn,stmt);
        }
    }

    /**
     * 查询所有的联系人
     *
     * @return
     */
    @Override
    public List<Contact> findAll() {
        Connection conn = null;
        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        try {
            // 获取连接
            conn = JdbcUtil.getConnection();

            // sql语句
            String sql = "select * from contact ";

            // 创建PreparedStatement
            stmt = conn.prepareStatement(sql);

            // 设置参数
//            stmt.setString(1,id);

            // 执行
           rs = stmt.executeQuery();
            List<Contact> list = new ArrayList<Contact>();
            while (rs.next()){
                Contact contact = new Contact();
                list.add(contact);
                contact.setId(rs.getString("id"));
                contact.setName(rs.getString("name"));
                contact.setGender(rs.getString("gender"));
                contact.setAge(rs.getInt("age"));
                contact.setEmail(rs.getString("email"));
                contact.setPhone(rs.getString("phone"));
                contact.setQq(rs.getString("qq"));
            }
            return  list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            JdbcUtil.close(conn,stmt,rs);
        }
    }

    /**
     * 根据编号查询
     *
     * @param id
     * @return
     */
    @Override
    public Contact findById(String id) {
        Connection conn = null;
        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        try {
            // 获取连接
            conn = JdbcUtil.getConnection();

            // sql语句
            String sql = "select * from  contact where id=?";

            // 创建PreparedStatement
            stmt = conn.prepareStatement(sql);

            // 设置参数
            stmt.setString(1,id);

            // 执行
            rs =  stmt.executeQuery();
            if (rs.next()){
                Contact contact = new Contact();
                contact.setId(rs.getString("id"));
                contact.setName(rs.getString("name"));
                contact.setGender(rs.getString("gender"));
                contact.setAge(rs.getInt("age"));
                contact.setEmail(rs.getString("email"));
                contact.setPhone(rs.getString("phone"));
                contact.setQq(rs.getString("qq"));
                return  contact;
            }else{
                return null ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            JdbcUtil.close(conn,stmt,rs);
        }

    }

    /**
     * 根据姓名查询是否重复
     *
     * @param name
     * @return true:重复<br/> false:不重复
     */
    @Override
    public boolean checkContact(String name) {
        {
            Connection conn = null;
            PreparedStatement stmt = null ;
            ResultSet rs = null;
            try {
                // 获取连接
                conn = JdbcUtil.getConnection();

                // sql语句
                String sql = "select * from contact where name=?";

                // 创建PreparedStatement
                stmt = conn.prepareStatement(sql);

                // 设置参数
                stmt.setString(1, name);


                // 执行
                rs = stmt.executeQuery();

                if (rs.next()){
                    return  true;
                }else{
                    return  false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw  new RuntimeException(e);
            }finally {
                JdbcUtil.close(conn,stmt,rs);
            }
        }
    }
}
