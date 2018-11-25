package cn.hunk.learn.session.history.Dao;

import cn.hunk.learn.session.history.entity.Product;
import cn.hunk.learn.session.history.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类中存放对Prodcut对象的CRUD方法
 * Created by hunk on 2015/7/31.
 */
public class ProductDao {
    // 模拟数据库，存放所有商品数据
    private static List<Product> data = new ArrayList<Product>();

    /**
     * 初始化商品
     */
    static {
        for (int i = 0; i < 10; i++) {
            data.add(new Product(i+"","笔记本"+i, "LNOO"+i,34.0+i));
        }
    }

    /**
     * 提供查询所有商品的方法
     */
    public List<Product> findAll(){
        return  data;
    }

    /**
     * 提供根据编号查询商品的方法
     */
    public Product findById(String id){
        for (Product p : data){
            if (p.getId().equals(id)){
                return p;
            }
        }
        return  null ;
    }


}
