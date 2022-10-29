package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.apache.commons.exec.OS;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"xxe.hints.content.type.xxe.1", "xxe.hints.content.type.xxe.2"})
public class ContentTypeAssignment extends AssignmentEndpoint {
    private static final String[] DEFAULT_LINUX_DIRECTORIES = {"usr", "etc", "var"};
    private static final String[] DEFAULT_WINDOWS_DIRECTORIES = {"Windows", "Program Files (x86)", "Program Files", "pagefile.sys"};
    @Value("${webgoat.server.directory}")
    private String webGoatHomeDirectory;
    @Autowired
    private WebSession webSession;
    @Autowired
    private CommentsCache comments;

    @PostMapping(path = "xxe/content-type")
    @ResponseBody
    public AttackResult createNewUser(HttpServletRequest request, @RequestBody String commentStr , @RequestHeader("Content-Type")String contentType) throws Exception{
        AttackResult attackResult = failed(this).build();

        if (APPLICATION_JSON_VALUE.equals(contentType)){
            comments.parseJson(commentStr).ifPresent(c -> comments.addComment(c,true));
            attackResult = failed(this).feedback("xxe.content.type.feedback.json").build();
        }
        if (null != contentType && contentType.contains(MediaType.APPLICATION_XML_VALUE)){
            String error = "";
            try {
                Comment comment = comments.parseXml(commentStr);
                comments.addComment(comment,false);
                if (checkSolution(comment)){
                    attackResult = success(this).build();
                }
            }catch (Exception e){
                error = ExceptionUtils.getStackTrace(e);
                attackResult = failed(this).feedback("xxe.content.type.feedback.xml").output(error).build();
            }

        }
        return attackResult;
    }

    private boolean checkSolution(Comment comment) {
        String[] directoriesToCheck = OS.isFamilyMac() || OS.isFamilyUnix() ? DEFAULT_LINUX_DIRECTORIES:DEFAULT_WINDOWS_DIRECTORIES;
        boolean success = false;
        for (String directory : directoriesToCheck){
            success |= org.apache.commons.lang3.StringUtils.contains(comment.getText(),directory);
        }
        return success;

    }
}
