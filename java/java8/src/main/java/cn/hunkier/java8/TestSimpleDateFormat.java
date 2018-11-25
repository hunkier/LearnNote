package cn.hunkier.java8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TestSimpleDateFormat {

    public static void main(String[] args) throws  Exception{
/*
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(pool.submit(new Callable<Date>() {
                @Override
                public Date call() throws Exception {
                    return DateFormatThreadLocal.convert("20180504");
                }
            }));
        }

        for (Future<Date> dateFuture : result) {
            System.out.println(dateFuture.get().toString());
        }

        pool.shutdown();
        */

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        Callable<LocalDate> task = new Callable<LocalDate>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20180504",dtf);
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<LocalDate> result : results) {
            System.out.println(result.get());
        }

        pool.shutdown();

    }
}
