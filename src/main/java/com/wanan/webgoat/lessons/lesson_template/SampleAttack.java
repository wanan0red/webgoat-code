package com.wanan.webgoat.lessons.lesson_template;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.UserSessionData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AssignmentHints({"lesson-template.hints.1","lesson-template.hints.2","lesson-template.hints.3"})
public class SampleAttack extends AssignmentEndpoint {
    String secretValue = "secr37Value";

    @Autowired
    UserSessionData userSessionData;
    @PostMapping("/lesson-template/sample-attack")
    @ResponseBody
    public AttackResult completed(@RequestParam("param1")String param1,@RequestParam("param2")String param2){
        if (userSessionData.getValue("some-value") != null){

        }
        if (secretValue.equals(param1)){
            return success(this)
                    .output("Custom Output ...if you want, for success")
                    .feedback("lesson-template.sample-attack.success")
                    .build();
        }

        return failed(this).feedback("lesson-template.sample-attack.failure-2")
                .output("Custom output for this failure scenario, usually html that will get rendered directly ... yes, you can self-xss if you want")
                .build();
    }

    @GetMapping("/lesson-template/shop/{user}")
    @ResponseBody
    public List<Item> getItemsInBasket(@PathVariable("user")String user){
        return List.of(new Item("WG-1","WebGoat promo",12.8),new Item("WG-2","webGoat sticker",0.00));
    }
    @AllArgsConstructor
    private class Item{
        private String number;
        private String description;
        private double price;
    }
}
