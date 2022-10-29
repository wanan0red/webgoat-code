package com.wanan.webgoat.lessons.logging;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogSpoofingTask extends AssignmentEndpoint {
    @PostMapping("/LogSpoofing/log-spoofing")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        if (Strings.isEmpty(username)){
            return failed(this).output(username).build();
        }
        username = username.replace("\n","<br/>");
        if (username.contains("<p>") || username.contains("<div>")){
            return failed(this).output("Try to think of something simple ").build();
        }
        if (username.indexOf("<br/>") < username.indexOf("admin")){
            return success(this).output(username).build();
        }
        return failed(this).output(username).build();
    }
}
