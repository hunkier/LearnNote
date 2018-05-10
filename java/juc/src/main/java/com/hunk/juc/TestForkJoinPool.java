package com.hunk.juc;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {

    public static void main(String[] args) {

        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0, 5000000000L);

        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为： " + Duration.between(start,end).toMillis());


    }

    @Test
    public void test1(){
        Instant start = Instant.now();
        long sum = 0 ;
        for (long i = 0; i < 5000000000L; i++) {
            sum+=i;
        }
        System.out.println(sum);
        Instant end = Instant.now();

        System.out.println("耗费时间为： " + Duration.between(start,end).toMillis());
    }



    @Test
    public void test2(){
        Instant start = Instant.now();

        long sum = LongStream.rangeClosed(0, 5000000000L)
                .parallel()
                .reduce(0L, Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();

        System.out.println("耗费时间为： " + Duration.between(start,end).toMillis());
    }





}

class ForkJoinSumCalculate extends RecursiveTask <Long>{

    private long start ;
    private long end;

    private final static long THURSHOLD = 10000;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        long count = end - start;
        if (count <= THURSHOLD){
            long sum = 0 ;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();

            return left.join()+right.join();
        }
    }
}
