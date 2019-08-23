package cn.hunkier.spring.springbucks.jpacomplexdemo.repository;

import cn.hunkier.spring.springbucks.jpacomplexdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository
public interface CoffeeOrderRepository  extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByItems_Name(String name);
}
