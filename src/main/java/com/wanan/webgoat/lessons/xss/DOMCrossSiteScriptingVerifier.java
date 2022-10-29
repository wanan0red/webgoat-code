package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-dom-message-hint-1", "xss-dom-message-hint-2", "xss-dom-message-hint-3", "xss-dom-message-hint-4", "xss-dom-message-hint-5", "xss-dom-message-hint-6"})
public class DOMCrossSiteScriptingVerifier extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/dom-follow-up")
    @ResponseBody
    public AttackResult completed(@RequestParam String  successMessage){
        UserSessionData userSessionData = getUserSessionData();
        String answer = (String) userSessionData.getValue("randValue");
        if (successMessage.equals(answer)){
            return success(this).feedback("xss-dom-message-success").build();
        }else {
            return failed(this).feedback("xss-dom-message-failure").build();
        }
    }
}
