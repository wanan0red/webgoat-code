package com.wanan.webgoat.lessons.insecure_login;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InsecureLoginTask extends AssignmentEndpoint {
    @PostMapping("/InsecureLogin/task")
    @ResponseBody
    public AttackResult completed(@RequestParam String username,@RequestParam String password){
        if ("CaptainJack".equals(username) && "BlackPearl".equals(password)){
            return success(this).build();
        }
        return failed(this).build();
    }
    @PostMapping("/InsecureLogin/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void login(){

    }
}
