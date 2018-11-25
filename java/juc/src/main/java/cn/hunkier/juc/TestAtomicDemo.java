package cn.hunkier.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、 i++ 的原子性问题： i++ 的实际上分为三个步骤“读-改-写”
 *          int i = 10 ;
 *          i = i++ ; // 10
 *
 *          int temp = i;
 *          i = i + 1;
 *          i = tem;
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
          AtomicDemo ad = new AtomicDemo();

        for (int i = 0; i < 100; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable{


//    private  int serialNumber ;
//    private volatile int serialNumber ;
    private AtomicInteger serialNumber = new AtomicInteger(0);

    @Override
    public void run() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber() {
//        return ++serialNumber;
        return serialNumber.incrementAndGet();
    }
}
