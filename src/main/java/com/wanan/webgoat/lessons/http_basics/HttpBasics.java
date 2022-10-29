package com.wanan.webgoat.lessons.http_basics;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
//实现bean的注入,当我们的类不属于@controller @services的时候我们就可以使用@component来标注这个类
public class HttpBasics  extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    @Override
    public String getTitle() {
        return "1.http-basics.title";
    }
}
