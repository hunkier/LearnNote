# Spring Boot 的配置演示

* ## 引入对应的数据库驱动 - - H2

* ## 引入 JDBC 依赖 - - spring-boot-starter-jdbc

* ## 获取 DataSource Bean，打印信息

* ## 也可以通过 /acturator/beans 查看 Bean



# 直接配置所需的 Bean

## 数据源相关

* DataSource  （根据选择的连接池实现决定）

## 事务相关 （可选）

* PlatformTransactoinManager (DataSourceTransactionManager)
* TransactionTemplate

## 操作相关 （可选）

* JdbcTemplate



# Spring Boot 做了哪些配置

## DataSourceAutoConfiguration

* 配置 DataSource

## DataSourceTransactionManagerAutoConfiguration

* 配置 DataSourceTransactionManager

# JdbcTemplateAutoConfiguratoin

* 配置 JdbcTemplate

#### 符合条件时才进行配置



## 数据源相关配置属性

### 通用

* spring.datasource.url=jdbc:mysql://localhost/test
* spring.datasource.username=dbuser
* spring.datasource.password=dbpass
* spring.datasource.driver-class-name=com.mysql.jdbc.Driver (可选)

### 初始化内嵌数据库

* spring.datasource.initializtion-mode=embedded|always|never
* spring.datasource.schema 与 spring.datasource.data 确定初始化 SQL 文件
* spring.datasource.platform=hsqldb | h2 | oracle | mysql | postgresql （与前者对应）



# Spring Boot 中的多数据源配置

## 手工配置两组 DataSource 及相关内容

### 与 Spring Boot 协同工作 （二选一）

* 配置 @Primary 类型的 Bean

* 排除 Spring Boot 的自动配置

   	1. DataSourceAutoConfiguratoin
      	
      ```java
      @SpringBootApplication (exclude = {
              DataSourceAutoConfiguration.class,
              DataSourceTransactionManagerAutoConfiguration.class,
              JdbcTemplateAutoConfiguration.class
      })
      @Slf4j
      public class MultDataSourceDemoApplication{
          
      }
      ```
      
      
      
      ```java
       @Bean
          @ConfigurationProperties("foo.datasource")
          public DataSourceProperties fooDataSourceProperties(){
              return new DataSourceProperties();
          }
      
          @Bean
          public DataSource fooDataSource() {
              final DataSourceProperties dataSourceProperties = fooDataSourceProperties();
              log.info("foo datasource: {}", dataSourceProperties.getUrl());
              return dataSourceProperties.initializeDataSourceBuilder().build();
          }
      
          @Bean
          @Resource
          public PlatformTransactionManager fooTxManager(DataSource fooDataSource){
              return new DataSourceTransactionManager(fooDataSource);
          }
      ```
      
      ```java
        @Bean
          @ConfigurationProperties("bar.datasource")
          public DataSourceProperties barDataSourceProperties(){
              return new DataSourceProperties();
          }
      
          @Bean
          public DataSource barDataSource() {
              final DataSourceProperties dataSourceProperties = barDataSourceProperties();
              log.info("bar datasource: {}", dataSourceProperties.getUrl());
              return dataSourceProperties.initializeDataSourceBuilder().build();
          }
      
          @Bean
          @Resource
          public PlatformTransactionManager barTxManager(DataSource barDataSource){
              return new DataSourceTransactionManager(barDataSource);
          }
      ```
      
      
      
      2. DataSourceTransactionManagerAutoConfiguration
         	3. JdbcTemplateAutoConfiguration

  

