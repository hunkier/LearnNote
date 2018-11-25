package cn.hunkier.java8;

import org.junit.Test;

public class TestDefaultInterface  {

    @Test
    public void test1(){
        MyClass myClass = new MyClass();
        String name = myClass.getName();
        System.out.println(name);

        MyI.show();
    }
}
