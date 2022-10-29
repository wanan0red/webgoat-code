package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.Course;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.LessonTracker;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReportCardService {
    private final WebSession webSession;
    private final UserTrackerRepository userTrackerRepository;
    private final Course course;
    private final PluginMessages pluginMessages;

    @GetMapping(path = "/service/reportcard.mvc",produces = "application/json")
    @ResponseBody
    public ReportCard reportCard(){
        final ReportCard reportCard = new ReportCard();
        reportCard.setTotalNumberOfLessons(course.getTotalOfLessons());
        reportCard.setTotalNumberOfAssignments(course.getTotalOfAssignments());

        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        reportCard.setNumberOfAssignmentsSolved(userTracker.numberOfAssignmentsSolved());
        reportCard.setNumberOfLessonsSolved(userTracker.numberOfLessonsSolved());

        for (Lesson lesson : course.getLessons()){
            LessonTracker lessonTracker = userTracker.getLessonTracker(lesson);
            final LessonStatistics lessonStatistics = new LessonStatistics();
            lessonStatistics.setName(pluginMessages.getMessage(lesson.getTitle()));
            lessonStatistics.setNumberOfAttempts(lessonTracker.getNumberOfAttempts());
            lessonStatistics.setSolved(lessonTracker.isLessonSolved());
            reportCard.lessonStatistics.add(lessonStatistics);

        }
        return reportCard;
    }
    @Getter
    @Setter
    private final class ReportCard{
        private int totalNumberOfLessons;
        private int totalNumberOfAssignments;
        private int solvedLessons;
        private int numberOfAssignmentsSolved;
        private int numberOfLessonsSolved;
        private List<LessonStatistics> lessonStatistics =  new ArrayList<>();
    }
    @Setter
    @Getter
    private final class LessonStatistics{
        private String name;
        private boolean solved;
        private int numberOfAttempts;
    }
}
