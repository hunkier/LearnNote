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

#### 一些注意事项

* 没特殊情况下，不要在生产环境打开监控的 Servlet
* 没有连接泄漏的情况下，不要开启 removeAbandoned
* testXxx 的使用需要注意
* 务必配置合理的超时时间



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





## Hibernate

* 一款开源的对象关系映射 (Object / Relational Mapping) 框架
* 将开发者从 95% 的常见数据持久化工作中解放出来
* 屏蔽了底层数据库的各种细节

### Java Persisitence  API

#### JPA 为对象关系映射提供了一种基于 POJO 的持久化模型

* 简化数据持久化代码的开发工作
* 为 Java 社区屏蔽了不同持久化 API 的差异

## Spring Data

#### 在保留底层存储特性的同时，提供相对一致的、基于 Spring 的编程模型

#### 主要模块

* Spring Data Commons
* Spring Data JDBC
* Spring Data JPS
* Spring Data Redis
* …...

```xml
<dependencyManagement>
	<dependencies>
  	<dependency>
    	<groupId>org.springframework.data</groupId>
      <artifactId>spring-data-releasetrain</artifactId>
      <version>Lovelace-SR4</version>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### 常用 JPA 注解

#### 实体

* @Entity、@MappedSupperclass
* @Table(name)

#### 主键

* @Id
* @GeneratedValue(strategy，generator)
* @SequenceGenerator(name，sequenceName)

```java
@Entity(name="Product")
public static class Product {
  @Id
  @GeneratedValue(
  	strategy=GenerationType.SEQUENCE,
    generator="sequence-generator"
  )
  @SequenceGenerator(
  	name="sequence-generator",
    sequenceName="product_sequence"
  )
  private Long id;
  
  @Column(name="product_name")
  private String name;
}
```

#### 映射

* @Column(name, nullable, length, insertable, updatable)
* @JoinTable(name)、@JoinColum(name)

#### 关系

* @OneToOne、@OneToMany、@ManyToOne、@ManyToMany
* @OrderBy

## Repository Bean 是如何创建的

#### JpaRepositoriesRegistrar

* 激活了 @EnableJapRepositories
* 返回了 JpaRepositoryConfigExtension

```java
package org.springframework.data.jpa.repository.config;

/**
 * {@link ImportBeanDefinitionRegistrar} to enable {@link EnableJpaRepositories} annotation.
 *
 * @author Oliver Gierke
 */
class JpaRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

```



#### RepositoryBeanDefinitionRegistrarSupport.registerBeanDefinitions

* 注册 Repository Bean (类型是 JpaRepositoryFactoryBean)

```java

package org.springframework.data.repository.config;
/**
 * Base class to implement {@link ImportBeanDefinitionRegistrar}s to enable repository
 *
 * @author Oliver Gierke
 */
public abstract class RepositoryBeanDefinitionRegistrarSupport
		implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

  
	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)
	 */
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

		Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");
		Assert.notNull(resourceLoader, "ResourceLoader must not be null!");

		// Guard against calls for sub-classes
		if (annotationMetadata.getAnnotationAttributes(getAnnotation().getName()) == null) {
			return;
		}

		AnnotationRepositoryConfigurationSource configurationSource = new AnnotationRepositoryConfigurationSource(
				annotationMetadata, getAnnotation(), resourceLoader, environment, registry);

		RepositoryConfigurationExtension extension = getExtension();
		RepositoryConfigurationUtils.exposeRegistration(extension, registry, configurationSource);

		RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(configurationSource, resourceLoader,
				environment);

		delegate.registerRepositoriesIn(registry, extension);
	}

}
```



#### RepositoryConfigurationExtensionSupport.getRepositoryConfigurations

* 取得 Repository 配置

```java
package org.springframework.data.repository.config;
/**
 * Base implementation of {@link RepositoryConfigurationExtension} to ease the implementation of the interface. Will
 * default the default named query location based on a module prefix provided by implementors (see
 * {@link #getModulePrefix()}). Stubs out the post-processing methods as they might not be needed by default.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 * @author Christoph Strobl
 */
