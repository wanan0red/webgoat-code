package com.wanan.webgoat.server;


import com.wanan.webgoat.container.WebGoat;
import com.wanan.webgoat.webwolf.WebWolf;
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
    //    定义webgoat的端口字符
    public static final String WEBWOLF_PORT = "webwolf.port";
//    定义webwolf端口字符
    private static final int MAX_PORT = 9999;
    //    定义最大端口

    public static void main(String[] args) {
        setEnvironmentVariableForPort(WEBGOAT_PORT,"8080");
        setEnvironmentVariableForPort(WEBWOLF_PORT, "9090");
        new SpringApplicationBuilder().parent(ParentConfig.class)//使用Parentconfig配置类
                .web(WebApplicationType.NONE).bannerMode(Banner.Mode.OFF)//不启动内嵌的WebServer，不是运行web application   banner也就是启动的那个图
                .child(WebGoat.class)//定义子配置项
                .web(WebApplicationType.SERVLET)//启动内嵌的reactive web server，这个application是一个reactive web application
                .sibling(WebWolf.class).bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.SERVLET)
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
