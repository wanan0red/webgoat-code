package com.wanan.webgoat.container.session;

import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.users.WebGoatUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serial;
import java.io.Serializable;

public class WebSession implements Serializable {

    private static final long serialVersionUID = 8965787678158574766L;
//    可序列化
    private final WebGoatUser currentUser;
    private transient Lesson currentLesson;
    private boolean securityEnabled;


    public WebSession() {
        this.currentUser = (WebGoatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
//    获取当前用户
    public void setCurrentLesson(Lesson lesson){
        this.currentLesson = lesson;
    }


    public Lesson getCurrentLesson() {
        return this.currentLesson;
    }
    public String getUserName(){
        return currentUser.getUsername();
    }
    public WebGoatUser getUser(){
        return currentUser;
    }
    public void toggleSecurity(){
        this.securityEnabled = !this.securityEnabled;
    }
    public boolean isSecurityEnable(){
        return securityEnabled;
    }



}
