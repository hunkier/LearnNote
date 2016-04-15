package com.hunk.learn.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/5.
 */
public class WhenTag extends SimpleTagSupport {
    private boolean test;

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 根据test的返回值决定是否输出标签体内容
        if (test){
            this.getJspBody().invoke(null);
        }

        //获取父标签
        ChooseTag parent = ((ChooseTag) this.getParent());
        parent.setFlag(test);

    }
}
