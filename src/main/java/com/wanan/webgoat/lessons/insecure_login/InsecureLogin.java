package com.wanan.webgoat.lessons.insecure_login;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class InsecureLogin extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "insecure-login.title";
    }
}
