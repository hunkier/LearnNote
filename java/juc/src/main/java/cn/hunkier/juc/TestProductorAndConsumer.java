package cn.hunkier.juc;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 生产者和消费者案例
 */
public class TestProductorAndConsumer {

    public static void main(String[] args) {

       final Clerk clerk = new Clerk();

        Runnable productor = ()->{
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk.get();
            }
        };

        Runnable consumer = ()->{
            for (int i = 0; i < 20; i++) {
                clerk.sale();
            }
        };

        new Thread(productor,"Productor A ").start();
        new Thread(consumer,"Consumer B ").start();

        new Thread(productor,"Productor C ").start();
        new Thread(consumer,"Consumer D ").start();
    }


    /**
     * 店员
     */
    public static class Clerk{
        private int product = 0 ;

        /**
         * 进货
         */
        public synchronized void get(){
            while (product > 10){ // 为了避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("产品已满！");
                try {
                    this.wait();
                } catch (InterruptedException e) {

                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++ product);
            this.notifyAll();
        }

        /**
         * 卖货
         */
        public synchronized void sale(){
            while (product <=0 ){
                System.out.println("缺货！");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            this.notifyAll();
        }
    }
}
