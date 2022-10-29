package com.wanan.webgoat.lessons.http_basics;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AssignmentPath;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"http-basics.hints.http_basic_quiz.1","http-basics.hints.http_basic_quiz.2"})
@AssignmentPath("HttpBasics/attack2")
//这里AssignmentPath 需要去看下
public class HttpBasicsQuiz extends AssignmentEndpoint {
    @PostMapping("/HttpBasics/attack2")
    @ResponseBody
    public AttackResult completed(@RequestParam String answer,@RequestParam String magic_answer,@RequestParam String magic_num){
        if ("POST".equalsIgnoreCase(answer) && magic_answer.equals(magic_num)){
//            如果post请求参数中answer与POST字符相同并且参数中magic_answer 与 magic_num相同就过关
            return success(this).build();
        }else {
            if (!"POST".equalsIgnoreCase(answer)){
                return failed(this).feedback("http-basics.incorrect").build();
            }
            if (!magic_answer.equals(magic_num)){
                return failed(this).feedback("http-basics.magic").build();
            }
        }
        return failed(this).build();
    }
}
