package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Initializeable;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.function.Function;

@Controller
@AllArgsConstructor
@Slf4j
public class RestartLessonService {
    private final WebSession webSession;
    private final UserTrackerRepository userTrackerRepository;
    private final Function<String , Flyway> flywayLessons;
    private final List<Initializeable> lessonsToInitialize;

    @RequestMapping(path = "/service/restartlesson.mvc",produces = "text/text")
    @ResponseStatus(value = HttpStatus.OK)
    public void restartLesson(){
        Lesson al = webSession.getCurrentLesson();
        log.debug("Restarting lesson: ",al);

        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        userTracker.reset(al);
        userTrackerRepository.save(userTracker);
        var flyway = flywayLessons.apply(webSession.getUserName());
        flyway.clean();
        flyway.migrate();
        lessonsToInitialize.forEach(i->i.initialize(webSession.getUser()));
    }

}
