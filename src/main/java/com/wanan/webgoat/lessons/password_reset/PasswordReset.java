package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class PasswordReset extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "password-reset.title";
    }
}
