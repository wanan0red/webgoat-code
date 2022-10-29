package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.session.UserSessionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class IDORViewOwnProfile {
    @Autowired
    UserSessionData userSessionData;
//    这里就是我们之间的全局变量 userSessionData

    @GetMapping(path = {"/IDOR/own","/IDOR/profile"},produces = {"application/json"})
//    这里很明显示产生了一个json的数据返回回去的
    @ResponseBody
    public Map<String,Object> invoke(){
        Map<String,Object> details =  new HashMap<>();
        try {
            if (userSessionData.getValue("idor-authenticated-as").equals("tom")){
//                如果这里已经登录的对象里存在tom
                String authUserId  = (String) userSessionData.getValue("idor-authenticated-user-id");
//                就取出这个id来
                UserProfile userProfile =  new UserProfile(authUserId);
//                通过这个id 去创建一个UserProfile 这个类是一个数据类
                details.put("userId",userProfile.getUserId());
                details.put("name",userProfile.getName());
                details.put("color",userProfile.getColor());
                details.put("size",userProfile.getSize());
                details.put("role",userProfile.getRole());
//                在详情里放入这些值 然后返回回去
            }else {
                details.put("error","You do not have privileges to view the profile. Authenticate as tom first please.");
            }
        }catch (Exception ex){
            log.error("something went wrong",ex.getMessage());
        }
        return details;
    }
}
