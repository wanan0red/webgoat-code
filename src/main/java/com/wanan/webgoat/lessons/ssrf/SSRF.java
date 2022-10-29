package com.wanan.webgoat.lessons.ssrf;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SSRF extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A10;
    }

    @Override
    public String getTitle() {
        return "ssrf.title";
    }
}
