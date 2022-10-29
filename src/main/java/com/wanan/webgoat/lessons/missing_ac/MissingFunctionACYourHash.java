package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_ADMIN;
import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_SIMPLE;


@RestController
@AssignmentHints({"access-control.hash.hint1", "access-control.hash.hint2", "access-control.hash.hint3", "access-control.hash.hint4", "access-control.hash.hint5"})
@RequiredArgsConstructor
public class MissingFunctionACYourHash extends AssignmentEndpoint {
    private final MissingAccessControlUserRepository userRepository;
//      这里创建一个与数据库交互的类
    @PostMapping(path = "/access-control/user-hash",produces = {"application/json"})
    @ResponseBody
    public AttackResult simple(String userHash){
        User user = userRepository.findByUsername("Jerry");
//        通过username 获取 user用户
        DisplayUser displayUser = new DisplayUser(user,PASSWORD_SALT_SIMPLE);
//        获取一个用户
        if (userHash.equals(displayUser.getUserHash())){
//            如果hash相等
            return success(this).feedback("access-control.hash.success").build();
        }else {
            return failed(this).build();
        }
    }

}
