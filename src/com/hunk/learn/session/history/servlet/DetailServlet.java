package com.hunk.learn.session.history.servlet;

import com.hunk.learn.session.history.Dao.ProductDao;
import com.hunk.learn.session.history.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by hunk on 2015/7/31.
 */
@WebServlet(name = "DetailServlet", urlPatterns = "/DetailServlet")
public class DetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 1.获取编号id
        String id = request.getParameter("id");

        // 2.到数据库中查询对应编号的商品
        ProductDao dao = new ProductDao();
        Product product = dao.findById(id);

        // 3.显示到浏览器
        PrintWriter writer = response.getWriter();


    }


    /**
     * 生成cookie的值
     * 分析：
     * 			当前cookie值                     传入商品id               最终cookie值
     *      null或没有prodHist          1                     1    （算法： 直接返回传入的id ）
     *             1                  2                     2,1 （没有重复且小于3个。算法：直接把传入的id放最前面 ）
     *             2,1                1                     1,2（有重复且小于3个。算法：去除重复id，把传入的id放最前面 ）
     *             3,2,1              2                     2,3,1（有重复且3个。算法：去除重复id，把传入的id放最前面）
     *             3,2,1              4                     4,3,2（没有重复且3个。算法：去最后的id，把传入的id放最前面）
     * @return
     */
    private String createValue(HttpServletRequest request,String id) {

        Cookie[] cookies = request.getCookies();
        String prodHist = null;
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("prodHist")){
                    prodHist = cookie.getValue();
                    break;
                }
            }
        }

        // null或没有prodHist
        if(cookies==null || prodHist==null){
            //直接返回传入的id
            return id;
        }

        // 3,21          2
        //String -> String[] ->  Collection :为了方便判断重复id
        String[] ids = prodHist.split(",");
        Collection colls = Arrays.asList(ids); //<3,21>
        // LinkedList 方便地操作（增删改元素）集合
        // Collection -> LinkedList
        LinkedList list = new LinkedList(colls);


        //不超过3个
        if(list.size()<3){
            //id重复
            if(list.contains(id)){
                //去除重复id，把传入的id放最前面
                list.remove(id);
                list.addFirst(id);
            }else{
                //直接把传入的id放最前面
                list.addFirst(id);
            }
        }else{
            //等于3个
            //id重复
            if(list.contains(id)){
                //去除重复id，把传入的id放最前面
                list.remove(id);
                list.addFirst(id);
            }else{
                //去最后的id，把传入的id放最前面
                list.removeLast();
                list.addFirst(id);
            }
        }

        // LinedList -> String
        StringBuffer sb = new StringBuffer();
        for (Object object : list) {
            sb.append(object+",");
        }
        //去掉最后的逗号
        String result = sb.toString();
        result = result.substring(0, result.length()-1);
        return result;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
