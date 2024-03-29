package cn.hunkier.spring.springbucks.service;

import cn.hunkier.spring.springbucks.model.Coffee;
import cn.hunkier.spring.springbucks.model.CoffeeOrder;
import cn.hunkier.spring.springbucks.model.OrderState;
import cn.hunkier.spring.springbucks.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder(String customer, Coffee...coffee){
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(
                        Arrays.asList(coffee)
                ))
                .state(OrderState.INIT)
                .build();
        final CoffeeOrder save = orderRepository.save(order);
        log.info("New Order: {}", save);
        return save;
    }

    public boolean updateState(CoffeeOrder order, OrderState state){
        if (state.compareTo(order.getState()) <=0 ){
            log.warn("Wrong State order: {} {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order: {}", order);
        return true;
    }
}
