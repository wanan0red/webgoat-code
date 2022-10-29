package com.wanan.webgoat.lessons.client_side_filtering;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ClientSideFiltering extends Lesson {
    @Override
    protected Category getDefaultCategory() {
        return Category.CLIENT_SIDE;
    }

    @Override
    public String getTitle() {
        return "client.side.filtering.title";
    }
}
