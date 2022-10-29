package com.wanan.webgoat.webwolf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("webwolf-login");
//        添加视图解析器从/login转到webwolf-login中
        registry.addViewController("/home").setViewName("home");
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
