package com.hunk.java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalLong;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {

    /**
     * ForkJoin框架
     */
    @Test // 3242
    public void test1(){

        Instant start = Instant.now();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 10000000000L);

        Long sum = forkJoinPool.invoke(task);

        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }


    @Test // 3867
    public void test2(){

        Instant start = Instant.now();

        long sum = 0 ;

        for (long i = 0; i < 10000000000L; i++) {
            sum += i;
        }

        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }

    @Test // 3553
    public void test3(){
        Instant start = Instant.now();
        OptionalLong sum = LongStream.rangeClosed(0, 10000000000L)
                .parallel()
                .reduce(Long::sum);
        System.out.println(sum.getAsLong());
        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());
    }

    @Test
    public void test4(){
        System.out.println("test4");
    }
}
