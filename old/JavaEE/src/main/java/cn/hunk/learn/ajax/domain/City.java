package cn.hunk.learn.ajax.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by hunk on 2015/8/17.
 */
@XStreamAlias("city")
public class City {
    @XStreamAsAttribute
    private int code;
    @XStreamAsAttribute
    private String name;

    public City() {
    }

    public City( int code,String name) {
        this.name = name;
        this.code = code;
    }

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

    @Override
    public String toString() {
        return "City{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
