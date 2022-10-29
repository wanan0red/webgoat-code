package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@AssignmentHints({"idor.hints.otherProfile1", "idor.hints.otherProfile2", "idor.hints.otherProfile3", "idor.hints.otherProfile4", "idor.hints.otherProfile5", "idor.hints.otherProfile6", "idor.hints.otherProfile7", "idor.hints.otherProfile8", "idor.hints.otherProfile9"})
public class IDOREditOtherProfiile extends AssignmentEndpoint {
    @Autowired
    private UserSessionData userSessionData;

    //    获取session 对象
    @PutMapping(path = "/IDOR/profile/{userId}", consumes = "application/json")
//    注意这里是put请求 并且传入是json格式的文件
    @ResponseBody
    public AttackResult completed(@PathVariable("userId") String userId, @RequestBody UserProfile userSubmittedProfile) {
//        这里传入的body是 UserProfile 类的json对象
        String authUserId = (String) userSessionData.getValue("idor-authenticated-user-id");
        UserProfile currentUserProfile = new UserProfile(userId);
//        创建一个UserProfile
        if (userSubmittedProfile.getUserId() != null && !userSubmittedProfile.getUserId().equals(authUserId) ) {
//          如果id不等于提交的
            currentUserProfile.setColor(userSubmittedProfile.getColor());
            currentUserProfile.setRole(userSubmittedProfile.getRole());
//            设置两个值
            userSessionData.setValue("idor-updated-other-profile", currentUserProfile);
//            这里添加一个session
            if (currentUserProfile.getRole() <= 1 && currentUserProfile.getColor().toLowerCase().equals("red")) {
//                如果得到的角色 是 <=1的数 并且当前用户的color 是 red
                return success(this).feedback("idor.edit.profile.success1")
                        .output(currentUserProfile.profileToMap().toString())
                        .build();
            }
            if (currentUserProfile.getRole() > 1 && currentUserProfile.getColor().toLowerCase().equals("red")) {
//              如果得到role > 1 并且 当前用户时red
                return failed(this)
                        .feedback("idor.edit.profile.failure1")
                        .output(currentUserProfile.profileToMap().toString())
                        .build();
            }
            if (currentUserProfile.getRole() <= 1 && !currentUserProfile.getColor().toLowerCase().equals("red")) {
//                如果得到role <= 1 并且 当前用户时不是red
                return failed(this)
                        .feedback("idor.edit.profile.failure2")
                        .output(currentUserProfile.profileToMap().toString())
                        .build();
            }
            return failed(this)
                    .feedback("idor.edit.profile.failure3")
                    .output(currentUserProfile.profileToMap().toString())
                    .build();
        } else if (userSubmittedProfile.getUserId().equals(authUserId)) {
//            或者当前用户id不等于 得到的id
            return failed(this)
                    .feedback("idor.edit.profile.failure4")
                    .build();
        }
        if (currentUserProfile.getColor().equals("black") && currentUserProfile.getRole() <= 1) {
//            如果当前用户颜色是black 并且 role <= 1
            return success(this)
                    .feedback("idor.edit.profile.success2")
                    .output(userSessionData.getValue("idor-updated-own-profile").toString())
                    .build();
        } else {
            return failed(this)
                    .feedback("idor.edit.profile.failure3")
                    .build();
        }
    }
}
