## Java History & OOPs
* OOPs Re-usability : IS A,HAS A
* IS A : extends : Inheritance
* HAS A : Used as objects : Dependency Management

* Java/Core Java : Basic one
* J2SE : Object, Class, Inheritance, Exception Handling, Collection, etc 
* J2SE helps in creating Command Line Application using psvm

## Enterprise Solutions for Java
* Server : Java Code which sends HTTP Response when gets HTTP Request
* Client : Accesses Server over internet(Protocols : HTTP Request)
* Servlet : Sun Microsystem created. So, this listens to HTTP Request & sends HTTP Response

```java
class MyServlet extends Servlet{
    
}
```

* Incoming Request : byte[] -> Network:8080 -> Server/Servlet Container(TOMCAT) ->Convert to Java Object -> MyServlet -> .java servlet
* Output Response : MyServlet -> Java Object Reponse -> Servlet Container -> byte[]/binary -> 8080 -> Network
* Servlet Container Examples : Tomcat/Jetty/Undertow/GlassFish/etc.

## Java Ecosystem : Enterprise Solutions
| Application Type | Solution by Java | Current Status / Alternatives in Market |
|---|---|---|
|Command Line Application|Old IDEs|Only for learning purpose|
|Browser Based Application|Applet|Discontinued by Java|
|Server Based Application|Servlet|Not used|
|HTML Based Application|JSP|Angular/React.js|
|Transaction/Security Based Application|EJB|Spring Framework(Enterprise Based Application)| 

## Why Spring
* Easy testing & maintenance
* EJB maintains HAS-A relationship inside Container. EJB solved this using Inversion of Control(Refer below example)
```java
class Shape{
    
}

class Circle extends Shape{
    //Color c = new Color();
    //Size s = new Size();
    
    //Post EJB Container : Object created by EJB(Inversion of Control) : So, Object Lookup in EJB Container(JNDI)
    Color c;
    Size s; 
    
    //Object Lookup by JNDI
    //Color c = JNDI.lookup("com.anupama.sinha.color");
}
```

Container -> EJB Container(Box) : new Color(), new Size()
* EJB = Inversion of Control + Dependency Lookup
* Spring solved dependency lookup problem using Dependency Injection(Refer below example)

```java
import org.springframework.beans.factory.annotation.Autowired;

class Circle extends Shape {
    @Autowired
    Cicle c;
}
```
* Hence, Spring = Inversion of Control + Dependency Injection(Component Scanning for Circle Object)

## Spring
* Object Creation number of instances can be configured(@Scope)
* EJB/J2EE/Testing Complexity reduced 
* Class created as @Bean & picked by Container
* Non-invasive

## Why Spring Boot
* Spring had issues with Dependency & Configuration

## Ideas behind Spring Boot
* Convention over Configuration
* Transitive Dependency
* Bill of materials(Parent POM & Dependency Management)

## Spring Boot Web
* MVC : HTTP Request -> Servlet -> HTTP Response -> HTML
* REST Application : HTTP Request -> Servlet -> HTTP Response -> JSON(/XML)

## Spring Boot In Depth
* SpringApplication.run : Creates & initializes context(Current instance of application)
* @SpringBootApplication : Interface

## Spring Annotation creation
```java
@Target(ElementType.METHOD) // Used only for field/method/type(class level) in main class
public @interface MyContext {

    @AliasFor("name") //To avoid writing "name" keyword in main class : @MyContext("Anupama")
    String name() default "";

    @AliasFor("age")
    int age() default 25;
}

@SpringBootApplication
public class Application {

    @MyContext(name="Anupama",age=20)
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

## Spring Boot Controller Annotation
* Below is RequestMapping annotation interface where path & value are same. While, method denotes the HTTP method type
```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {
    String name() default "";

    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};

    RequestMethod[] method() default {};
}


public enum RequestMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private RequestMethod() {
    }
}
```

## Controller Annotations
* When @Controller is used, and a String is returned. Spring searches for string.jsp page and returns it
* So, as MVC Pattern below can be done
```xml
<dependency>
	<groupId>org.apache.tomcat.embed</groupId>
	<artifactId>tomcat-embed-jasper</artifactId>
</dependency>
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
</dependency>
```

```properties
spring.mvc.view.prefix=/WEB-INF/pages/
spring.mvc.view.suffix=.jsp
```

```java
@Controller
public class MyController {

    Logger logger = LoggerFactory.getLogger("MyController.class");

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String test(ModelMap modelMap){
        logger.info("Am invoking test(), Anupama. Check if u got response...");
        modelMap.addAttribute("time",System.currentTimeMillis());
        //This search. Adds prefix(/WEB-INF/pages/) & Adds suffix(.jsp)
        return "anupama";
    }
}
```

* Page created in main/webapp/WEB-INF/pages/anupama.jsp as below
```html
<html>
    <body>
        <h1>Hi Anupama from anupama.jsp</h1>
        <h1>Current time : ${time}</h1>
    </body>
