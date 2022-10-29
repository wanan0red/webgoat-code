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
