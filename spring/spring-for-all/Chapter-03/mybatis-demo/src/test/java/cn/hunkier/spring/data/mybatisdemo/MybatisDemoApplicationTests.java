package cn.hunkier.spring.data.mybatisdemo;

import cn.hunkier.spring.data.mybatisdemo.mapper.CoffeeMapper;
import cn.hunkier.spring.data.mybatisdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisDemoApplicationTests {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Test
    public void contextLoads() {
        Coffee c = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0)).build();
        int count = coffeeMapper.save(c);
        log.info("Save {} coffee : {} ", count, c);

        c = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0)).build();
        count = coffeeMapper.save(c);
        log.info("Save {} coffee : {} ", count, c);

        final Coffee coffee = coffeeMapper.findById(c.getId());
        log.info("Find Coffee : {} ", c);

    }

}