</html>
```

* @RequestMapping can be at class level for common path & at method level also
* Dynamic content can be passed using JSP instead of HTML
* Now, using @RestController, gives the exact String being returned in response. And the JSP isn't used. If HashMap/Custom Object are returned, then RestController converts to application/json
* Object is converted to JSON by Jackson library(SpringBoot is opinionated)  
* Host : www.anupama.sinha.com, Port : 8080, Path Param : /greet, Path Variable : Anu
> Endpoint : www.anupama.sinha.com/greet/Anu  
* Host : www.anupama.sinha.com, Port : 8080, Path Param : /greet, Query Param : name=Anupama
> Endpoint : www.anupama.sinha.com/greet?name=Anupama

* MethodArgumentException received when wrong datatype sent for Path Variable

## Service Annotations
* @Service is used to mark the class as Service layer of Application
* @PostConstruct can be used to initialize local data structure(Collection) 

## Caching
* @EnableCaching in Main Application class
* @Cacheable for the method we need to cache which must be public method

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
    @AliasFor("cacheNames")
    String[] value() default {};

    @AliasFor("value")
    String[] cacheNames() default {};
}    
```

* AOP is used(Aspect Oriented Programming)
> Written once and injected at any place
> Method is public
> Method called from outside the class & not from same class as Proxy refrence(CGLIB Spring being called) is used in object

* AOP -> Injects code -> Proxy Object(Non invasive Spring as uses Annotation instead of extends/implements)
* CacheAspectSupport AOP for Cache
```java
@Nullable
protected Object execute(CacheOperationInvoker invoker, Object target, Method method, Object[] args) {
        if (this.initialized) {
        Class<?> targetClass = this.getTargetClass(target);
        CacheOperationSource cacheOperationSource = this.getCacheOperationSource();
        if (cacheOperationSource != null) {
        Collection<CacheOperation> operations = cacheOperationSource.getCacheOperations(method, targetClass);
        if (!CollectionUtils.isEmpty(operations)) {
        return this.execute(invoker, method, new CacheAspectSupport.CacheOperationContexts(operations, method, args, target, targetClass));
        }
        }
        }
}        
```

Main Class
```java
@SpringBootApplication
@EnableCaching
public class Application {

	@MyContext(name="Anupama",age=20)
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
```

Controller
```java
class MyRestController {
    @RequestMapping(path = "/testemp/{id}",method = RequestMethod.GET)
    public String testObjectId(@PathVariable("id") Integer id){
        //Below not used. Instead Cache method called directly
        //return myService.getName(id);
        String name = myService.getNameinDB(id);
        return name;
    }
}
```

Service
```java
class MyService{
    @Cacheable(cacheNames = "emp-cache")
    //private will not work for cache
//    private String getNameinDB(Integer id){
    public String getNameinDB(Integer id){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return empMap.get(id);
    }
}
```

## Annotation Types
* @Bean : For external libraries beyond Spring
* @Service,@Repository,@Component,@RestController

* StringUtils Usecase : A better library built on top of Spring with good method features

Method 1 : Creating Object Instance
```java
class MyRestController{
    @RequestMapping(path = "/testemp/{id}",method = RequestMethod.GET)
    public String testObjectId(@PathVariable("id") Integer id){
        //Below not used. Instead Cache method called directly
        //return myService.getName(id);
        String name = myService.getNameinDB(id);

        StringUtils stringUtils = new StringUtils();
        name = stringUtils.upperCase(name);

        return name;
    }
}
```

Method 2 : Autowiring in Class

```java
class MyRestController{
    @Autowired
    private StringUtils stringUtils;
    
    @RequestMapping(path = "/testemp/{id}",method = RequestMethod.GET)
    public String testObjectId(@PathVariable("id") Integer id){
        //Below not used. Instead Cache method called directly
        //return myService.getName(id);
        String name = myService.getNameinDB(id);

        name = stringUtils.upperCase(name);
        return name;
    }
}
```

* Error Description : Field stringUtils in com.anupama.sinha.MyRestController required a bean of type 'org.apache.commons.lang3.StringUtils' that could not be found.
* So, needs to be defined as @Bean in @Configuration. Bean loaded by Container

```java
@Configuration
public class MyConfiguation {
    @Bean
    StringUtils stringUtils(){
        return new StringUtils();
    }
}
```

* Earlier, in Spring, every class(Service, Repository, Controller, etc) needed to be created as Bean separetely. But now, SpringBoot automatically creates for these Annotations

