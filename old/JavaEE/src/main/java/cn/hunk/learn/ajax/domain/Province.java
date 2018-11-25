package cn.hunk.learn.ajax.domain;

import com.thoughtworks.xstream.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunk on 2015/8/17.
 */
@XStreamAlias("province")
public class Province {
    @XStreamAsAttribute
    private int code;
    @XStreamAsAttribute
    private String name;

    public Province() {
    }

    public Province(int code, String name) {
        this.code = code;
        this.name = name;
    }
    @XStreamImplicit
    private List<City> citys = new ArrayList<City>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    public String toString() {
        return "Province{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", citys=" + citys +
                '}';
    }
}
