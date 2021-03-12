package cn.hunkier.spring.springbucks.repository;

import cn.hunkier.spring.springbucks.model.CoffeeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;

@Repository
public class CoffeeOrderRepository {
    @Autowired
    private  DatabaseClient databaseClient;

    public Mono<Long> save(CoffeeOrder order){

        return databaseClient.insert().into("t_order")
                .value("customer", order.getCustomer())
                .value("state", order.getState().ordinal())
                .value("create_time", order.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .value("update_time", order.getUpdateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .fetch()
                .first()
                .flatMap(m -> Mono.just(((Long) m.get("ID"))))
                .flatMap(id -> Flux.fromIterable(order.getItems())
                        .flatMap(c -> databaseClient.insert().into("t_order_coffee")
                                .value("coffee_order_id", id)
                                .value("items_id", c.getId())
                                .then()
                        ).then(Mono.just(id))

                );

    }
}
