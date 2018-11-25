package cn.hunkier;

public class Application {

    public static void main(String[] args) {
        Generator gen = new Generator();
        System.out.println("Generated strings: " + gen.createStrings());
    }

    public void testMultiJar(){
        Generator gen = new Generator();
        System.out.println("Generated strings: " + gen.createStrings());
    }


}
