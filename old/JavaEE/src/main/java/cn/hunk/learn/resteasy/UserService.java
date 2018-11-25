package cn.hunk.learn.resteasy;

import cn.hunk.learn.web.contact.dao.ContactDao;
import cn.hunk.learn.web.contact.dao.Impl.ContactDaoMySQLImpl;
import cn.hunk.learn.web.contact.entity.Contact;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hunk on 2015/8/19.
 */
 @Path("/hunk")
public class UserService {
    /**
     * 初始化数据
     */
    private static Map<Integer,Object> map = new HashMap<Integer, Object>();

    private ContactDao dao = new ContactDaoMySQLImpl();

    /**
     * 获取指定id的用户信息
     * @param id
     * @return
     */
    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    public Contact getById(@PathParam("id")String id){
        return dao.findById(id);
    }

    /**
     * 获取所有用户列表
     * @return
     */
    @GET
    @Path("/users")
    @Produces("application/json")
    public List<Contact> getAll(){
        return  dao.findAll();
    }

    /**
     * 获取用户传入的信息
     * @param msg
     * @return
     */
    @GET
    @Path("/user/trans/{msg}")
    public String getMessage(@PathParam("msg")String msg){
        return "[hello dear!]" + ", MSG:{" + msg +"}";
    }
}
