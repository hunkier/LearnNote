package cn.hunkier.spring.springbucks.jpademo.repository;

import cn.hunkier.spring.springbucks.jpademo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface CoffeeOrderRepository  extends JpaRepository<CoffeeOrder, Long> {

}
