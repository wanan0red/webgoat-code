package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.sql_injection.advanced.SqlInjectionLesson6a;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"SqlOnlyInputValidation-1", "SqlOnlyInputValidation-2", "SqlOnlyInputValidation-3"})
public class SqlOnlyInputValidation extends AssignmentEndpoint {
    private final SqlInjectionLesson6a lesson6a;

    public SqlOnlyInputValidation(SqlInjectionLesson6a lesson6a) {
        this.lesson6a = lesson6a;
    }

    @PostMapping("/SqlOnlyInputValidation/attack")
    @ResponseBody
    public AttackResult attack(@RequestParam("userid_sql_only_input_validation")String userid){
        if (userid.contains(" ")){
//            过滤sql语句中的空格
            return failed(this).feedback("SqlOnlyInputValidation-failed").build();
        }
        AttackResult attackResult = lesson6a.injectableQuery(userid);
        return new AttackResult(attackResult.isLessonCompleted(),attackResult.getFeedback(),attackResult.getOutput(),getClass().getSimpleName(),true);
    }
}
