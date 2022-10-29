package com.wanan.webgoat.lessons.hijacksession.cas;



import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoublePredicate;
import java.util.function.Supplier;

@ApplicationScope
//没太读懂 这里是指这个类与@Bean类注解的相似 但是会将作用域放到SCOPE_APPLICATION里面?
//我理解就是用springboot自动去加载管理只不过作用域不同
@Component
public class HijackSessionAuthenticationProvider implements AuthenticationProvider<Authentication> {
//    这里也很简单就可以理解 首先实现的是AuthenticationProvider 而这里的Authentication 就相当于实参去传递给了T
    private Queue<String> sessions = new LinkedList<>();
//    用于在处理之前保存元素的集合
    private static long id = new Random().nextLong() & Long.MAX_VALUE;
//    这里其实不太能理解 前面是生成一个long类型的随机数 这个 & 是什么意思 表表示最大是 Long.MAX_VALUE?
    protected static final int MAX_SESSIONS = 50 ;
//    这里其实是作为session 最大值的一个定义


    private static final DoublePredicate PROBABILITY_DOUBLE_PREDICATE = pr -> pr < 0.75;
//
    private static final Supplier<String> GENERATE_SESSION_ID = () -> ++id + "-" + Instant.now().toEpochMilli();
//    这里简单理解就是 id先自加一 再拼接起当前时间戳
    public static final Supplier<Authentication> AUTHENTICATION_SUPPLIER = () -> Authentication
            .builder()
            .id(GENERATE_SESSION_ID.get())
//        get()获取结果的提供者 就是获取Authentication创建的时间
            .build();

    @Override
    public Authentication authenticate(Authentication authentication) {
//        进行认证
        if (authentication == null){
//            如果没有authentication 则返回一个
            return AUTHENTICATION_SUPPLIER.get();
        }
        if (StringUtils.isNotEmpty(authentication.getId()) && sessions.contains(authentication.getId())) {
//      如果id不是空 并且 sessions 包含id
            authentication.setAuthenticated(true);
//            认证成功
            return authentication;
        }
        if (StringUtils.isEmpty(authentication.getId())){
//            如果id是空
            authentication.setId(GENERATE_SESSION_ID.get());
//            救赋值
        }
        authorizedUserAutoLogin();
//        认证的用户自动登录
        return authentication;
    }

    protected void  authorizedUserAutoLogin(){
        if (!PROBABILITY_DOUBLE_PREDICATE.test(ThreadLocalRandom.current().nextDouble())){
//            生成一个随机 数判断与 0.75 大小   如果大于0.75
            Authentication authentication = AUTHENTICATION_SUPPLIER.get();
            authentication.setAuthenticated(true);
//            设置成已认证
            addSession(authentication.getId());
//            添加一个session  为id
        }
    }
    protected boolean addSession(String sessionId) {
        if (sessions.size() >= MAX_SESSIONS) {
            sessions.remove();
        }
        return sessions.add(sessionId);
    }
    protected int getSessionsSize(){
        return sessions.size();
    }
}
