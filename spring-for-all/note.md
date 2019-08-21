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

### 慢 SQL 日志

#### 系统属性配置

* druid.stat.logSlowSql=true
* druid.stat.slowSqlMillis=3000

#### Spring Boot

* spring.datasource.druid.filter.stat.enabled=true
* spring.datasource.druid.filter.stat.log-slow-sql=true
* spring.datasource.druid.filter.stat.slow-sql-millis=3000





## Spring 的 JDBC 操作类

### spring-jdbc

* core， JdbcTemplate 等相关核心接口和类
* datasource，数据源相关的辅助类
* object，将基本的 JDBC 操作封装成对象
* support，错误码等其他辅助工具

## 常用的 Bean 注解

通过注解定义 Bean

* @Component
* @Repository
* @Service
* @Controller
* @RestController

## 简单的 JDBC 操作

### JdbcTemplate

* query
* queryForObject
* queryForList
* update
* execute

```
Talk is cheap，show me the code。
									-Linux Torvalds
```

## SQL 批处理

## JdbcTemplate

* batchUpdate
* BatchPreparedStatementSetter

### NameParameterJdbcTemplate

* batchUpdate
* SqlParameterSourceUtils.createBatch







# 了解 Spring 的抽象

## 事务抽象

## Spring 的事务抽象

### 一致的事务模型

* JDBC/Hibernate/myBatis
* DataSource/JTA

## 事务抽象的核心接口

### PlatformTransactionManager

* DataSourceTransactionManager
* HibernateTransactionManager
* JtaTransactionManager

### TransactionDefinition

* Propagation
* Isolation
* Timeout
* Read-only status

```java
void commit(TransactionStatus status) throws TransactionException;
void roolback(Transactionstatus status)throws TransactionException;
TransactionStatus getTransaction(@Nullable TransactionDefinition definition)throw TransactionException;
```



## 事务传播特性

| 传播性                    | 值   | 描述                                   |
| ------------------------- | ---- | -------------------------------------- |
| PROPAGETION_REQUIRED      | 0    | 当前有事务就用当前的，没有就用新的     |
| PROPAGETION_SUPPORTS      | 1    | 事务可有可无，不是必须的               |
| PROPAGETION_MANDTORY      | 2    | 当前一定要有事务，不然就抛异常         |
| PROPAGETION_REQUIRES_NEW  | 3    | 无论当前是否有事务，都起个新的事务     |
| PROPAGETION_NOT_SUPPORTED | 4    | 不支持事务，按非事务方式运行           |
| PROPAGETION_NEVER         | 5    | 不支持事务，如果有事务则抛出异常       |
| PROPAGETION_NESTED        | 6    | 当前有事务就在当前事务里面再起一个事务 |

### REQUIRES_NEW v.s. NESTED

#### REQUIRES_NEW, 始终启动一个新的事务

* 两个事务没有关联

#### NESTED ，在原事务内启动一个内嵌事务

* 两个事务有关联
* 外部事务回滚，内嵌事务也会回滚



## 事务隔离特性

| 隔离性                     | 值   | 脏读 | 不可重复读 | 幻读 |
| -------------------------- | ---- | ---- | ---------- | ---- |
| ISOLATION_READ_UNCOMMITTED | 1    | ✅    | ✅          | ✅    |
| ISOLATION_READ_COMMITTED   | 2    | ❌    | ✅          | ✅    |
| ISOLATION_REPEATABLE_READ  | 3    | ❌    | ❌          | ✅    |
| ISOLATION_SERIALIZABLE     | 4    | ❌    | ❌          | ❌    |

## 事务的本质

* ### Spring 的声明式事务本质上是通过 AOP 来增强了类的功能

* ### Spring 的 AOP 本质上就是为类做了一个代理

* 看似在调用自己的的类，实际用的是增强后的代理类

* ### 问题的解法

* 访问增强的代理类的方法，而非直接访问自身的方法

## 编程式事务

