package com.wanan.webgoat.lessons.http_basics;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"http-basics.hints.http_basics_lesson.1"})
//这里的hints需要我们重点去看
public class HttpBasicsLesson extends AssignmentEndpoint {
    @PostMapping("/HttpBasics/attack1")
    @ResponseBody
    public AttackResult completed(@RequestParam String person){
//        获取person参数
        if (!person.isBlank()){
//            如果字符串不仅仅是包含空字符
            return success(this)
                    .feedback("http-basics.reversed")
                    .feedbackArgs(new StringBuilder(person).reverse().toString())
                    .build();
        }else {
            return failed(this).feedback("http-basics.empty").build();
        }
    }
}
