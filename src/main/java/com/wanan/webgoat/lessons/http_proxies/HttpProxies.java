package com.wanan.webgoat.lessons.http_proxies;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class HttpProxies extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "2.http-proxies.title";
    }
}
