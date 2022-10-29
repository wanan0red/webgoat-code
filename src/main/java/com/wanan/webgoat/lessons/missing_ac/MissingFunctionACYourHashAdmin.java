package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_ADMIN;

@RestController
@AssignmentHints({"access-control.hash.hint6", "access-control.hash.hint7",
        "access-control.hash.hint8", "access-control.hash.hint9", "access-control.hash.hint10", "access-control.hash.hint11", "access-control.hash.hint12"})
public class MissingFunctionACYourHashAdmin extends AssignmentEndpoint {

    private final MissingAccessControlUserRepository userRepository;

    public MissingFunctionACYourHashAdmin(MissingAccessControlUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/access-control/user-hash-fix",produces = {"application/json"})
    @ResponseBody
    public AttackResult admin(String userHash){
        var user = userRepository.findByUsername("Jerry");
        var displayUser = new DisplayUser(user,PASSWORD_SALT_ADMIN);
//        这里需要与admin相同
        if (userHash.equals(displayUser.getUserHash())) {
            return success(this).feedback("access-control.hash.success").build();
        }else {
            return failed(this).feedback("access-control.hash.close").build();
        }

    }
}
