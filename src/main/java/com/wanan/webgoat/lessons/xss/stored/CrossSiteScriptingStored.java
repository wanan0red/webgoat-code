package com.wanan.webgoat.lessons.xss.stored;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CrossSiteScriptingStored extends Lesson {

    @Override
    protected Category getDefaultCategory() {
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "xss-stored.title";
    }
}
