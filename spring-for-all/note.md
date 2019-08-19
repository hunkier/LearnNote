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

  

# 常用的数据源连接池

## HikariCP 为什么快

### 1. 字节码级别优化 (很多方法通过 JavaAssist 生成)

### 2. 大量小改进

* 用 FastStatementList  代替 ArrayList
* 无锁集合 ConcurrentBag
* 代理类的优化 (比如，用 invokestatic 代替了 invokevirtual)

## 在 Spring Boot 中的配置

### Spring Boot 2.x

* 默认使用 HikariCP
* 配置 spring.datasource.hikari.* 配置

### Spring Boot 1.x

* 默认使用 Tomcat 连接池，需要移除 tomcat-jdbc 依赖
* spring.datasource.type=com.zaxxer.hikari.HikariDataSource

```java
	/**
	 * Hikari DataSource configuration.
	 */
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari {

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.hikari")
		public HikariDataSource dataSource(DataSourceProperties properties) {
			HikariDataSource dataSource = createDataSource(properties,
					HikariDataSource.class);
			if (StringUtils.hasText(properties.getName())) {
				dataSource.setPoolName(properties.getName());
			}
			return dataSource;
		}

	}

```

## 常用 HikariCP 配置参数

常用配置

* spring.datasource.hikari.maximumPoolSize=10
* spring.datasource.hikari.minimumldle=10
* spring.datasource.hikari.idleTimeout=600000
* spring.datasource.hikari.connectionTimeout=30000
* spring.datasource.hikari.maxLifetime=1800000

### 其他配置详见 HikariCP 官网

* https://github.com/brettwooldridge/HikariCP

## Alibaba Druid 官方介绍

"Druid 连接池是阿里巴巴开源的数据库连接池项目。Druid 连接池为监控而生，内置强大的监控功能，监控特性不影响性能。功能强大，能防 SQL 注入，内置 Logging 能诊断 Hack 应用行为。"

### 经过阿里巴巴各大系统的考验，值得信赖

### 使用功能

* 详细的监控 (真的是全面)
* ExceptionSorter，针对主流数据库的返回码都有支持
* SQL 防注入
* 内置加密配置
* 众多扩展点，方便进行定制

## 数据源配置

### 直接配置 DruiDataSource

### 通过 druid-spring-boot-start

* Spring.datasource.druid.*

```properties
spring.output.ansi.enabled=ALWAYS

spring.datasource.url=jdbc:h2:mem:foo
spring.datasource.username=sa
spring.datasource.password=/zew8632fds698h32fs932VDFwenfFe836GE==

spring.datasource.druid.initial-size=5
spring.datasource.druid.max-active=5
spring.datasource.druid.min-idle=5
spring.datasource.druid.filters=connn,config,stat,slf4j

spring.datasource.druid.connection-properties=config.decrypt=true;config.decrypt.key=${public-key}
spring.datasource.druid.filter.config.enabled=true

spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.test-on-return=true
spring.datasource.druid.test-while-idle=true
```



## 数据源配置

### Filter 配置

* spring.datasource.druid.filters=stat,config,wall,log4j

### 密码加密

* spring.datasource.password=<加密密码>
* spring.datasource.druid.filter.config.enabled=true
* spring.datasource.druid.connection-properties=config.decrypt=true;config.decrypt.key=<public-key>

### SQL 防注入

* spring.datasource.druid.filter.wall.enabled=true
* spring.datasource.druid.filter.wall.db-type=h2
* spring.datasource.druid.filter.wall.config.delete-allow=false
* spring.datasource.druid.filter.wall.config.drop-table-allow=false

### Druid Filter

* 用于定制连接池操作的各种环节
* 可以继承 FilterEventAdapter  以便方便地实现 Filter
* 修改 META-INF/druid-filter.properties  增加 Filter 配置

```properties
druid.filters.conn=cn.hunkier.spring.data.druiddemo.ConnectionLogFilter
```



```java
@Slf4j
public class ConnectionLogFilter  extends FilterEventAdapter {

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        log.info("BEFORE CONNECTOIN !");
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        log.info("AFTER CONNECTOIN !");
    }
}

```





## Spring 的 JDBC 操作类

### spring-jdbc

* core， JdbcTemplate 等相关核心接口和类
* datasource，数据源相关的辅助类
* object，将基本的 JDBC 操作封装成对象
* support，错误码等其他辅助工具