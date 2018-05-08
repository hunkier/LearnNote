package com.hunk.juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch ： 闭锁，在完成有些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */

public class TestCountDownLatch {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(50);
        LatchDemo ld = new LatchDemo(latch);
        Instant start = Instant.now();

        for (int i = 0; i < 50; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();

        System.out.println("耗费时间为： " + Duration.between(start,end).toMillis());
    }

}

class LatchDemo implements Runnable{

    private CountDownLatch latch ;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 50000; i++) {
                if (i%2==0){
                    System.out.println(i);
                }
            }
        }finally {
            latch.countDown();
        }

    }
}
