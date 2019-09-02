package cn.hunkier.spring.data.mongodemo;

import cn.hunkier.spring.data.converter.MoneyReadCoverter;
import cn.hunkier.spring.data.model.Coffee;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootApplication
@Slf4j
public class MongoDemoApplication implements ApplicationRunner {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MongoDemoApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return  new MongoCustomConversions(Arrays.asList(new MoneyReadCoverter()));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final Coffee espresso = Coffee.builder()
				.name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date())
				.updateTime(new Date())
				.build();
		final Coffee save = mongoTemplate.save(espresso);
		log.info("Coffee {} ", save);
//		mongoTemplate.remove(save);

		final List<Coffee> list = mongoTemplate.find(
				query(where("name").is("espresso")), Coffee.class
		);
		list.forEach( coffee -> log.info("Coffee {} ", coffee));

		Thread.sleep(1000);

		final UpdateResult updateResult = mongoTemplate.updateFirst(query(where("name").is("espresso")),
				new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30))
						.currentDate("updateTime"),
				Coffee.class
		);
		log.info("Update result : {}", updateResult.getModifiedCount());

		final Coffee updateOne = mongoTemplate.findById(save.getId(), Coffee.class);
		log.info("Update Result: {}" ,updateOne);

		mongoTemplate.remove(updateOne);

	}
}
