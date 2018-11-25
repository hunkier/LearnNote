package cn.hunkier.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *  Java8 内置四大核心函数接口
 *
 *  Consumer<T>：消费型接口
 *          void accept(T t);
 *
 *  Supplier<T>：供给型接口
 *          T get();
 *
 *  Function<T, R>：函数型接口
 *          R apply(T t);
 *
 *  Predicate<T>：断言型接口
 *          boolean test(T t);
 *
 *
 */
public class TestLambda3 {


    /**
     * Predicate</T>：断言型接口
     */
    @Test
    public void test4(){
        List<String> list = Arrays.asList("Hello","Lambda","www","ok");
        List<String> newList = filterStr(list,(s)->s.length()>3);
        newList.forEach(System.out::println);
    }


    /**
     * 需求：将满足条件的字符串，放入集合中
     * @param list
     * @param pre
     * @return
     */
    public List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)){
                result.add(s);
            }
        }
        return  result;

    }

    /**
     * Function<T, R> 函数型接口：
     */
    @Test
    public void test3(){
        String newStr = strhandler("Hello Lambda!", (str) -> str.toUpperCase());
        System.out.println(newStr);
    }

    /**
     * 需求：用于处理字符串
     * @param str
     * @param fun
     * @return
     */
    public String strhandler(String str, Function<String, String> fun){
        return fun.apply(str);
    }

    /**
     * Supplier</T>：供给型接口
     */
    @Test
    public void test2(){
        List<Integer> numList = getNumList(10, ()->(int)(Math.random()*100));
        numList.stream().forEach(System.out::println);
    }

    /**
     * 需求：产生指定个数的整数，并放入集合中
     */
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(sup.get());
        }
        return result;
    }

    /**
     * Consumer<T>：消费型接口
     */
    @Test
    public void test1(){
        happy(10000, (m) -> System.out.println("你消费了："+m + "元！"));
    }

    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }
}
