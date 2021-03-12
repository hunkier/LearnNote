package cn.hunkier.spring.data.mybatispagehelperdemo;

import cn.hunkier.spring.data.mapper.CoffeeMapper;
import cn.hunkier.spring.data.model.Coffee;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("cn.hunkier.spring.data.mapper")
public class MybatisPagehelperDemoApplication implements ApplicationRunner {

    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisPagehelperDemoApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeMapper.findAllWithRowBounds(new RowBounds(1,3))
        .forEach(coffee -> log.info("Page(1) Coffee {}",coffee));

        log.info("=======================");

        coffeeMapper.findAllWithRowBounds(new RowBounds(1,0))
                .forEach(coffee -> log.info("Page(2) Coffee {}",coffee));

        log.info("-----------------------");

        coffeeMapper.findAllWithParam(1,3)
                .forEach(coffee -> log.info("Page(3) Coffee {}",coffee));
        final List<Coffee> list = coffeeMapper.findAllWithParam(2, 3);
        final PageInfo pageInfo = new PageInfo(list);
        log.info("PageInfo: {} ",pageInfo );
    }
}
