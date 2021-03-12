package cn.hunkier.spring.springbucks.jpacomplexdemo;

import cn.hunkier.spring.springbucks.jpacomplexdemo.model.BaseEntity;
import cn.hunkier.spring.springbucks.jpacomplexdemo.model.Coffee;
import cn.hunkier.spring.springbucks.jpacomplexdemo.model.CoffeeOrder;
import cn.hunkier.spring.springbucks.jpacomplexdemo.model.OrderState;
import cn.hunkier.spring.springbucks.jpacomplexdemo.repository.CoffeeOrderRepository;
import cn.hunkier.spring.springbucks.jpacomplexdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@Slf4j
public class JpaComplexDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeRepository coffeeRepository;

	@Autowired
	private CoffeeOrderRepository orderRepository;

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(JpaComplexDemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		initOrders();
		findOrders();
		log.info("dataSource : {} ", dataSource);
	}
	private void initOrders(){
		final Coffee espresso = Coffee.builder().name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.build();

		coffeeRepository.save(espresso);
		log.info("Coffee: {} ", espresso);

		final Coffee latte = Coffee.builder().name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.build();

		coffeeRepository.save(latte);
		log.info("Coffee: {} ", latte);

		CoffeeOrder order = CoffeeOrder.builder()
				.customer("Hunkier")
				.items(Arrays.asList(espresso))
				.state(OrderState.INIT)
				.build();
		orderRepository.save(order);
		log.info("Order: {} ", order);
		order = CoffeeOrder.builder()
				.customer("Hunkier")
				.items(Arrays.asList(espresso,latte))
				.state(OrderState.INIT)
				.build();
		orderRepository.save(order);
		log.info("Order: {} ", order);

	}

	private void findOrders(){
		coffeeRepository.findAll(Sort.by(Sort.Direction.DESC,"id"))
				.forEach(coffee -> log.info("Loading {}", coffee));

		List<CoffeeOrder> list = orderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
		log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

		// 不开启事务会因为没 Session 而报 LazyInitializationException
		// 在主动调用方法上加 @Transactional，表明方法内是一个事务
		list.forEach(o->{
			log.info("Order {}", o.getId());
			o.getItems().forEach((i -> log.info(" Item {} ", i)));
		});


		list = orderRepository.findByItems_Name("latte");
		log.info("findByItems_Name : {}", getJoinedOrderId(list));


	}

	private String getJoinedOrderId(List<CoffeeOrder> list){
		return list.stream().map(BaseEntity::getId).map(Object::toString)
				.collect(Collectors.joining(","));
	}
}
