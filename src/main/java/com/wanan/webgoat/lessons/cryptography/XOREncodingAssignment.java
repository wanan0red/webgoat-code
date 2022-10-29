package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"crypto-encoding-xor.hints.1"})
public class XOREncodingAssignment extends AssignmentEndpoint {
    @PostMapping("/crypto/encoding/xor")
    @ResponseBody
    public AttackResult completed(@RequestParam String answer_pwd1){
        if (answer_pwd1 != null && answer_pwd1.equals("databasepassword")){
//            简单的进行字符串的对比
            return success(this)
                    .feedback("crypto-encoding-xor.success")
                    .build();
        }
        return failed(this)
                .feedback("crypto-encoding-xor.empty")
                .build();
    }
}
