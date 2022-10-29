package com.wanan.webgoat.lessons.client_side_filtering;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"ClientSideFilteringHint1", "ClientSideFilteringHint2", "ClientSideFilteringHint3", "ClientSideFilteringHint4"})
public class ClientSideFilteringAssignment extends AssignmentEndpoint {
    @PostMapping("/clientSideFiltering/attack1")
    @ResponseBody
    public AttackResult completed(@RequestParam String  answer){
        return "450000".equals(answer) ? success(this).feedback("assignment.solved").build():
                failed(this).feedback("ClientSideFiltering.incorrect").build();
    }
}
