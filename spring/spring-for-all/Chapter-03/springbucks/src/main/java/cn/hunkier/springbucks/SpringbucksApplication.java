package cn.hunkier.springbucks;

import cn.hunkier.springbucks.model.Coffee;
import cn.hunkier.springbucks.model.CoffeeOrder;
import cn.hunkier.springbucks.model.OrderState;
import cn.hunkier.springbucks.repository.CoffeeOrderRepository;
import cn.hunkier.springbucks.repository.CoffeeRepository;
import cn.hunkier.springbucks.service.CoffeeOrderService;
import cn.hunkier.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class SpringbucksApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbucksApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("All Coffee: {}" ,coffeeRepository.findAll());

        final Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
        if (latte.isPresent()){
            final CoffeeOrder order = orderService.createOrder("hunkier", latte.get());
            log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
            log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
        }
    }
}
