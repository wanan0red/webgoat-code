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
