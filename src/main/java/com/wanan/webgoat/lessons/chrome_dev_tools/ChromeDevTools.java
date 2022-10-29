package com.wanan.webgoat.lessons.chrome_dev_tools;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ChromeDevTools extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "3.chrome-dev-tools.title";
    }
}
