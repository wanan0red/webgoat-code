package com.wanan.webgoat.lessons.challenges.challenge1;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Challenge1 extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge1.title";
    }
}
