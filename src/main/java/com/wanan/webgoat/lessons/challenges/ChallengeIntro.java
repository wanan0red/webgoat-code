package com.wanan.webgoat.lessons.challenges;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;

public class ChallengeIntro extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CHALLENGE;
    }

    @Override
    public String getTitle() {
        return "challenge0.title";
    }
}
