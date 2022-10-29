package com.wanan.webgoat.lessons.challenges.challenge8;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge8 extends Lesson {

    @Override
    public Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge8.title";
    }
}
