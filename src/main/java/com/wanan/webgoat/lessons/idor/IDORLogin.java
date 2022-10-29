package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AssignmentHints({"idor.hints.idor_login"})
public class IDORLogin extends AssignmentEndpoint {
    private Map<String,Map<String,String >> idorUserInfo = new HashMap<>();
//    外面map用来存储用户  内部的map 用来存储用户的信息
    public void initIDORInfo(){
        idorUserInfo.put("tom",new HashMap<String, String>());
        idorUserInfo.get("tom").put("password","cat");
        idorUserInfo.get("tom").put("id","2342384");
        idorUserInfo.get("tom").put("color","yellow");
        idorUserInfo.get("tom").put("size","small");

        idorUserInfo.put("bill",new HashMap<String, String>());
        idorUserInfo.get("bill").put("password","buffalo");
        idorUserInfo.get("bill").put("id","2342388");
        idorUserInfo.get("bill").put("color","brown");
        idorUserInfo.get("bill").put("size","large");

    }

    @PostMapping("/IDOR/login")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        initIDORInfo();
//        先进行用户的初始化
        UserSessionData userSessionData = getUserSessionData();
//        先获取一个userSessionData对象这里主要是用于存放关于用户的一些信息 请注意这里的userSessionData其实是在全局范围内生效的
        if (idorUserInfo.containsKey(username)){
//            如果idorUserInfo包含这个用户
            if ("tom".equals(username) && idorUserInfo.get("tom").get("password").equals(password)){
                userSessionData.setValue("idor-authenticated-as",username);
//                这里先存入了一个username
                userSessionData.setValue("idor-authenticated-user-id",idorUserInfo.get(username).get("id"));
//                紧接着存入了一个id
                return success(this).feedback("idor.login.success").feedbackArgs(username).build();
            }else {
                return failed(this).feedback("idor.login.failure").build();
            }
        }else {
            return failed(this).feedback("idor.login.failure").build();
        }
    }
}
