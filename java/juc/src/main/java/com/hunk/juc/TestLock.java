package com.hunk.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一、 用于解决多线程安全问题的方式
 *
 * synchronized：隐式锁
 * 1.同步代码块
 *
 * 2.同步方法
 *
 *
 * jdk 1.5 后：
 * 注意： 是一个显示锁，需要通过 lock() 方法上锁， 必须通过 unlock() 方法释放锁
 *
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        for (int i = 0; i < 10; i++) {
            new Thread(ticket).start();
        }
    }
}

class Ticket implements Runnable{

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while (true) {
            lock.lock();
            try {
                if (tick > 0) {

                    Thread.sleep(200);
                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为： " + --tick);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

    }
}
