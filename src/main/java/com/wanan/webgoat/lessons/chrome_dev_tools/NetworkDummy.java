package com.wanan.webgoat.lessons.chrome_dev_tools;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkDummy extends AssignmentEndpoint {
    @PostMapping("/ChromeDevTools/dummy")
    @ResponseBody
    public AttackResult completed(@RequestParam String successMessage){
//        获取successMessage参数
        UserSessionData userSessionData = getUserSessionData();
//        这里我们去看 UserSessionData在哪里赋值的
        String answer = (String) userSessionData.getValue("randValue");
        if (successMessage != null && successMessage.equals(answer)){
            return success(this).feedback("xss-dom-message-success").build();
        }else {
            return failed(this).feedback("xss-dom-message-failure").build();
        }
    }
}
