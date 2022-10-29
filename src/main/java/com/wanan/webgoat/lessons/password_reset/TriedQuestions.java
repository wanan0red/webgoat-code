package com.wanan.webgoat.lessons.password_reset;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class TriedQuestions {
    private Set<String> answeredQuestions = new HashSet<>();

    public void incr(String question){
        answeredQuestions.add(question);
    }
    public boolean isCompleted(){
        return answeredQuestions.size() > 1;
    }
}