public abstract class RepositoryConfigurationExtensionSupport implements RepositoryConfigurationExtension {

  
	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.config.RepositoryConfigurationExtension#getRepositoryConfigurations(org.springframework.data.repository.config.RepositoryConfigurationSource, org.springframework.core.io.ResourceLoader, boolean)
	 */
	public <T extends RepositoryConfigurationSource> Collection<RepositoryConfiguration<T>> getRepositoryConfigurations(
			T configSource, ResourceLoader loader, boolean strictMatchesOnly) {

		Assert.notNull(configSource, "ConfigSource must not be null!");
		Assert.notNull(loader, "Loader must not be null!");

		Set<RepositoryConfiguration<T>> result = new HashSet<>();

		for (BeanDefinition candidate : configSource.getCandidates(loader)) {

			RepositoryConfiguration<T> configuration = getRepositoryConfiguration(candidate, configSource);
			Class<?> repositoryInterface = loadRepositoryInterface(configuration,
					getConfigurationInspectionClassLoader(loader));

			if (repositoryInterface == null) {
				result.add(configuration);
				continue;
			}

			RepositoryMetadata metadata = AbstractRepositoryMetadata.getMetadata(repositoryInterface);

			boolean qualifiedForImplementation = !strictMatchesOnly || configSource.usesExplicitFilters()
					|| isStrictRepositoryCandidate(metadata);

			if (qualifiedForImplementation && useRepositoryConfiguration(metadata)) {
				result.add(configuration);
			}
		}

		return result;
	}
}
```



#### JpaRepositoryFactory.getTargetRepository

* 创建了 Repository

```java
package org.springframework.data.jpa.repository.support;

/**
 * JPA specific generic repository factory.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 * @author Christoph Strobl
 * @author Jens Schauder
 * @author Stefan Fussenegger
 */
public class JpaRepositoryFactory extends RepositoryFactorySupport {

  
	/**
	 * Callback to create a {@link JpaRepository} instance with the given {@link EntityManager}
	 *
	 * @param information will never be {@literal null}.
	 * @param entityManager will never be {@literal null}.
	 * @return
	 */
	protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
			EntityManager entityManager) {

		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
		Object repository = getTargetRepositoryViaReflection(information, entityInformation, entityManager);

		Assert.isInstanceOf(JpaRepositoryImplementation.class, repository);

		return (JpaRepositoryImplementation<?, ?>) repository;
	}
}
```



### 接口中的方法是如何被解释的

#### RepositoryFactorySupport.getRepository 添加了 Advice

* DefaultMethodInvokingMethodInterceptor
* QueryExecutorMethodInterceptor

```java
package org.springframework.data.repository.core.support;
/**
 * Factory bean to create instances of a given repository interface. Creates a proxy implementing the configured
 * repository interface and apply an advice handing the control to the {@code QueryExecuterMethodInterceptor}. Query
 * detection strategy can be configured by setting {@link QueryLookupStrategy.Key}.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 * @author Christoph Strobl
 * @author Jens Schauder
 */
@Slf4j
public abstract class RepositoryFactorySupport implements BeanClassLoaderAware, BeanFactoryAware {
  /**
	 * Returns a repository instance for the given interface backed by an instance providing implementation logic for
	 * custom logic.
	 *
	 * @param repositoryInterface must not be {@literal null}.
	 * @param fragments must not be {@literal null}.
	 * @return
	 * @since 2.0
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Initializing repository instance for {}…", repositoryInterface.getName());
		}

		Assert.notNull(repositoryInterface, "Repository interface must not be null!");
		Assert.notNull(fragments, "RepositoryFragments must not be null!");

		RepositoryMetadata metadata = getRepositoryMetadata(repositoryInterface);
		RepositoryComposition composition = getRepositoryComposition(metadata, fragments);
		RepositoryInformation information = getRepositoryInformation(metadata, composition);

		validate(information, composition);

		Object target = getTargetRepository(information);

		// Create proxy
		ProxyFactory result = new ProxyFactory();
		result.setTarget(target);
		result.setInterfaces(repositoryInterface, Repository.class, TransactionalProxy.class);

		if (MethodInvocationValidator.supports(repositoryInterface)) {
			result.addAdvice(new MethodInvocationValidator());
		}

		result.addAdvice(SurroundingTransactionDetectorMethodInterceptor.INSTANCE);
		result.addAdvisor(ExposeInvocationInterceptor.ADVISOR);

		postProcessors.forEach(processor -> processor.postProcess(result, information));

		result.addAdvice(new DefaultMethodInvokingMethodInterceptor());

		ProjectionFactory projectionFactory = getProjectionFactory(classLoader, beanFactory);
		result.addAdvice(new QueryExecutorMethodInterceptor(information, projectionFactory));

		composition = composition.append(RepositoryFragment.implemented(target));
		result.addAdvice(new ImplementationMethodExecutionInterceptor(composition));

		T repository = (T) result.getProxy(classLoader);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Finished creation of repository instance for {}.", repositoryInterface.getName());
		}

		return repository;
	}
}

/**
	 * This {@code MethodInterceptor} intercepts calls to methods of the custom implementation and delegates the to it if
	 * configured. Furthermore it resolves method calls to finders and triggers execution of them. You can rely on having
	 * a custom repository implementation instance set if this returns true.
	 *
	 * @author Oliver Gierke
	 */
	public class QueryExecutorMethodInterceptor implements MethodInterceptor {
    /*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
		 */
		@Override
		@Nullable
		public Object invoke(@SuppressWarnings("null") MethodInvocation invocation) throws Throwable {

			Method method = invocation.getMethod();

			return QueryExecutionConverters //
					.getExecutionAdapter(method.getReturnType()) //
					.apply(() -> resultHandler.postProcessInvocationResult(doInvoke(invocation), method));
		}

