package com.hunk.learn.web.eg.util;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by hunk on 2015/8/12.
 */
public class WebUtils {

    public static <T> T copyToBean_old(HttpServletRequest request, Class<T> clazz){

        try {
            // 创建对象
            T t = clazz.newInstance();
            // 获取所有表单元素的名称
            Enumeration<String> enums = request.getParameterNames();
            // 遍历
            while(enums.hasMoreElements()){
                // 获取表单元素的名称：<input type="password" name="pwd"/>
                String name = enums.nextElement();
                // 获取对应的值
                String value = request.getParameter(name);
                //把指定属性名称对应的值进行拷贝
                BeanUtils.copyProperty(t,name,value);
            }
            return  t;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    /**
     * 请求数据的封装
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyToBean(HttpServletRequest request, Class<T> clazz){
        try {
            // 注册日期类型转换器
            // 创建对象
            T t = clazz.newInstance();
            BeanUtils.populate(t,request.getParameterMap());
            return  t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
