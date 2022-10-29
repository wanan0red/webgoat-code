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
//    @scope注解是spring ioc容器中的一个作用域 session 是指每一次http请求都会产生一个bean请求,同时改bean仅对当前http session有效
    public UserSessionData userSessionData(){
        return new UserSessionData("test","data");
    }

    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}

}
