package com.wanan.webgoat.lessons.challenges.challenge7;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge7 extends Lesson {

    @Override
    public Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge7.title";
    }
}
