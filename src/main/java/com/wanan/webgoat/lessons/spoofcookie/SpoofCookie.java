package com.wanan.webgoat.lessons.spoofcookie;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SpoofCookie extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "spoofcookie.title";
    }
}
