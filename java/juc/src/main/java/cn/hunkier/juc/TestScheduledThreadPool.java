package cn.hunkier.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TestScheduledThreadPool {
    public static void main(String[] args) throws Exception{
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        List<Future<Integer>> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<Integer> future = pool.schedule(() -> {
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() + " : " + num);
                return num;
            }, 1, TimeUnit.SECONDS);
            results.add(future);
        }

        pool.shutdown();
        for (Future<Integer> future : results) {
            System.out.println(future.get().toString());
        }
    }
}
