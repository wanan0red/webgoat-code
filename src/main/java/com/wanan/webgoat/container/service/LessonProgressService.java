package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LessonProgressService {
    private final UserTrackerRepository userTrackerRepository;
    private final WebSession webSession;

    @RequestMapping(value = "/service/lessonoverview.mvc",produces = "application/json")
    @ResponseBody
    public List<LessonOverview> lessonOverview(){
        var  userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        var currentLesson = webSession.getCurrentLesson();
        if (currentLesson != null){
            var lessonTracker = userTracker.getLessonTracker(currentLesson);
            return lessonTracker.getLessonOverview().entrySet().stream()
                    .map(entry -> new LessonOverview(entry.getKey(),entry.getValue()))
                    .toList();
        }
        return List.of();
    }
    @AllArgsConstructor
    @Getter
    private static class LessonOverview{
        private Assignment assignment;
        private Boolean solved;
    }
}
