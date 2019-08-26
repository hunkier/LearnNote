package cn.hunkier.spring.data.mybatisgeneratordemo;

import cn.hunkier.spring.data.mybatis.mapper.CoffeeMapper;
import cn.hunkier.spring.data.mybatis.model.Coffee;
import cn.hunkier.spring.data.mybatis.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("cn.hunkier.spring.data.mybatis.mapper")
public class MybatisGeneratorDemoApplication implements ApplicationRunner {

    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        generateArtifacts();
        playWithArtifacts();
    }

    private void generateArtifacts() throws Exception{
        List<String> warnings = new ArrayList<>();
        final ConfigurationParser cp = new ConfigurationParser(warnings);
        final Configuration configuration = cp.parseConfiguration(
                this.getClass().getResourceAsStream("/generatorConfig.xml")
        );
        final DefaultShellCallback callback = new DefaultShellCallback(true);
        final MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        warnings.forEach(log::warn);
    }

    private void playWithArtifacts() {
        Coffee espresso = new Coffee()
                .withName("espresso")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        coffeeMapper.insert(espresso);

        Coffee latte = new Coffee()
                .withName("latte")
                .withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .withCreateTime(new Date())
                .withUpdateTime(new Date());
        coffeeMapper.insert(latte);

        Coffee s = coffeeMapper.selectByPrimaryKey(1L);
        log.info("Coffee {}", s);

        CoffeeExample example = new CoffeeExample();
        example.createCriteria().andNameEqualTo("latte");
        List<Coffee> list = coffeeMapper.selectByExample(example);
        list.forEach(e -> log.info("selectByExample: {}", e));
    }

}
