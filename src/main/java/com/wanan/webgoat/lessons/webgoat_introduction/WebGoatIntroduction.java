package com.wanan.webgoat.lessons.webgoat_introduction;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
//实现bean的注入,当我们的类不属于@controller @services的时候我们就可以使用@component来标注这个类
public class WebGoatIntroduction extends Lesson {
    @Override
    public Category getDefaultCategory(){
        return Category.INTRODUCTION;
    }
    @Override
    public String getTitle(){
        return "webgoat.title";
    }
}
