package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrossSiteScriptingLesson1 extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/attack1")
    @ResponseBody
    public AttackResult completed(@RequestParam(value = "checkboxAttack1",required = false)String checkboxValue){
        if (checkboxValue != null){
            return success(this).build();
        }
        return failed(this).feedback("xss.lesson1.failure").build();
    }
}
