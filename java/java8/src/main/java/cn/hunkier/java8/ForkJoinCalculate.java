package cn.hunkier.java8;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start, end;

    /**
     * 定义当个任务的最大值
     */
    private final static long THRESHOLD = 100000L;

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD){ // 在当个任务范围内，直接计算
            long sum = 0 ;
            for (long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        }else { // 超出单个任务范围，拆分为子任务
            long middle = (end + start) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork(); // 拆分，并将子队列压人线程队列
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();
            long sum = left.join() + right.join();
            return sum;
        }

    }
}
