package cn.hunkier.java8.test;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestTransaction {


    List<Transaction> transactions = null;

    @Before
    public void before(){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
    @Test
    public void test1(){
        transactions.stream()
                .filter((e)->e.getYear()==2011)
                .sorted((t1, t2)->Integer.compare(t1.getValue(), t2.getValue()))
                .forEach(System.out::println);
    }

    //2. 交易员都在哪些不同的城市工作过？
    @Test
    public void test2(){
        transactions.stream()
                .map((t)->t.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    //3. 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test3(){
        transactions.stream()
                .filter((t)->"Cambridge".equals(t.getTrader().getCity()))
                .map((t)-> t.getTrader())
                .forEach(System.out::println);
    }

    //4. 返回所有交易员的姓名字符串，按字母顺序排序
    @Test
    public void test4(){
        transactions.stream()
                .map((t)->t.getTrader().getName())
                .sorted()
                .forEach(System.out::println);
        System.out.println("------------------------");

        String reduce = transactions.stream()
                .map((e) -> e.getTrader().getName())
                .sorted()
                .reduce("", String::concat);
        System.out.println(reduce);
    }

    //5. 有没有交易员是在米兰工作的？
    @Test
    public void test5(){
        boolean match = transactions.stream()
                .anyMatch((t) -> "Milan".equals(t.getTrader().getCity()));
        System.out.println(match);
    }

    //6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6(){
        Optional<Integer> sum = transactions.stream()
                .filter((t) -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(sum.get());
    }

    //7. 所有交易中，最高的交易额是多少
    @Test
    public void test7(){
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compareTo);
        System.out.println(max.get());
    }

    //8. 找到交易额最小的交易
    @Test
    public void test8(){
        Optional<Transaction> min = transactions.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
        System.out.println(min.get());
    }

}
