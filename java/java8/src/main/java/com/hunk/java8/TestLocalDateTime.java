package com.hunk.java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class TestLocalDateTime {

    /**
     * ZonedDate、ZonedTime、ZonedDateTime
     */
    @Test
    public void test8(){
        LocalDateTime hongkong = LocalDateTime.now(ZoneId.of("Hongkong"));
        System.out.println(hongkong);

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Etc/UTC"));
        ZonedDateTime japan = localDateTime.atZone(ZoneId.of("Japan"));
        System.out.println(japan);
    }

    @Test
    public void test7(){
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);
    }


    /**
     * DateTimeFormatter：格式化时间/日期
     */
    @Test
    public void test6(){
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime ldt = LocalDateTime.now();

        String strDate = ldt.format(dtf);
        System.out.println(strDate);

        System.out.println("---------------------------------------");

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strDate2 = ldt.format(dtf2);
        System.out.println(strDate2);

        TemporalAccessor ta = dtf2.parse(strDate2);
        LocalDateTime ldt2 = LocalDateTime.from(ta);
        System.out.println(ldt2);

    }

    /**
     * TemporalAdjuster : 时间校正器
     */
    @Test
    public void test5(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        // 自己定下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;
            DayOfWeek dayOfWeek = ldt4.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.FRIDAY) {
                return ldt4.plusDays(3);
            } else if (dayOfWeek == DayOfWeek.SATURDAY) {
                return ldt4.plusDays(2);
            } else {

                return ldt4.plusDays(1);
            }
        });
        System.out.println(ldt5);
    }


    /**
     * Period: 计算两个“日期之间的间隔”
     */
    @Test
    public void test4(){
        LocalDate ld1 = LocalDate.of(2017, 1, 1);
        LocalDate ld2 = LocalDate.now();

        Period period = Period.between(ld1, ld2);
        System.out.println(period);

        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }


    /**
     * 3.Duration: 计算两个日期之间的间隔
     *
     */
    @Test
    public void test3(){
        Instant ins1 = Instant.now();

        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
        Instant ins2 = Instant.now();

        Duration duration = Duration.between(ins1, ins2);
        System.out.println(duration);
        System.out.println(duration.toMillis());

        System.out.println("--------------------------------------");

        LocalTime lt1 = LocalTime.now();
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
        LocalTime lt2 = LocalTime.now();
        System.out.println(Duration.between(lt1,lt2).toMillis());

    }


    /**
     * 2.Instant： 时间戳（以Unix元年：1970年1月1日00:00:00到某个时间的毫秒值）
     */
    @Test
    public void test2(){
        Instant ins1 = Instant.now();// 默认获取UTC时区
        System.out.println(ins1);

        OffsetDateTime odt = ins1.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(ins1.toEpochMilli());
    }


    /**
     * 1. LocalDate  LocalTime LocalDateTime
     */
    @Test
    public void test1(){

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = LocalDateTime.of(2018, 05, 04, 20, 03);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.plusYears(2);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ldt.minusMonths(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
    }
}
