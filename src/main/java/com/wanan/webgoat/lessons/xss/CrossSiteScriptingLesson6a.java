package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-reflected-6a-hint-1", "xss-reflected-6a-hint-2", "xss-reflected-6a-hint-3", "xss-reflected-6a-hint-4"})
public class CrossSiteScriptingLesson6a extends AssignmentEndpoint {
    @Autowired
    UserSessionData userSessionData;
    @PostMapping("/CrossSiteScripting/attack6a")
    @ResponseBody
    public AttackResult completed(@RequestParam String DOMTestRoute){
        if (DOMTestRoute.matches("start\\.mvc#test(\\/|)")){
            return success(this).feedback("xss-reflected-6a-success").build();
        }else {
            return failed(this).feedback("xss-reflected-6a-failure").build();
        }
    }
}
