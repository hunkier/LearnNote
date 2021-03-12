package cn.hunkier.spring.springbucks;

import cn.hunkier.spring.springbucks.converter.MoneyReadConverter;
import cn.hunkier.spring.springbucks.converter.MoneyWriteConverter;
import cn.hunkier.spring.springbucks.model.Coffee;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.relational.core.dialect.Dialect;

import java.util.Arrays;

@SpringBootApplication
@EnableR2dbcRepositories
public class ReactiveSpringbucksApplication extends AbstractR2dbcConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSpringbucksApplication.class, args);
	}

	@Override
	public ConnectionFactory connectionFactory() {
		return new H2ConnectionFactory(
				H2ConnectionConfiguration.builder()
						.inMemory("testdb")
						.username("sa")
						.build());
	}

	@Bean
	public R2dbcCustomConversions r2dbcCustomConversions() {
		R2dbcDialect dialect = getDialect(connectionFactory());
		CustomConversions.StoreConversions storeConversions =
				CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder());
		return new R2dbcCustomConversions(storeConversions,
				Arrays.asList(new MoneyReadConverter(), new MoneyWriteConverter()));
	}

	@Bean
	public ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		StringRedisSerializer keySerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<Coffee> valueSerializer = new Jackson2JsonRedisSerializer<>(Coffee.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, Coffee> builder
				= RedisSerializationContext.newSerializationContext(keySerializer);

		RedisSerializationContext<String, Coffee> context = builder.value(valueSerializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}

}