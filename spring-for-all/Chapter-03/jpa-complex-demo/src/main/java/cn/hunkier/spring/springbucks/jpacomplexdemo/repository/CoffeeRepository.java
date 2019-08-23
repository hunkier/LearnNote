package cn.hunkier.spring.springbucks.jpacomplexdemo.repository;
import cn.hunkier.spring.springbucks.jpacomplexdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface CoffeeRepository extends BaseRepository<Coffee, Long>  {
}
