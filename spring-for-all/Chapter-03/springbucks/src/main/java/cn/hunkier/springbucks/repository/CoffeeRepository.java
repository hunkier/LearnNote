package cn.hunkier.springbucks.repository;
import cn.hunkier.springbucks.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>  {
}
