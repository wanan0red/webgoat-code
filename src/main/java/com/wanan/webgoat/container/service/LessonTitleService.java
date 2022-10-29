package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LessonTitleService {
    private final WebSession webSession;
    public LessonTitleService(final WebSession webSession){
        this.webSession = webSession;
    }
    @RequestMapping(path = "/service/lessontitle.mvc",produces = "application/html")
    public @ResponseBody String showPlan(){
        Lesson lesson = webSession.getCurrentLesson();
        return lesson != null ? lesson.getTitle() :"";

    }
}
