package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SqlInjectionMitigations extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "3.sql.mitigation.title";
    }
}
