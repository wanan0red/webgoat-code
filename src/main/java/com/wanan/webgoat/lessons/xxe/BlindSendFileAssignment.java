package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.WebGoat;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;

import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.users.WebGoatUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@AssignmentHints({"xxe.blind.hints.1", "xxe.blind.hints.2", "xxe.blind.hints.3", "xxe.blind.hints.4", "xxe.blind.hints.5"})
public class BlindSendFileAssignment extends AssignmentEndpoint {
    private final String webGoatHomeDirectory;
    private final CommentsCache comments;
    private final Map<WebGoatUser,String > userToFileContents = new HashMap<>();


    public BlindSendFileAssignment(@Value("${webgoat.user.directory}") String webGoatHomeDirectory, CommentsCache comments) {
        this.webGoatHomeDirectory = webGoatHomeDirectory;
        this.comments = comments;
    }
    private void createSecretFileWithRandomContents(WebGoatUser user){
        var fileContents = "WebGoat 8.0 rocks... (" + randomAlphabetic(10) + ")";
        userToFileContents.put(user,fileContents);
        File targetDirectory = new File(webGoatHomeDirectory,"/XXE/" + user.getUsername());
        if (!targetDirectory.exists()){
            targetDirectory.mkdirs();
        }
        try {
            Files.writeString(new File(targetDirectory,"secret.txt").toPath(),fileContents,UTF_8);
//            这里是在初始化时创建flag文件
        }catch (IOException e){
            log.error("Unable to write 'secret.txt' to '{}",targetDirectory);
        }

    }

    @PostMapping(path = "xxe/blind",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult addComment(@RequestBody String commentStr){
        var fileContentsForUser = userToFileContents.getOrDefault(getWebSession().getUser(),"");
        if (commentStr.contains(fileContentsForUser)){
            return success(this).build();
        }
        try {
            Comment comment = comments.parseXml(commentStr);
            if (fileContentsForUser.contains(comment.getText())){
                comment.setText("Nice try, you need to send the file to WebWolf");
            }
            comments.addComment(comment,false);
        }catch (Exception e){
            return failed(this).output(e.toString()).build();
        }
        return failed(this).build();
    }
    @Override
    public void initialize(WebGoatUser user){
        comments.reset(user);
        userToFileContents.remove(user);
        createSecretFileWithRandomContents(user);
    }
}
