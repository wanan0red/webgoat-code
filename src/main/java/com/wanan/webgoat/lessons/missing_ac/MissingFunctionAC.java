package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class MissingFunctionAC extends Lesson {
    public static final String PASSWORD_SALT_SIMPLE = "DeliberatelyInsecure1234";
    public static final String PASSWORD_SALT_ADMIN = "DeliberatelyInsecure1235";

    @Override
    protected Category getDefaultCategory() {
        return Category.A1;
    }

    @Override
    public String getTitle() {
        return "missing-function-access-control.title";
    }
}
