package com.wanan.webgoat.lessons.password_reset;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class ResetLinkAssignmentForgotPassword extends AssignmentEndpoint {
    private final RestTemplate restTemplate;
    private String webWolfHost;
    private String webWolfPort;
    private final String webWolfMailURL;

    public ResetLinkAssignmentForgotPassword(RestTemplate restTemplate, @Value("${webwolf.host}") String webWolfHost, @Value("${webwolf.port}") String webWolfPort, @Value("${webwolf.mail.url}") String webWolfMailURL) {
        this.restTemplate = restTemplate;
        this.webWolfHost = webWolfHost;
        this.webWolfPort = webWolfPort;
        this.webWolfMailURL = webWolfMailURL;
    }
    @PostMapping("/PasswordReset/ForgotPassword/create-password-reset-link")
    @ResponseBody
    public AttackResult sendPasswordResetLink(@RequestParam String email, HttpServletRequest request){
        String resetLink = UUID.randomUUID().toString();
//        这里先获取一个uuid 用于区分url
        ResetLinkAssignment.resetLinks.add(resetLink);
//        将链接存放进去
        String host = request.getHeader("host");
//        获取请求头的host
        if (ResetLinkAssignment.TOM_EMAIL.equals(email) && (host.contains(webWolfPort) || host.contains(webWolfHost))){
//            如果重置的是tom 的密码 并且host中包含webWolf
            ResetLinkAssignment.userToTomResetLink.put(getWebSession().getUserName(), resetLink);
//            那么就存放到tom的重置url中
            fakeClickingLinkEmail(host,resetLink);
//            这里是模拟tom去点击重置密码
        }else {
            try {
                sendMailToUser(email,host,resetLink);
//              发送邮件出去
            }catch (Exception e){
                return failed(this).output("E-mail can't be send. please try again.").build();
            }
        }
        return success(this).feedback("email.send").feedbackArgs(email).build();
    }

    private void sendMailToUser(String email, String host, String resetLink) {
        int index = email.indexOf("@");
        String username = email.substring(0,index == -1 ? email.length(): index);
        PasswordResetEmail mail = PasswordResetEmail.builder()
                .title("Your password reset link")
                .contents(String.format(ResetLinkAssignment.TEMPLATE,host,resetLink))
                .sender("password-reset@webgoat-cloud.net")
                .recipient(username).build();
        this.restTemplate.postForEntity(webWolfMailURL,mail,Object.class);
    }

    private void fakeClickingLinkEmail(String host, String resetLink) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            new RestTemplate().exchange(String.format("http://%s/PasswordReset/reset/reset-password/%s",host,resetLink), HttpMethod.GET,httpEntity,Void.class);
//            这里是模拟tom去点击重置密码
        }catch (Exception e){

        }
    }
}
