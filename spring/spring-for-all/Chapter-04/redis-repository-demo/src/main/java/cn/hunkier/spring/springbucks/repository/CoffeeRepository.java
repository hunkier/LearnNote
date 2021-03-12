package cn.hunkier.spring.springbucks.repository;

import cn.hunkier.spring.springbucks.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
