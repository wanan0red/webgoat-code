package com.wanan.webgoat.lessons.path_traversal;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class PathTraversal extends Lesson {
    @Override
    public Category getDefaultCategory(){
        return Category.A3;
    }

    @Override
    public String getTitle() {
        return "path-traversal-title";
    }

}
