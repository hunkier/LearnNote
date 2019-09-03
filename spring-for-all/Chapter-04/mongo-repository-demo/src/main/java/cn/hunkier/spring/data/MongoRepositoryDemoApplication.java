package cn.hunkier.spring.data;

import cn.hunkier.spring.data.converter.MoneyReadCoverter;
import cn.hunkier.spring.data.model.Coffee;
import cn.hunkier.spring.data.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableMongoRepositories
@Slf4j
public class MongoRepositoryDemoApplication implements CommandLineRunner {

	@Autowired
	private CoffeeRepository coffeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(MongoRepositoryDemoApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions(){
		return new MongoCustomConversions(Arrays.asList(new MoneyReadCoverter()));
	}

	@Override
	public void run(String... args) throws Exception {
		final Coffee espresso = Coffee.builder()
				.name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date())
				.updateTime(new Date())
				.build();
		final Coffee latte = Coffee.builder()
				.name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.createTime(new Date())
				.updateTime(new Date())
				.build();

		coffeeRepository.insert(
				Arrays.asList(espresso, latte)
		);
		coffeeRepository.findAll(Sort.by("name"))
		.forEach(coffee -> log.info("Save Coffe {}", coffee));

		Thread.sleep(1000);
		latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
		latte.setUpdateTime(new Date());
		coffeeRepository.save(latte);

		coffeeRepository.findByName("latte")
				.forEach(coffee -> log.info("Coffee {}", coffee));

		coffeeRepository.deleteAll();
	}
}
