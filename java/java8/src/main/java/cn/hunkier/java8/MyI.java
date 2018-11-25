package cn.hunkier.java8;

public interface MyI {

    default String getName(){
        return "B";
    }

    public static void show(){
        System.out.println("接口中的静态方法");
    }
}
