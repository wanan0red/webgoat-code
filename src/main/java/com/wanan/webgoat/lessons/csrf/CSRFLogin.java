package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AssignmentHints({"csrf-login-hint1", "csrf-login-hint2", "csrf-login-hint3"})
public class CSRFLogin extends AssignmentEndpoint {
    private final UserTrackerRepository userTrackerRepository;

    public CSRFLogin(UserTrackerRepository userTrackerRepository) {
        this.userTrackerRepository = userTrackerRepository;
    }

    @PostMapping(path = "/csrf/login",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(HttpServletRequest request){
        String userName = request.getUserPrincipal().getName();
        if (userName.startsWith("csrf")){
            markAssignmentSolvedWithRealUser(userName);
            return success(this).feedback("csrf-login-success").build();

        }
        return failed(this).feedback("csrf-login-failed").feedbackArgs(userName).build();
    }

    private void markAssignmentSolvedWithRealUser(String userName) {
        UserTracker userTracker = userTrackerRepository.findByUser(userName);
        userTracker.assignmentSolved(getWebSession().getCurrentLesson(), this.getClass().getSimpleName());
        userTrackerRepository.save(userTracker);
    }
}
