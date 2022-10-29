package com.wanan.webgoat.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//使用注解@Configuration，告诉Spring Boot这是一个配置类。
@ComponentScan("com.wanan.webgoat.server")
//使用@ComponentScan自动扫描组件
public class ParentConfig {
}
