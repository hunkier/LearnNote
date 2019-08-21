package cn.hunkier.spring.data.druiddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void selectForUpdate(){
        jdbcTemplate.queryForObject("SELECT  ID from  FOO where id=1 for update ", Long.class);
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
