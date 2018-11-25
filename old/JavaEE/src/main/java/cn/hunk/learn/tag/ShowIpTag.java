package cn.hunk.learn.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**标签处理器类
 * Created by hunk on 2015/8/5.
 * 1）继承SimpleTagSupport
 */
public class ShowIpTag extends SimpleTagSupport {
    /**
     * 2） 覆盖doTag方法
     * @throws JspException
     * @throws IOException
     */
    @Override
    public void doTag() throws JspException, IOException {
       // 想浏览器输出客户的ip地址
        PageContext pageContext = ((PageContext) this.getJspContext());
        HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
        String ip = request.getRemoteHost();
        JspWriter out = pageContext.getOut();
        out.write("使用自定义标签输出客户的IP地址：" +ip);
    }
}
