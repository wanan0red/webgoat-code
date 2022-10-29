package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@AssignmentHints({"idor.hints.otherProfile1", "idor.hints.otherProfile2", "idor.hints.otherProfile3", "idor.hints.otherProfile4", "idor.hints.otherProfile5", "idor.hints.otherProfile6", "idor.hints.otherProfile7", "idor.hints.otherProfile8", "idor.hints.otherProfile9"})
public class IDORViewOtherProfile extends AssignmentEndpoint {
    @Autowired
    UserSessionData userSessionData;
//    session对象
    @GetMapping(path = "/IDOR/profile/{userId}",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(@PathVariable("userId") String userId, HttpServletResponse response){
//        获取路径中的userId参数
        Map<String,Object> details = new HashMap<>();
        if (userSessionData.getValue("idor-authenticated-as").equals("tom")){
//      tom登录
            String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");
            if (userId != null && !userId.equals(authUserId)){
//                id和认证id一样
                UserProfile requestedProfile = new UserProfile(userId);
                if (requestedProfile.getUserId().equals("2342388")){
//                    bill 这里要注意了哦 这里的id是 bill的
                    return success(this).feedback("idor.view.profile.success").output(requestedProfile.profileToMap().toString()).build();
                }else {
                    return failed(this).feedback("idor.view.profile.close1").build();
                }
            }else {
                return failed(this).feedback("idor.view.profile.close2").build();
            }
        }
        return failed(this).build();
    }
}
