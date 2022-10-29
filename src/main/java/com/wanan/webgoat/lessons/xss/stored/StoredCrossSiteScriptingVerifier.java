package com.wanan.webgoat.lessons.xss.stored;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredCrossSiteScriptingVerifier extends AssignmentEndpoint {

    @PostMapping("/CrossSiteScripting/stored-xss-follow-up")
    @ResponseBody
    public AttackResult completed(@RequestParam String successMessage){
        UserSessionData userSessionData = getUserSessionData();
        if (successMessage.equals(userSessionData.getValue("randValue").toString())){
            return success(this).feedback("xss-stored-callback-success").build();
        }else {
            return failed(this).feedback("xss-stored-callback-failure").build();
        }
    }
}
