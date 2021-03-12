package cn.hunkier.springbucks.repository;
import cn.hunkier.springbucks.model.Coffee;
import cn.hunkier.springbucks.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long>  {
}
