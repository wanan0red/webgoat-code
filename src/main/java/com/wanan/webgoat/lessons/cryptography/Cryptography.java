package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Cryptography extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A2;
    }

    @Override
    public String getTitle() {
        return "6.crypto.title";
    }
}
