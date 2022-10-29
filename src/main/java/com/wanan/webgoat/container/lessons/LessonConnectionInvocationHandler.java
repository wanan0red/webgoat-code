package com.wanan.webgoat.container.lessons;

import com.wanan.webgoat.container.users.WebGoatUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

@Slf4j
//日志系统
public class LessonConnectionInvocationHandler implements InvocationHandler {
    private final Connection targetConnection;

    public LessonConnectionInvocationHandler(Connection targetConnection) {
        this.targetConnection = targetConnection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        进行身份验证的
        if (authentication != null && authentication.getPrincipal() instanceof WebGoatUser user){
//            如果拿到的对象输入webgoatuser就执行
            try(var statement = targetConnection.createStatement()){
                statement.execute("SET SCHEMA \"" + user.getUsername() + "\"");
//                使模式成为SQL控制台会话的默认模式。如果不使用此语句，则会将默认模式视为用户模式。如果您正在使用系统用户，则很危险。
            }

        }
        try{
            return method.invoke(targetConnection,args);
        }catch (InvocationTargetException e){
            throw e.getTargetException();
        }

    }
}
