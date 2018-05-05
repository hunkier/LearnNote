package com.hunk.java8;

public class MyClass /*extends SubClass */implements MyI ,MyF{
    @Override
    public String getName() {
        return MyF.super.getName();
    }
}
