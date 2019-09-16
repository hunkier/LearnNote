package cn.hunkier.spring.springbucks.repository;

import cn.hunkier.spring.springbucks.model.Coffee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import reactor.core.publisher.Mono;

public interface CoffeeRepository extends R2dbcRepository<Coffee, Long> {
    @Query("select * from t_coffee where name=$1")
    Mono<Coffee> findByName(String name);
}
