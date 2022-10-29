package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.Random;

@RestController
public class EncodingAssignment extends AssignmentEndpoint {
    public static String getBasicAuth(String username,String password){
        return Base64.getEncoder().encodeToString(username.concat(":").concat(password).getBytes());
//        获取认证 首先将用户名和 密码进行拼接 接着编码为 base64
    }
    @GetMapping(path = "/crypto/encoding/basic",produces = MediaType.TEXT_HTML_VALUE)
//    生产html字符
    @ResponseBody
    public String getBasicAuth(HttpServletRequest request){
        String basicAuth = (String) request.getSession().getAttribute("basicAuth");
//        从session中获取值
        String username = request.getUserPrincipal().getName();
//      获取当前登录的用户名
        if (basicAuth == null){
//            如果 认证为空
            String password = HashingAssignment.SECRETS[new Random().nextInt(HashingAssignment.SECRETS.length)];
//            从常量中随机挑选一个作为密码
            basicAuth = getBasicAuth(username,password);
//            获取base64值
            request.getSession().setAttribute("basicAuth",basicAuth);
//            添加到session中去
        }
        return "Authorization: Basic ".concat(basicAuth);
    }
    @PostMapping("/crypto/encoding/basic-auth")
    @ResponseBody
    public AttackResult completed(HttpServletRequest request,
                                  @RequestParam String answer_user,
                                  @RequestParam String answer_pwd){
        String basicAuth = (String) request.getSession().getAttribute("basicAuth");
        if (basicAuth != null && answer_user != null && answer_pwd != null && basicAuth.equals(getBasicAuth(answer_user,answer_pwd))){
//            如果 base 不为空 并且 answer不为空 并且与base64相等
            return success(this)
                    .feedback("crypto-encoding.success")
                    .build();
        }else {
            return failed(this).feedback("crypto-encoding.empty").build();
        }
    }
}
