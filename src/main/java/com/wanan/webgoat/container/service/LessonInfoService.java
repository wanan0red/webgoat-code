package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.lessons.LessonInfoModel;
import com.wanan.webgoat.container.session.WebSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LessonInfoService {
    private final WebSession webSession;

    @RequestMapping(path = "/service/lessoninfo.mvc",produces = "application/json")
    public @ResponseBody LessonInfoModel getLessonInfo(){
        Lesson lesson = webSession.getCurrentLesson();
        return new LessonInfoModel(lesson.getTitle(),false,false,false);
    }

}