* @SpringBootApplication : @Configuration(@Beans created here) + @ComponentScan + @EnableAutoConfiguration

## Interceptor
* Intercepts message before reaching Controller and do certain task
* LoggingInterceptor Class : Override methods of HandlerInterceptor interface: preHandle(), postHandle(), afterCompletion() 
* Register Interceptor in Context by defining in @Configuration implementing WebMvcConfigurer interface

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }
}
```

* MDC is thread specific
* After Completion : MDC.clear()

Console Log : Tomcat assigns Thread from ThreadPool(Max:10K can be spinned) to Caller individually. Reuses, the free thread pool
Threads here : nio-8081-exec-2,nio-8081-exec-3

```properties
2021-03-02 14:25:54.647  INFO 22866 --- [nio-8081-exec-2] com.anupama.sinha.LoggingInterceptor     : Incoming request
2021-03-02 14:25:56.704  INFO 22866 --- [nio-8081-exec-2] MyController.class                       : Controller called for id:1
2021-03-02 14:25:56.751  INFO 22866 --- [nio-8081-exec-2] com.anupama.sinha.LoggingInterceptor     : Request Completed; execTime=2103ms

2021-03-02 14:26:01.623  INFO 22866 --- [nio-8081-exec-3] com.anupama.sinha.LoggingInterceptor     : Incoming request
2021-03-02 14:26:01.629  INFO 22866 --- [nio-8081-exec-3] MyController.class                       : Controller called for id:1
2021-03-02 14:26:01.636  INFO 22866 --- [nio-8081-exec-3] com.anupama.sinha.LoggingInterceptor     : Request Completed; execTime=12ms
```

## Loggers
* Slf4j : Standard/Interface
* Frameworks : LogBack, Log4j
* Spring Boot being Opinionated has chosen LogBack as best framework and included in spring-boot-starter-logging in spring-boot-starter-web
* Method to use Log4j instead

```xml
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-logging</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-log4j2</artifactId>
		</dependency>
```

* log4j2.xml : Gives format of log. log folder keeps File Logger. Below format used

```properties
17:26:20.907 |  INFO | (nio-8082-exec-1) | [LoggingInterceptor] : Incoming request; | uri=/rest/testemp/1; endpoint=testObjectId; queryString=;
17:26:22.967 |  INFO | (nio-8082-exec-1) | [class] : Controller called for id:1; | uri=/rest/testemp/1; endpoint=testObjectId; queryString=;
17:26:23.001 |  INFO | (nio-8082-exec-1) | [LoggingInterceptor] : Request Completed; execTime=2098ms; | uri=/rest/testemp/1; endpoint=testObjectId; queryString=;
```

## Application's JAR usage in local code
* In application, for which JAR has to be created. Created JAR in .m2 folder
> mvn clean install

* Copy dependency in pom.xml of Common Library
```xml
<dependency>
	<groupId>com.anupama.sinha</groupId>
	<artifactId>common-lib</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>C://Users/anupama/.m2/common-lib.jar</systemPath>
</dependency>
```

* To help CommonLib Application's Bean loading in Context in Main Class
> @ComponentScan(basePackages = { "com.anupama.sinha.common", "com.anupama.sinha" })


## JDBC
* Java Library for Bridge between Class/Object & Database
* JDBC -> Physical Socket Connection for JVM & DB -> Query sent -> DB executes -> Result returned -> JDBC converts result to Java Object -> Returned to User -> Connection closed
* Costly socket connection & memory

## Connection Pool
* Connection creation saved to improve performance
* Connection Pool created by Apache Tomcat-JDBC
* Latest best open source connection pool is Hikari CP which is also provided by Spring Boot as being Opinionated

## Spring Data JPA(Java Persistence API)
* Only Class/Object needed
* Query created for @Entity
* JPA Specification Implementation : Hibernate,iBatis,MyBatis
* Data JPA : Hibernate + HikariCP(Since 1.6 onwards)

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
<groupId>mysql</groupId>
<artifactId>mysql-connector-java</artifactId>
</dependency>
```

Properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/EMP_DB?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

Entity
```java
@Entity
@Table(name="EMPLOYEE")
@Getter
@Setter
public class Employee {
    @Id
    private Integer id;

    @Column
    private String name;
}   
```

Repository
```java
public interface EmployeeRepo extends JpaRepository<Emp,Integer> {
    
}
```
## Spring Security

## Spring Reactive

## Spring Cloud

## References
* [Rod Johnson's Book on J2EE over EJB](https://www.google.co.in/books/edition/Expert_One_on_One_J2EE_Development_witho/oAE90y3_Df4C?hl=en&gbpv=0)