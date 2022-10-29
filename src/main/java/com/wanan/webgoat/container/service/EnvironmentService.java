package com.wanan.webgoat.container.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/environment")
//@RestController 是@controller和@ResponseBody 的结合
//@Controller 将当前修饰的类注入到springboot ioc容器,使得从该类所在的项目跑起来的过程中,这个类就被实例化
//@ResponseBody 它的作用简短的说就是指该类中的api即可返回的数据 不管对应方法返回map或者其他的object,它会以json字符串的形式返回给客户端
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
