package cn.hunkier.spring.data.datasourcedemo;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

@Configurable
@EnableTransactionManagement
@Component
public class DataSourceDemo {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) throws  Exception {
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
        showBeans(applicationContext);
        dataSourceDemo(applicationContext);
    }


    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws Exception{
        final Properties properties = new Properties();
        properties.setProperty("driverClassName","org.h2.Driver");
        properties.setProperty("url","jdbc:h2:mem:testdb");
        properties.setProperty("username","sa");
        return BasicDataSourceFactory.createDataSource(properties);
    }


    @Bean
    public PlatformTransactionManager transactionManager() throws  Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    private static void showBeans(ApplicationContext applicationContext){
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }

    private static void dataSourceDemo (ApplicationContext applicationContext) throws SQLException{
//        final DataSourceDemo demo = applicationContext.getBean("dataSourceDemo", DataSourceDemo.class);
        final DataSourceDemo demo = applicationContext.getBean(DataSourceDemo.class);
        demo.showDataSource(demo);
    }

    private static void showDataSource(DataSourceDemo demo){
        System.out.println(demo.dataSource.toString());
    }


}
