package cn.hunkier.spring.data.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

//@SpringBootApplication
@Slf4j
public class DatasourceDemoApplicationBackup implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DatasourceDemoApplicationBackup.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConection();
    }

    private void showConection() throws  Exception {
        log.info(dataSource.toString());
        final Connection connection = dataSource.getConnection();
        log.info(dataSource.toString());
        log.info(connection.toString());
        connection.close();
    }
}
