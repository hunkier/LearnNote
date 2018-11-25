package cn.hunkier.juc;

/**
 * 题目：判断打印的“one” or “two”？
 *
 * 1.两个普通同步方法，两个线程，标准打印？ // one two
 * 2.新增Thread.sleep() 给getOne，打印？ // one two
 * 3.新增普通方法 getThree(), 打印？      // three one two
 * 4.两个普通同步方法，两个Number对象，打印？// two one
 * 5.修改getOne()为静态同步方法，打印？     // two one
 * 6.修改两个方法均为静态同步方法，一个Number对象，打印？ // one two
 * 7.一个静态同步方法，一个非静态同步方法，两个Number对象，打印？ // two one
 * 8.两个静态同步方法，两个Number对象，打印？     // one two
 *
 * 线程八锁的关键
 * ①非静态方法的锁默认为 this， 非静态方法的锁对应的 Class 实例
 * ②某一时刻内只能有一个线程持有锁，无论几个方法
 */
public class TestThreadPool8Monitor {

    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();

        new Thread(()->{
            number.getOne();;
        }).start();
        new Thread(()->{
//            number.getTwo();
            number2.getTwo();
        }).start();

        /*
        new Thread(()->{
            number.getThree();
        }).start();
        */
    }


    public static class Number{

        public static synchronized void getOne(){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("one");
        }

        public static synchronized void getTwo(){
            System.out.println("two");
        }

        public  void getThree(){
            System.out.println("three");
        }
    }

}
