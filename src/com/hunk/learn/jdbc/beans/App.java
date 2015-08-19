package com.hunk.learn.jdbc.beans;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hunk on 2015/8/12.
 */
public class App {
    /**
     * 对javabean的基本操作
     */
    @Test
    public void test1() throws  Exception{
        // a. 基本操作
        Admin admin = new Admin();
        // admin.setUserName("jack");;
        // admin.setPwd("999");

        // b. BeanUtils组件实现对象属性拷贝
        BeanUtils.copyProperty(admin,"userName", "jack");
        BeanUtils.copyProperty(admin, "age", 18);

        // 总结1： 对于基本数据类型，会自动进行类型转换！

        // c. 对象拷贝
        Admin newAdmin = new Admin();
//        BeanUtils.copyProperties(newAdmin, admin);
        System.out.println(admin);
        System.out.println(newAdmin);


        // d. map数据，拷贝到对象中
        Admin adminMap = new Admin();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userName", "Jerry");
        map.put("age", 29);
        // 注意：map中的key要与javabean中的属性名称一致
        BeanUtils.populate(adminMap,map);
        System.out.println(adminMap);

    }

    /**
     * 自定义日期转换类型
     * @throws Exception
     */
    @Test
    public void test2() throws  Exception{
        // 模拟表单数据
        String name = "jack";
        String age = "20";
        String birth = "    ";

        // 对象
        Admin admin = new Admin();

        // 注册日期类型转换器： 1，自定义的方式
        ConvertUtils.register(new Converter() {
            // 转换器的内部实现方法，需要重写
            @Override
            public Object convert(Class aClass, Object o) {
                    // 判断
                    if ( aClass != Date.class){
                        return  null ;
                    }
                if (null==o || "".equals(o.toString().trim())){
                    return  null ;
                }

                try {
                    // 字符串转换为日期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.parse(o.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }, Date.class);

        // 把表单提交的数据，封装到对象中
        BeanUtils.copyProperty(admin, "userName", name);
        BeanUtils.copyProperty(admin, "age", age);
        BeanUtils.copyProperty(admin, "birth", birth);

        System.out.println(admin);
    }

    /**
     * 使用提供的日期类型转换器工具类
     * @throws Exception
     */
    @Test
    public void test3() throws  Exception{
        // 模拟表单数据
        String name = "userName";
        String age = "20";
        String birth = null ;

        // 对象
        Admin admin = new Admin();

        // 注册日期类型转换器：2， 使用组件提供的转换器工具类
        ConvertUtils.register(new DateLocaleConverter(),Date.class);

        // 把表单提交的数据，封装的对象中
        BeanUtils.copyProperty(admin, "userName",name);
        BeanUtils.copyProperty(admin, "age",age);
        BeanUtils.copyProperty(admin, "birth",birth);

        System.out.println(admin);
    }
}