### TransactionTemplate

* TransactionCallback
* TransactionCallBackWithoutResult

### PlatformTRansactionManager

* 可以传入 TransactionDefinition 进行定义

```java

@SpringBootApplication
@Slf4j
public class ProgrammticTransactionDemoApplication implements CommandLineRunner {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProgrammticTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("COUNT BEFORE TRANSACTION: {}", getCount());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.execute("INSERT  INTO FOO (ID, BAR) VALUES (1, 'aaa')");
                log.info("COUNT IN TRANSACTION: {}", getCount());
                status.setRollbackOnly();
            }
        });
        log.info("COUNT AFTER TRANSACTION: {}", getCount());
    }

    private long getCount(){
        return (long) jdbcTemplate.queryForList("SELECT  COUNT(*) AS CNT FROM FOO" ).get(0).get("CNT");
    }
}
```

### 基于注解的配置方式

#### 开启事务注解的方式

* @EnableTransactionManagement
* <tx:annotation-driven />

#### 一些配置

* proxyTargetClass
* mode
* order

## @Transactional

* transactionManager
* propagation
* isolation
* timeout
* readOnly
* 怎么判断回滚

## Spring 的 JDBC 异常抽象

### Spring 会将数据操作的异常转换为 DataAccessException，无论使用何种数据访问方式，都能使用一样的异常



### Spring 是怎么认识那些错误码的

#### 通过 SQLErrorCodeSQLExceptionTranslator 解析错误码

#### ErrorCode 定义

* org/springframework/jdbc/support/sql-error-codes.xml
* Classpath 下的 sql-error-codes.xml

### 定制错误码解析逻辑

```xml

	<bean id="H2" class="org.springframework.jdbc.support.SQLErrorCodes">
    <property name="badSqlGrammarCodes">
        <value>42000,42001,42101,42102,42111,42112,42121,42122,42132</value>
    </property>
    <property name="duplicateKeyCodes">
        <value>23001,23505</value>
    </property>
    <property name="dataIntegrityViolationCodes">
        <value>22001,22003,22012,22018,22025,23000,23002,23003,23502,23503,23506,23507,23513</value>
    </property>
    <property name="dataAccessResourceFailureCodes">
        <value>90046,90100,90117,90121,90126</value>
    </property>
    <property name="cannotAcquireLockCodes">
        <value>50200</value>
    </property>
    <property name="customTranslations">
        <bean class="org.springframework.jdbc.support.CustomSQLErrorCodesTranslation">
            <property name="errorCodes" value="23001,23505"/>
            <property name="exceptionClass"
                      value="cn.hunkier.spring.data.sqlerrorcodedemo.CustomDuplicateKeyException"/>
        </bean>
    </property>
</bean>
```

```java
public class CustomDuplicateKeyException extends DuplicateKeyException {
    public CustomDuplicateKeyException(String msg) {
        super(msg);
    }

    public CustomDuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

```

```java

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
```



### 一些常用的注解

#### Java Config 相关注解

* @Configuration
* @ImportResource
* @ComponentScan
* @Bean
* @ConfigurationProperties

#### 定义相关注解

* @Component / @Respository / @Service
* @Controller / @ RestController
* @RequestMapping

#### 注入相关注解

* @Autowired / @Qualifier / @Resource
* @Value

#### Actuator 提供的一些好用的 EndPoint



| URL               | 作用                  |
| ----------------- | --------------------- |
| /actuator/health  | 健康检查              |
| /actuator/mapping | 查看 Web 的 URL 映射  |
| /actuator/beans   | 查看容器中的所有 Bean |
| /actuator/env     | 查看环境信息          |

### 如何解禁 Endpoint

#### 默认

* /actuator/health  和 /actuator/info 可 Web 访问

#### 解禁所有 Endpoint

* 在 application.properties / application.yaml 中添加

```properties
management.endpointis.web.exposure.include=*
```

**生产环境需谨慎**