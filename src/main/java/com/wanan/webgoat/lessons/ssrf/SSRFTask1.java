package com.wanan.webgoat.lessons.ssrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"ssrf.hint1", "ssrf.hint2"})
public class SSRFTask1 extends AssignmentEndpoint {
    @PostMapping("/SSRF/task1")
    @ResponseBody
    public AttackResult completed(@RequestParam String url){
        return stealTheCheese(url);
    }
    protected AttackResult stealTheCheese(String url){
        try {
            StringBuffer html = new StringBuffer();
            if (url.matches("images/tom.png")){
                html.append("<img class=\"image\" alt=\"Tom\" src=\"images/tom.png\" width=\"25%\" height=\"25%\">");
                return failed(this)
                        .feedback("ssrf.tom")
                        .output(html.toString())
                        .build();
            } else if (url.matches("images/jerry.png")) {
                html.append("<img class=\"image\" alt=\"Jerry\" src=\"images/jerry.png\" width=\"25%\" height=\"25%\">");
                return success(this)
                        .feedback("ssrf.success")
                        .output(html.toString())
                        .build();
            }else {
                html.append("<img class=\"image\" alt=\"Silly Cat\" src=\"images/cat.jpg\">");
                return failed(this)
                        .feedback("ssrf.failure")
                        .output(html.toString())
                        .build();
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed(this)
                    .output(e.getMessage())
                    .build();
        }
    }
}
