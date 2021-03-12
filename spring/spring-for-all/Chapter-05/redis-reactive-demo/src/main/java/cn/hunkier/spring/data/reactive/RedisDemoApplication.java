package cn.hunkier.spring.data.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootApplication
public class RedisDemoApplication implements ApplicationRunner {

	private static  final String KEY = "COFFEE_MENU";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ReactiveStringRedisTemplate redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Bean
	ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory factory){
		return  new ReactiveStringRedisTemplate(factory);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final ReactiveHashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
		final CountDownLatch cdl = new CountDownLatch(1);


		List<Coffee> list = jdbcTemplate.query("select * from t_coffee", (rs, rowNum) ->
			Coffee.builder()
					.id(rs.getLong("id"))
					.name(rs.getString("name"))
					.price(rs.getLong("price"))
					.build()
		);

		Flux.fromIterable(list)
				.publishOn(Schedulers.single())
				.doOnComplete(()->log.info("list ok"))
				.flatMap(coffee -> {
					log.info("try to put {} {}", coffee.getName(), coffee.getPrice());
					return hashOps.put(KEY, coffee.getName(), coffee.getPrice().toString());
				})
				.doOnComplete(()->log.info("set ok"))
				.concatWith(redisTemplate.expire(KEY, Duration.ofMinutes(1)))
				.doOnComplete(()-> log.info("expire ok"))
				.onErrorResume(e->{
					log.error("exception {}", e.getMessage());
					return Mono.just(false);
				})
				.subscribe(b-> log.info("Boolean: {}",b),
						e -> log.error("Exception {}", e.getMessage()),
						()-> cdl.countDown()
						);
		log.info("Waiting");
		cdl.await();

	}
}
