package cn.hunkier.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一、 创建执行线程的方式三：实现Callable接口。相对于Runnable接口，方法可以有返回值，并且可以抛出异常。
 *
 * 二、 执行 Callable 方式，需要FutureTask实现类的支持，用于接收运算结果。 FutureTask 是 Future接口的实现类
 */
public class TestCallable {

    public static void main(String[] args) {
        ThreadCallableDemo td = new ThreadCallableDemo();

        // 1.执行 Callable 方式， 需要 FutureTask 实现类的支持， 用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(td);

        new Thread(result).start();

        // 2.接收线程运算后的结果
        try {
            Integer sum = result.get();
            System.out.println(sum);
            System.out.println("-----------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}

class ThreadCallableDemo implements Callable<Integer>{
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        int sum = 0 ;
        for (int i = 0; i <= 100000; i++) {
            sum += i;
        }
        return sum;
    }
}
