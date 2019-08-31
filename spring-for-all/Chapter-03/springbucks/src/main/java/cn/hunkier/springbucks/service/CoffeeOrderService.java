package cn.hunkier.springbucks.service;

import cn.hunkier.springbucks.model.Coffee;
import cn.hunkier.springbucks.model.CoffeeOrder;
import cn.hunkier.springbucks.model.OrderState;
import cn.hunkier.springbucks.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder(String customer, Coffee... coffees){
        final CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();
        final CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order: {}", saved);
        return saved;
    }


    public boolean updateState(CoffeeOrder order, OrderState state){
        if (state.compareTo(order.getState()) <=0 ){
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return  false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Update Order: {}", order);
        return  true;
    }
}
