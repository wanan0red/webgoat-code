package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AssignmentHints(value = {"xss-mitigation-4-hint1"})
public class CrossSiteScriptingLesson4 extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/attack4")
    @ResponseBody
    public AttackResult completed(@RequestParam String editor2){
        String editor =editor2.replaceAll("\\<.*?>","");
//        进行了过滤
        log.debug(editor);
        if ((editor.contains("Policy.getInstance(\"antisamy-slashdot.xml\"") || editor.contains(".scan(newComment, \"antisamy-slashdot.xml\"") || editor.contains(".scan(newComment, new File(\"antisamy-slashdot.xml\")")) &&
                editor.contains("new AntiSamy();") &&
                editor.contains(".scan(newComment,") &&
                editor.contains("CleanResults") &&
                editor.contains("MyCommentDAO.addComment(threadID, userID") &&
                editor.contains(".getCleanHTML());")){
            log.debug("true");
            return  success(this).feedback("xss-mitigation-4-success").build();
        }else {
            log.debug("false");
            return failed(this).feedback("xss-mitigation-4-failed").build();
        }
    }
}
