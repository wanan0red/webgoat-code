package com.wanan.webgoat.lessons.hijacksession;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class HijackSession extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "hijacksession.title";
    }
}
