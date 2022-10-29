package com.wanan.webgoat.lessons.challenges.challenge5;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge5 extends Lesson {

    @Override
    public Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge5.title";
    }
}
