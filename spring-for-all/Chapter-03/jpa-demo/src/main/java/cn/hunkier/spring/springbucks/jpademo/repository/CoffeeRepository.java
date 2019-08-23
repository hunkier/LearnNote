package cn.hunkier.spring.springbucks.jpademo.repository;
import cn.hunkier.spring.springbucks.jpademo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long>  {
}
