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
                .locations("db/migration")
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
