package cn.hunkier.spring.data.transactionpropagationdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
public class TransactionPropagationDemoApplication implements CommandLineRunner {

    @Autowired
    private FooService fooService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TransactionPropagationDemoApplication.class, args);
    }

    /**
     * 测试内外层事务（嵌套）出现异常时，数据保存的结果测试
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {


        try {
            fooService.invokeInsertTheRollback();
        } catch (Exception e) {

        }
        log.info("AAA {}", logCount("SELECT COUNT(*) FROM FOO WHERE BAR='AAA' "));
        log.info("BBB {}", logCount("SELECT COUNT(*) FROM FOO WHERE BAR='BBB' "));

    }

    private Long logCount(String sql){
        final Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count;
    }
}
