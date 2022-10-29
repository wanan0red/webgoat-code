package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SqlInjection extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "1.sql.injection.title";
    }
}
