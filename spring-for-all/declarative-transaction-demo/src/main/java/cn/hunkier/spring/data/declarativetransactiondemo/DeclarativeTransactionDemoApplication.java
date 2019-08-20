package cn.hunkier.spring.data.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@Slf4j
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {

    @Autowired
    private FooService fooService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fooService.insertRecord();
        log.info("AAA {}", logCount("SELECT COUNT(*) FROM FOO WHERE BAR='AAA' "));
        try {
            fooService.insertThenRollback();
        } catch (RollbackException e) {
            log.info("BBB {}", logCount("SELECT COUNT(*) FROM FOO WHERE BAR='BBB' "));
        }
        try {
            fooService.invokeInsertTheRollback();
        } catch (RollbackException e) {
            log.info("BBB {}", logCount("SELECT COUNT(*) FROM FOO WHERE BAR='BBB' "));
        }

    }

    private Long logCount(String sql){
        final Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count;
    }
}
