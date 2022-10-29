package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class IDOR extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "idor.title";
    }
}