		@Nullable
		private Object doInvoke(MethodInvocation invocation) throws Throwable {

			Method method = invocation.getMethod();
			Object[] arguments = invocation.getArguments();

			if (hasQueryFor(method)) {
				return queries.get(method).execute(arguments);
			}

			return invocation.proceed();
		}
  }
```



#### AbstractJpaQuery.excute 执行具体的查询

#### 语法解析在 Part 中

```java
package org.springframework.data.repository.query.parser;
/**
 * A single part of a method name that has to be transformed into a query part. The actual transformation is defined by
 * a {@link Type} that is determined from inspecting the given part. The query part can then be looked up via
 * {@link #getProperty()}.
 *
 * @author Oliver Gierke
 * @author Martin Baumgartner
 */
@EqualsAndHashCode
public class Part {

	private static final Pattern IGNORE_CASE = Pattern.compile("Ignor(ing|e)Case");

	private final PropertyPath propertyPath;
	private final Part.Type type;

	private IgnoreCaseType ignoreCase = IgnoreCaseType.NEVER;

```



## MyBatis

### 认识 MyBatis

#### MyBatis (https://github.com/mybatis/mybatis-3)

* 一款优秀的持久化层框架
* 支持定制化 SQL、存储过程和高级映射

#### 在 Spring 中使用 MyBatis

* MyBatis Spring Adapter (https://github.com/mybatis/spring)
* MyBatis Spring-Boot-Starter (https://github.com/mybatis/spring-boot-starter)

#### 简单配置

* mybatis.mapper-locations = classpath*:mapper/**/\*/\*.xml
* Mybatis.type-aliases-package = 类型别名的包名
* mybatis.type-handlers-package = TypeHander 扫描包名
* mybatis.configuration.map-underscore-to-camel-case = true

### Mapper 的定义与扫描

* @MapperScan 配置扫描位置
* @Mapper 定义接口
* 映射的定义 —  XML 与注解

### 让 MyBatis 更好用的那些工具

#### MyBatis Generator

### 认识 MyBatis Generator

#### Mybatis Generator (http://www.mybatis.org/generator/)

* MyBatis 代码生成器
* 根据数据库表生成相关代码
* POJO
* Mapper 接口
* SQL Map XML

### 运行 MyBatisGenerator

#### 命令行

* java -jar mybatis-generator-core-x.x.x.jar -configfile generatorConfig.xml

#### Maven Plugin (mybatis-generator-maven-plugin)

* mvn mybatis-generator:generate
* ${basedir}/src/main/resources/generatorConfig.xml

#### Eclipse Plugin

#### Java 程序

#### Ant Task



### 配置 MyBatis Generator

#### generatorConfiguration

#### context

* jdbcConection
* javaModelGenerator
* sqlMapGenerator
* javaClientGenerator (ANNOTATEDMAPPER / XMLAPPER / MIXEDMAPPER )
* table



## Project Lombok

### Project Lombok 能够自动嵌入 IDE 和构建工具，提升开发效率

#### 常用功能

* @Getter / @Setter
* @ToString
* @NoArgsConstructor / @RequiredArgsConstructor / @AllArgsConstructor
* @Data
* @Builder
* Slf4j / @CommonsLog / @Log4j2

# 线上咖啡馆实战项目

## SpringBucks

### 通过一个完整的例子演示 Spring 全家桶各主要成员的用法

### 项目中的实体对象

#### 实体

* 咖啡、订单、顾客、服务员、咖啡师

#### 订单状态

* 初始、已支付、制作中、制作完毕、已取货