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
    public HttpTraceRepository traceRepository(){
        return new WebWolfTraceRepository();
    }
}
