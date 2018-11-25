package cn.hunk.learn.ajax.servlet;


import cn.hunk.learn.ajax.domain.City;
import cn.hunk.learn.ajax.domain.Product;
import cn.hunk.learn.ajax.domain.Province;
import com.thoughtworks.xstream.XStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunk on 2015/8/17.
 */
@WebServlet(name = "ServletDemo4", urlPatterns = "/ajax/demo4")
public class ServletDemo4 extends HttpServlet{
    private List<Province> provinces = new ArrayList<Province>();
    private XStream xs = new XStream();

    public void init()throws ServletException{
        Province sd = new Province(37, "山东省");
        Province hb = new Province(42, "湖北省");
        Province hn = new Province(41, "河南省");

        sd.getCitys().add(new City(01, "济南市"));
        sd.getCitys().add(new City(02, "青岛市"));
        sd.getCitys().add(new City(03, "淄博市"));

        hb.getCitys().add(new City(01, "武汉市"));
        hb.getCitys().add(new City(02, "黄冈市"));
        hb.getCitys().add(new City(03, "襄阳市"));

        hn.getCitys().add(new City(01, "郑州市"));
        hn.getCitys().add(new City(02, "开封市"));
        hn.getCitys().add(new City(03, "洛阳市"));

        provinces.add(sd);
        provinces.add(hb);
        provinces.add(hn);
    }
    private String usernames[] = {"admin","hunk"};
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //

        System.out.println(this.getClass().getName() +" 执行了！");
        xs.autodetectAnnotations(true);
        xs.alias("provinces", List.class);

        String xml = xs.toXML(provinces);
        System.out.println(xml);
        resp.setContentType("text/xml;charset=UTF-8");
       resp.getWriter().write(xml);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
