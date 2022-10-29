package com.wanan.webgoat.lessons.deserialization;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class InsecureDeserialization extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A8;
    }

    @Override
    public String getTitle() {
        return "insecure-deserialization.title";
    }
}
