package com.wanan.webgoat.lessons.challenges;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
public class Flag extends AssignmentEndpoint {
    public static final Map<Integer,String> FLAGS = new HashMap<>();

    @Autowired
    private UserTrackerRepository userTrackerRepository;
    @Autowired
    private WebSession webSession;

    @AllArgsConstructor
    private class FlagPosted{
        @Getter
        private boolean lessonCompleted;

    }

    @PostConstruct
    public void initFlags(){
        IntStream.range(1,10).forEach(i -> FLAGS.put(i, UUID.randomUUID().toString()));
    }

    @RequestMapping(path = "/challenge/flag",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult postFlag(@RequestParam String flag){
        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        String currentChallenge = webSession.getCurrentLesson().getName();
        int challengeNumber = Integer.valueOf(currentChallenge.substring(currentChallenge.length() -1,currentChallenge.length()));
//        截取是第几关
        String expectedFlag = FLAGS.get(challengeNumber);
        final AttackResult attackResult;
        if (expectedFlag.equals(flag)){
            userTracker.assignmentSolved(webSession.getCurrentLesson(),"Assignment" + challengeNumber);
            attackResult  = success(this).feedback("challenge.flag.correct").build();
        }else {
            userTracker.assignmentFailed(webSession.getCurrentLesson());
            attackResult = failed(this).feedback("challenge.flag.incorrect").build();
        }
        userTrackerRepository.save(userTracker);
        return attackResult;

    }
}
