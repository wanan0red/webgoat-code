package com.wanan.webgoat.lessons.webwolf_introduction;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class WebWolfIntroduction extends Lesson {
    @Override
    public Category getDefaultCategory(){
        return Category.INTRODUCTION;
    }
    @Override
    public  String getTitle(){
        return "webwolf.title";
    }
}
