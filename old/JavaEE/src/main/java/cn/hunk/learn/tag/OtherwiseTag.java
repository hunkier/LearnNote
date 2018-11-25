package cn.hunk.learn.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by hunk on 2015/8/5.
 */
public class OtherwiseTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        // 通过父标签传递，when标签中test的值
        // 获取父标签
        ChooseTag parent = ((ChooseTag) this.getParent());
        boolean test = parent.isFlag();

        if (!test){
            this.getJspBody().invoke(null);
        }
    }
}
