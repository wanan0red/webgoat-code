package com.wanan.webgoat.lessons.html_tampering;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class HtmlTampering extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CLIENT_SIDE;
    }

    @Override
    public String getTitle() {
        return "html-tampering.title";
    }
}
