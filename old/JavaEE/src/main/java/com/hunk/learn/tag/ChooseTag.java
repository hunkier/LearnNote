package com.hunk.learn.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/5.
 */
public class ChooseTag extends SimpleTagSupport {
    // 不是属性，而是临时变量
    private  boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 输出标签体
        this.getJspBody().invoke(null);
    }
}
