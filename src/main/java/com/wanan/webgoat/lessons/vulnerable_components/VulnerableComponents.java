package com.wanan.webgoat.lessons.vulnerable_components;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class VulnerableComponents extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A6;
    }

    @Override
    public String getTitle() {
        return "vulnerable-components.title";
    }
}
