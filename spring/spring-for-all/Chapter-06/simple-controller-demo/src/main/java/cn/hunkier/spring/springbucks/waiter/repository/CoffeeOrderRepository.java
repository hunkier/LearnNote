package cn.hunkier.spring.springbucks.waiter.repository;

import cn.hunkier.spring.springbucks.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
