package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;
@RestController
public class SimpleMailAssignment extends AssignmentEndpoint {

    private final String webWolfURL;
    private RestTemplate restTemplate;

    public SimpleMailAssignment(RestTemplate restTemplate, @Value("${webwolf.mail.url}") String webWolfURL) {
        this.restTemplate = restTemplate;
        this.webWolfURL = webWolfURL;
    }

    @PostMapping(path = "/PasswordReset/simple-mail", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public AttackResult login(@RequestParam String email, @RequestParam String password) {
        String emailAddress = ofNullable(email).orElse("unknown@webgoat.org");
        String username = extractUsername(emailAddress);

        if (username.equals(getWebSession().getUserName()) && StringUtils.reverse(username).equals(password)) {
            return success(this).build();
        } else {
            return failed(this).feedbackArgs("password-reset-simple.password_incorrect").build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value = "/PasswordReset/simple-mail/reset")
    @ResponseBody
    public AttackResult resetPassword(@RequestParam String emailReset) {
        String email = ofNullable(emailReset).orElse("unknown@webgoat.org");
        return sendEmail(extractUsername(email), email);
    }

    private String extractUsername(String email) {
        int index = email.indexOf("@");
        return email.substring(0, index == -1 ? email.length() : index);
    }

    private AttackResult sendEmail(String username, String email) {
        if (username.equals(getWebSession().getUserName())) {
            PasswordResetEmail mailEvent = PasswordResetEmail.builder()
                    .recipient(username)
                    .title("Simple e-mail assignment")
                    .time(LocalDateTime.now())
                    .contents("Thanks for resetting your password, your new password is: " + StringUtils.reverse(username))
                    .sender("webgoat@owasp.org")
                    .build();
            try {
                restTemplate.postForEntity(webWolfURL, mailEvent, Object.class);
//                简单发送一个post请求
            } catch (RestClientException e) {
                return informationMessage(this).feedback("password-reset-simple.email_failed").output(e.getMessage()).build();
            }
            return informationMessage(this).feedback("password-reset-simple.email_send").feedbackArgs(email).build();
        } else {
            return informationMessage(this).feedback("password-reset-simple.email_mismatch").feedbackArgs(username).build();
        }
    }
}

