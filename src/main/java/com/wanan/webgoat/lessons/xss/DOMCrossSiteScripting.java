package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;

@RestController
public class DOMCrossSiteScripting extends AssignmentEndpoint {

    @PostMapping("/CrossSiteScripting/phone-home-xss")
    @ResponseBody
    public AttackResult completed(@RequestParam Integer param1,
                                  @RequestParam Integer param2, HttpServletRequest request) {
        UserSessionData userSessionData = getUserSessionData();
        SecureRandom number = new SecureRandom();
        userSessionData.setValue("randValue", String.valueOf(number.nextInt()));

        if (param1 == 42 && param2 == 24 && request.getHeader("webgoat-requested-by").equals("dom-xss-vuln")) {
            return success(this).output("phoneHome Response is " + userSessionData.getValue("randValue").toString()).build();
        } else {
            return failed(this).build();
        }
    }
}