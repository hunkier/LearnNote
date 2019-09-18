package cn.hunkier.spring.springbucks;

import cn.hunkier.spring.springbucks.model.Coffee;
import cn.hunkier.spring.springbucks.model.CoffeeOrder;
import cn.hunkier.spring.springbucks.model.OrderState;
import cn.hunkier.spring.springbucks.repository.CoffeeRepository;
import cn.hunkier.spring.springbucks.service.CoffeeOrderService;
import cn.hunkier.spring.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMajorAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAspectJAutoProxy
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
        log.info("All Coffee : {}", coffeeRepository.findAll());
        PersistentMoneyMajorAmount a;
        final Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
        if (latte.isPresent()){
            final CoffeeOrder order = orderService.createOrder("Hunkkier", latte.get());
            log.info("Update INIT to PAID: {}",orderService.updateState(order, OrderState.PAID));
            log.info("Update  PAID to INIT: {}",orderService.updateState(order, OrderState.INIT));
        }

    }
}
