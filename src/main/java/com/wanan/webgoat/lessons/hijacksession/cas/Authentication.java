package com.wanan.webgoat.lessons.hijacksession.cas;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.security.Principal;

@Getter
@ToString
public class Authentication implements Principal {
//    这里很明显继承了 Principal 那么应该是 AuthenticationProvider中的 T参数了
    private boolean authenticated = false;
    private String name;
    private Object credentials;
    private String id;

    @Builder
//    可进行链式赋值
    public Authentication(boolean authenticated, String name, Object credentials, String id) {
        this.name = name;
        this.credentials = credentials;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }
    protected  void setAuthenticated(boolean authenticated){
        this.authenticated = authenticated;
    }
    protected void setId(String id){
        this.id = id;
    }
}
