package com.wanan.webgoat.lessons.bypass_restrictions;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BypassRestrictionsFrontendValidation extends AssignmentEndpoint {

    @PostMapping("/BypassRestrictions/frontendValidation")
    @ResponseBody
    public AttackResult completed(@RequestParam String field1, @RequestParam String field2, @RequestParam String field3, @RequestParam String field4, @RequestParam String field5, @RequestParam String field6, @RequestParam String field7, @RequestParam Integer error){
        final String regex1 = "^[a-z]{3}$";
//        匹配 以A-Z开头总共三位并且是以这个开头和结尾的字符 类似 abc
        final String regex2 = "^[0-9]{3}$";
//        数字
        final String regex3 = "^[a-zA-Z0-9]*$";
//        匹配以 a-z A-Z 0-9 开头结尾的0个或多个字符
        final String regex4 = "^(one|two|three|four|five|six|seven|eight|nine)$";
//        匹配以 这些中一个为开头结尾的字符
        final String regex5 = "^\\d{5}$";
//        匹配以 数字开头结尾的5个字符
        final String regex6 = "^\\d{5}(-\\d{4})?$";
//        匹配类似 12345-1234 或 12345 的字符
        final String regex7 = "^[2-9]\\d{2}-?\\d{3}-?\\d{4}$";
//        匹配类似 233-333-4444 233-3334444 2333334444 这样的字符
        if (error > 0){
            return failed(this).build();
        }
        if (field1.matches(regex1)){
            return failed(this).build();
        }
        if (field2.matches(regex2)){
            return failed(this).build();
        }
        if (field3.matches(regex3)){
            return failed(this).build();
        }
        if (field4.matches(regex4)){
            return failed(this).build();
        }
        if (field5.matches(regex5)){
            return failed(this).build();
        }
        if (field6.matches(regex6)){
            return failed(this).build();
        }
        if (field7.matches(regex7)){
            return failed(this).build();
        }
        return success(this).build();

    }
}
