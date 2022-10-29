package com.wanan.webgoat.container.assignments;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
//存放在接口 类 枚举
@Retention(RetentionPolicy.RUNTIME)
//注解不仅被保存到class文件中,jvm加载class文件之后依然存在
public @interface AssignmentPath {
    String[] path() default {};
//    路径
    RequestMethod[] method() default {};
//    请求方法
    String value() default "";
//    请求值
}
