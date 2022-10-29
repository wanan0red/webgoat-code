package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class XXE extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A5;
    }

    @Override
    public String getTitle() {
        return "xxe.title";
    }
}
