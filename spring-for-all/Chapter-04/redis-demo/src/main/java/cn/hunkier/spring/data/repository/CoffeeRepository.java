package cn.hunkier.spring.data.repository;

import cn.hunkier.spring.data.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
