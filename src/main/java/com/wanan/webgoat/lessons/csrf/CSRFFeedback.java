package com.wanan.webgoat.lessons.csrf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@AssignmentHints({"csrf-feedback-hint1", "csrf-feedback-hint2", "csrf-feedback-hint3"})
public class CSRFFeedback extends AssignmentEndpoint {
    @Autowired
    private UserSessionData userSessionData;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/csrf/feedback/message",produces = {"application/json"})
//    这里需要的是json格式的数据
    @ResponseBody
    public AttackResult completed(HttpServletRequest request , @RequestBody String feedback){
        try {
            objectMapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
            objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            objectMapper.enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
            objectMapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
            objectMapper.readValue(feedback.getBytes(), Map.class);
        }catch (IOException e){
            return failed(this).feedback(ExceptionUtils.getStackTrace(e)).build();
        }
        boolean correctCSRF = requestContainsWebGoatCookie(request.getCookies()) && request.getContentType().contains(MediaType.TEXT_PLAIN_VALUE);
        correctCSRF &= hostOrRefererDifferentHost(request);
        if (correctCSRF){
            String flag = UUID.randomUUID().toString();
            userSessionData.setValue("csrf-feedback",flag);
            return success(this).feedback("csrf-feedback-success").feedbackArgs(flag).build();
        }
        return failed(this).build();
    }
    @PostMapping(path = "/csrf/feedback", produces = "application/json")
    @ResponseBody
    public AttackResult flag(@RequestParam("confirmFlagVal") String flag) {
        if (flag.equals(userSessionData.getValue("csrf-feedback"))) {
            return success(this).build();
        } else {
            return failed(this).build();
        }
    }

    private boolean hostOrRefererDifferentHost(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String host = request.getHeader("Host");
        if (referer != null){
            return !referer.contains(host);
        }else {
            return true;
        }
    }

    private boolean requestContainsWebGoatCookie(Cookie[] cookies) {
        if (cookies != null){
            for (Cookie c : cookies){
                if (c.getName().equals("JSESSIONID")){
                    return true;
                }
            }
        }
        return false;
    }
}
