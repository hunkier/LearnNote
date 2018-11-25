package cn.hunk.learn.web.eg.dao;

import cn.hunk.learn.web.eg.entity.Admin;
import cn.hunk.learn.web.eg.entity.Admin;

/**
 * 2.数据库访问层，接口设计
 * Created by hunk on 2015/8/11.
 */
public interface IAdminDao {
    /**
     * 保存
     * @param admin
     */
    public void save(Admin admin);

    /**
     * 根据用户名密码查询
     * @param admin
     * @return
     */
    public Admin findByNameAndPwd(Admin admin);

    /**
     * 检查用户名是否存在
     * @param name 要检查的用户名
     * @return  true 表示用户名已经存在；否则用户名不存在
     */
    public boolean userExists(String name);
}
