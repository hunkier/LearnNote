package cn.hunkier.spring.springbucks.service;

import cn.hunkier.spring.springbucks.model.CoffeeOrder;
import cn.hunkier.spring.springbucks.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private CoffeeOrderRepository repository;

    @Autowired
    private DatabaseClient client;

    public Mono<Long> create(CoffeeOrder order){
        return repository.save(order);
    }
}
