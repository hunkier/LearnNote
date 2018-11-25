package cn.hunk.learn.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.*;

/**
 * Created by hunk on 2015/8/5.
 */
public class ForEachTag extends SimpleTagSupport {
    private Object items;//需要遍历的数据。List和Map
    private String var;//每个元素的名称

    public void setItems(Object items) {
        this.items = items;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 遍历items数据
        // List
        PageContext pageContext = ((PageContext) this.getJspContext());
        /*
        if (itmes instanceof List){
            List list = ((List) itmes);
            for (Object obj : list) {
                // 把每一个对象放入到域对象中(pageContext)
                pageContext.setAttribute(var, obj);
                this.getJspBody().invoke(null);
            }
        }

        // Map
        if (itmes instanceof Map){
            Map map = ((Map) itmes);
            Set<Map.Entry> set = map.entrySet();
            for (Map.Entry entry : set) {
                // 把每个对象放入到域对象中(pageContext)
                pageContext.setAttribute(var, entry);
                // 显示标签体内容
                this.getJspBody().invoke(null);
            }
        }
        */
        // 简化代码
        // 思路：
            // 1) list          -> Collection
            // 2) map.entryset  -> Collection
        Collection colls = null ;
        if (items instanceof  List){
            colls = (List)items;
        }

        if (items instanceof  Map){
            colls = ((Map) items).entrySet();
        }

        for (Object obj : colls) {
            // 把每个对象放入到域对象中(pageContext)
            pageContext.setAttribute(var, obj);
            // 显示标签体内容
            this.getJspBody().invoke(null);
        }
    }
}
