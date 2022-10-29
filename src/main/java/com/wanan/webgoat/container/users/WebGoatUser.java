package com.wanan.webgoat.container.users;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
@Entity
// @Entity是指这个类映射到数据库表
public class WebGoatUser implements UserDetails {
    public static final String ROLE_USER = "WEBGOAT_USER";
    public static final String ROLE_ADMIN  = "WEBGOAT_ADMIN";

    @Id
//    用于声明一个实体类的属性映射为数据库的主键列。该属性通常置于属性声明语句之前，可与声明语句同行，也可写在单独行上。
    private String username;
    private String password;
    private String role = ROLE_USER;

    @Transient
//    java 的transient关键字的作用是需要实现Serilizable接口，将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中。
    private User user;



    protected WebGoatUser() {
    }
    public WebGoatUser(String username,String password){
        this(username,password,ROLE_USER);
    }
    public WebGoatUser(String username,String password,String role){
        this.username = username;
        this.password = password;
        this.role = role;
        createUser();
    }

    public void createUser() {
        this.user = new User(username,password,getAuthorities());
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
         return o instanceof WebGoatUser webGoatUser && this.user.equals(webGoatUser.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole()));

    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
