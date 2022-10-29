package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"csrf-get.hint1", "csrf-get.hint2", "csrf-get.hint3", "csrf-get.hint4"})
public class CSRFConfirmFlag1 extends AssignmentEndpoint {

    @Autowired
    UserSessionData userSessionData;

    @PostMapping(path = "/csrf/confirm-flag-1",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(String confirmFlagVal){
        Object userSessionDataStr = userSessionData.getValue("csrf-get-success");
        if (userSessionDataStr != null && confirmFlagVal.equals(userSessionDataStr.toString())){
            return success(this)
                    .feedback("csrf-get-null-referer.success")
                    .output("Correct, the flag was " + userSessionData.getValue("csrf-get-success"))
                    .build();
        }
        return failed(this).build();
    }
}
