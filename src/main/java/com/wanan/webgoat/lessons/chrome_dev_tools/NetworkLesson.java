package com.wanan.webgoat.lessons.chrome_dev_tools;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"networkHint1","networkHint2"})
public class NetworkLesson extends AssignmentEndpoint {
    @PostMapping(value = "/ChromeDevTools/network",params = {"network_num","number"})
    @ResponseBody
    public AttackResult completed(@RequestParam String network_num,@RequestParam String number){
        if (network_num.equals(number)){
//            这里主要是判断输入的两个值是否相同
            return success(this).feedback("network.success").output("").build();
        }else {
            return failed(this).feedback("network.failed").build();
        }
    }

    @PostMapping(path = "/ChromeDevTools/network",params = "networkNum")
    @ResponseBody
    public ResponseEntity<?> ok(@RequestParam String networkNum){
        return ResponseEntity.ok().build();
    }
}
