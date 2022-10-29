package com.wanan.webgoat.lessons.cia;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CIA extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "4.cia.title";
    }
}
