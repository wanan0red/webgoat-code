package com.wanan.webgoat.lessons.logging;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LogSpoofing extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A9;
    }

    @Override
    public String getTitle() {
        return "logging.title";
    }
}
