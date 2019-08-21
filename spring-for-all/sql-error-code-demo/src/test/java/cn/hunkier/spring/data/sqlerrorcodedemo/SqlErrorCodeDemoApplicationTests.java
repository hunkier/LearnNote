package cn.hunkier.spring.data.sqlerrorcodedemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlErrorCodeDemoApplicationTests {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test(expected = CustomDuplicateKeyException.class)
	public void testThrowingCustomException(){
		final String sql = "INSERT INTO FOO (ID, BAR) VALUES (1, 'A')";
		jdbcTemplate.execute(sql);
		jdbcTemplate.execute(sql);
	}

}
