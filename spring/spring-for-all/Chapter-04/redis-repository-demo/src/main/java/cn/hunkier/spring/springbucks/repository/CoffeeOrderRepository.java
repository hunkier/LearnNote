package cn.hunkier.spring.springbucks.repository;

import cn.hunkier.spring.springbucks.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
