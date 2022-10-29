package com.wanan.webgoat.lessons.lesson_template;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonTemplate extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "lesson-template.title";
    }
}
