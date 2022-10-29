package com.wanan.webgoat.lessons.bypass_restrictions;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BypassRestrictionsFieldRestrictions extends AssignmentEndpoint {
    @PostMapping("/BypassRestrictions/FieldRestrictions")
    @ResponseBody
    public AttackResult completed(@RequestParam String select,@RequestParam String radio,@RequestParam String checkbox,
                                  @RequestParam String shortInput,@RequestParam String readOnlyInput){
        if (select.equals("option1") || select.equals("option2")){
            return failed(this).build();
        }
        if (radio.equals("option1") || radio.equals("option2")){
            return failed(this).build();
        }
        if (checkbox.equals("on") || checkbox.equals("off")){
            return failed(this).build();
        }
        if (shortInput.length() <= 5){
            return failed(this).build();
        }
        if ("change".equals(readOnlyInput)){
            return failed(this).build();
        }
        return success(this).build();
    }

}
