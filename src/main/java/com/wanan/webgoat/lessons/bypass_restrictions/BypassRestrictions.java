package com.wanan.webgoat.lessons.bypass_restrictions;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class BypassRestrictions extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CLIENT_SIDE;
    }

    @Override
    public String getTitle() {
        return "bypass-restrictions.title";
    }
}
