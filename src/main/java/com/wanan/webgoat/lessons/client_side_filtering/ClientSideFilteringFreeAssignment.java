package com.wanan.webgoat.lessons.client_side_filtering;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"client.side.filtering.free.hint1", "client.side.filtering.free.hint2", "client.side.filtering.free.hint3"})
public class ClientSideFilteringFreeAssignment extends AssignmentEndpoint {
    public static final String SUPER_COUPON_CODE = "get_it_for_free";
    @PostMapping("/clientSideFiltering/getItForFree")
    @ResponseBody
    public AttackResult completed(@RequestParam String checkoutCode){
        if (SUPER_COUPON_CODE.equals(checkoutCode)){
            return success(this).build();
        }
        return failed(this).build();
    }
}
