package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"idor.hints.ownProfileAltUrl1","idor.hints.ownProfileAltUrl2","idor.hints.ownProfileAltUrl3"})
public class IDORViewOwnProfileAltUrl extends AssignmentEndpoint {
    @Autowired
    UserSessionData userSessionData;
//    获取seesion对象
    @PostMapping("/IDOR/profile/alt-path")
    @ResponseBody
    public AttackResult completed(@RequestParam String url){
//        获取请求参数url
        try {
            if (userSessionData.getValue("idor-authenticated-as").equals("tom")){
//                确认是否是tom登录了
                String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");
//                获取id
                String[] urlParts = url.split("/");
//                根据/进行分割
                if (urlParts[0].equals("WebGoat")&&urlParts[1].equals("IDOR")&&urlParts[2].equals("profile")&&urlParts[3].equals(authUserId)){
//                    分别按WebGoat IDOR profile id进行对比
                    UserProfile userProfile = new UserProfile(authUserId);
//                    创建一个用户
                    return success(this).feedback("idor.view.own.profile.success").output(userProfile.profileToMap().toString()).build();
//                    先转换成数组后进行输出
                }else {
                    return failed(this).feedback("idor.view.own.profile.failure1").build();
                }
            }else {
                return failed(this).feedback("idor.view.own.profile.failure2").build();
            }
        }catch (Exception ex){
            return failed(this).feedback("an error occurred with your request").build();
        }
    }
}
