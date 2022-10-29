package com.wanan.webgoat.lessons.auth_bypass;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import com.wanan.webgoat.container.session.WebSession;
import org.jcodings.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AssignmentHints({"auth-bypass.hints.verify.1", "auth-bypass.hints.verify.2", "auth-bypass.hints.verify.3", "auth-bypass.hints.verify.4"})
public class VerifyAccount extends AssignmentEndpoint {
    @Autowired
    private WebSession webSession;
    @Autowired
    UserSessionData userSessionData;

    @PostMapping(path = "/auth-bypass/verify-account",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(@RequestParam String userId, @RequestParam String verifyMethod, HttpServletRequest req)throws ServerException , IOException{
        AccountVerificationHelper verificationHelper = new AccountVerificationHelper();
        Map<String,String> submittedAnswers = parseSecQuestions(req);
        if (verificationHelper.didUserLikelyCheat((HashMap) submittedAnswers)){
//            判断用户是否作弊
            return failed(this).feedback("verify-account.cheated")
                    .output("Yes, you guessed correctly,but see the feedback message")
                    .build();
        }

        if (verificationHelper.verifyAccount(Integer.valueOf(userId),(HashMap)submittedAnswers)){
            userSessionData.setValue("account-verified-id",userId);
            return success(this).feedback("verify-account.success").build();
        }else {
            return failed(this).feedback("verify-account.failed").build();
        }
    }

    private Map<String, String> parseSecQuestions(HttpServletRequest req) {
        Map<String,String> userAnswers = new HashMap<>();
        List<String > paramNames = Collections.list(req.getParameterNames());
        for (String paramName : paramNames){
            if (paramName.contains("secQuestion")){
//                这里是挑选其中参数带有secQuestion 的
                userAnswers.put(paramName,req.getParameter(paramName));
            }
        }
        return (HashMap) userAnswers;
    }

}
