package com.hunk.juc;

/**
 * 一、 volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 *                     相较于 synchronized 是一种比较轻量级的同步策略
 *
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. valatile 不能保证变量的“原子性”
 *
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();

        while (true){
            if (threadDemo.isFlag()){
                System.out.println("---------------------------");
            }
        }

    }
}

class ThreadDemo implements Runnable{

    private volatile boolean flag = false;
//    private  boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        flag = true;

        System.out.printf("flag : "+flag);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
