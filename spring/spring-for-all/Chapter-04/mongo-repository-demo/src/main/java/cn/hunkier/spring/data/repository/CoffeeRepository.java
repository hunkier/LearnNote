package cn.hunkier.spring.data.repository;

import cn.hunkier.spring.data.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {

    List<Coffee> findByName(String name);
}
