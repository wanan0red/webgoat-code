package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"access-control.hidden-menus.hint1","access-control.hidden-menus.hint2","access-control.hidden-menus.hint3"})
public class MissingFunctionACHiddenMenus extends AssignmentEndpoint {
    @PostMapping(path = "/access-control/hidden-menu",produces = {"application/json"})
    @ResponseBody
    public AttackResult completed(String hiddenMenu1,String hiddenMenu2){
        if (hiddenMenu1.toLowerCase().equals("users") && hiddenMenu2.toLowerCase().equals("config")){
//            这里是对比两个 值是否相等 比较简单 但是比较考验前段的知识
            return success(this)
                    .output("")
                    .feedback("access-control.hidden-menus.success")
                    .build();
        }
        if (hiddenMenu1.equals("Config") && hiddenMenu2.equals("Users")){
            return failed(this)
                    .output("")
                    .feedback("access-control.hidden-menus.failure")
                    .build();
        }
        return failed(this)
                .feedback("access-control.hidden-menus.failure")
                .output("")
                .build();
    }
}
