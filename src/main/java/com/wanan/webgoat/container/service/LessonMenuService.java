package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.*;
import com.wanan.webgoat.container.session.Course;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.LessonTracker;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class LessonMenuService {
    public static final String URL_LESSONMENU_MVC = "/service/lessonmenu.mvc";
//    这里的请求其实是根据一定的时间间隔去一直请求的
    private final Course course;
    private final WebSession webSession;
    private UserTrackerRepository userTrackerRepository;

    @Value("#{'${exclude.categories}'.split(',')}")
    private List<String > excludeCategories;

    @Value("#{'${exclude.lessons}'.split(',')}")
    private List<String > excludeLessons;

    @RequestMapping(path = URL_LESSONMENU_MVC,produces = "application/json")
    public @ResponseBody List<LessonMenuItem> showLeftNav(){
//        这里其实已经规定好了返回体为LessonMenuItem 我们跟进去看一下LessonMenuItem
        List<LessonMenuItem> menu = new ArrayList<>();
        List<Category> categories = course.getCategories();
        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());

        for (Category category: categories){
            if (excludeCategories.contains(category.name())){
                continue;
            }
            LessonMenuItem categoryItem = new LessonMenuItem();
            categoryItem.setName(category.getName());
            categoryItem.setType(LessonMenuItemType.CATEGORY);

            List<Lesson> lessons = course.getLessons(category);
            lessons = lessons.stream().sorted(Comparator.comparing(Lesson::getTitle)).toList();
            for (Lesson lesson:lessons){
                if (excludeLessons.contains(lesson.getName())){
                    continue;
                }
                LessonMenuItem lessonItem = new LessonMenuItem();
                lessonItem.setName(lesson.getTitle());
                lessonItem.setLink(lesson.getLink());
                lessonItem.setType(LessonMenuItemType.LESSON);
                LessonTracker lessonTracker = userTracker.getLessonTracker(lesson);
                boolean lessonSolved = lessonCompleted(lessonTracker.getLessonOverview(),lesson);
                lessonItem.setComplete(lessonSolved);
                categoryItem.addChild(lessonItem);

            }
            categoryItem.getChildren().sort(((o1, o2) -> o1.getRanking() - o2.getRanking()));
            menu.add(categoryItem);


        }
        return menu;
    }
    private boolean lessonCompleted(Map<Assignment,Boolean> map,Lesson currentLesson){
        boolean result = true;
        for (Map.Entry<Assignment,Boolean> entry : map.entrySet()){
            Assignment storedAssignment = entry.getKey();
            for (Assignment lessonAssignment : currentLesson.getAssignments()){
                if (lessonAssignment.getName().equals(storedAssignment.getName())){
                    result = result && entry.getValue();
                    break;
                }
            }
        }
        return result;
    }

}
