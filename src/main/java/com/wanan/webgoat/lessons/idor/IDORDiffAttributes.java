package com.wanan.webgoat.lessons.idor;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AssignmentHints({"idor.hints.idorDiffAttributes1","idor.hints.idorDiffAttributes2","idor.hints.idorDiffAttributes3"})
public class IDORDiffAttributes extends AssignmentEndpoint {
    @PostMapping("/IDOR/diff-attributes")
    @ResponseBody
    public AttackResult completed(@RequestParam String attributes){
//        接收一个attributes参数
        attributes = attributes.trim();
//        对参数进行前后去空
        String[] diffAttribs = attributes.split(",");
//        将参数通过，进行分割成数组
        if (diffAttribs.length < 2){
//            如果数组的长度小于2 直接返回错误
            return failed(this).feedback("idor.diff.attributes.missing").build();
        }

        if (diffAttribs[0].toLowerCase().trim().equals("userid") && diffAttribs[1].toLowerCase().trim().equals("role") ||
        diffAttribs[1].toLowerCase().trim().equals("userid") && diffAttribs[0].toLowerCase().trim().equals("role")){
//        如果数组中第一个值先转换为小写 后去前后空格 接着与userid进行对比 对第二个进行比较如果是role
//            简单来说就是需要前两个是userid
            return success(this).feedback("idor.diff.success").build();
        }else {
            return failed(this).feedback("idor.diff.failure").build();
        }
    }
}
