package com.wanan.webgoat.lessons.auth_bypass;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class AuthBypass extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "auth-bypass.title";
    }
}
