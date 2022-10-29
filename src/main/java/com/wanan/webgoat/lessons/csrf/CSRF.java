package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CSRF extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A10;
    }

    @Override
    public String getTitle() {
        return "csrf.title";
    }
}
