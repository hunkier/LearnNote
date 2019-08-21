package cn.hunkier.spring.data.transactionpropagationdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FooService fooService;
/*

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }
*/

    @Override
//    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.REQUIRES_NEW)
    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.NESTED)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
//        throw  new RollbackException();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void invokeInsertTheRollback() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
        try {
            fooService.insertThenRollback();
        } catch (RollbackException e) {
            log.error("RollbackException",e);
        }
        throw  new RuntimeException();
    }
}
