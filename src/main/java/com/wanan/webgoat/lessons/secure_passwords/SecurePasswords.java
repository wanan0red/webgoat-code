package com.wanan.webgoat.lessons.secure_passwords;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class SecurePasswords extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.A7;
    }

    @Override
    public String getTitle() {
        return "secure-passwords.title";
    }
}
