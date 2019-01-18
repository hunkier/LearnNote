package cn.hunkier.netty.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicUpdaterTest {

    public static void main(String[] args) {
        Person person = new Person();


/*

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(person.age++);
            }).start();
        }

*/



        AtomicIntegerFieldUpdater<Person> ageUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
/*
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ageUpdater.getAndIncrement(person));
            }).start();
        }
        */

    }

}
    class Person {
        volatile int age = 1;
    }
