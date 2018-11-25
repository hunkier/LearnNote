package cn.hunkier.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 生产者消费者案例：
 */
public class TestProductAndConsumerForLock {

    public static void main(String[] args) {
        final  Clerk clerk = new Clerk();

        Runnable productor = ()-> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
                clerk.get();
            }
        };

        Runnable consumer = ()->{
            for (int i = 0; i < 20; i++) {
                clerk.sale();
            }
        };

        new Thread(productor, "Product A ").start();
        new Thread(consumer, "Consumer B ").start();


    }

    public static class Clerk{
        private int product = 0 ;

        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        /**
         * 进货
         */
        public void get(){
            lock.lock();

            try {
                if (product > 1){
                    System.out.println("产品已满！");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(Thread.currentThread().getName() + " : " + ++ product);
                condition.signalAll();
            }finally {
                lock.unlock();
            }


        }

        /**
         * 卖货
         */
        public void sale(){
            lock.lock();

            try {
                if (product<=0){
                    System.out.println("缺货！");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(Thread.currentThread().getName() + " : " + --product);
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }

    }
}
