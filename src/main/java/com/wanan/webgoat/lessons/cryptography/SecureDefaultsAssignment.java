package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@AssignmentHints({"crypto-secure-defaults.hints.1", "crypto-secure-defaults.hints.2", "crypto-secure-defaults.hints.3"})
public class SecureDefaultsAssignment extends AssignmentEndpoint {
    @PostMapping("/crypto/secure/defaults")
    @ResponseBody
    public AttackResult completed(@RequestParam String secretFileName,
                                  @RequestParam String secretText)throws NoSuchAlgorithmException{
        if (secretFileName != null && secretFileName.equals("default_secret")){
//            如果密文文件名不为空 并且 密文文件名为 default_secret
            if (secretText!= null && HashingAssignment.getHash(secretText,"SHA-256").equalsIgnoreCase("34de66e5caf2cb69ff2bebdc1f3091ecf6296852446c718e38ebfa60e4aa75d2")){
//                如果密文不为空 并且经过SHA-256 加密后与字符串相同
                return success(this)
                        .feedback("crypto-secure-defaults.success")
                        .build();
            }else {
                return failed(this).feedback("crypto-secure-defaults.messagenotok").build();
            }
        }
        return failed(this).feedback("crypto-secure-defaults.notok").build();
    }
}
