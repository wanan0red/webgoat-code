package com.wanan.webgoat.lessons.xss;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AssignmentPath;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints(value = {"xss-mitigation-3-hint1", "xss-mitigation-3-hint2", "xss-mitigation-3-hint3", "xss-mitigation-3-hint4"})
public class CrossSiteScriptingLesson3 extends AssignmentEndpoint {
    @PostMapping("/CrossSiteScripting/attack3")
    @ResponseBody
    public AttackResult completed(@RequestParam String editor){
        String unescapedString = org.jsoup.parser.Parser.unescapeEntities(editor,true);
//        取消html 实体字符的转义
        try {
            if (editor.isEmpty())
                return failed(this).feedback("xss-mitigation-3-no-code").build();
            Document doc = Jsoup.parse(unescapedString);
//            将 html 字符解析为文档
            String[] lines = unescapedString.split("<html>");
//            以<html> 分割字符
            String include = (lines[0]);
            String fistNameElement = doc.select("body > table > tbody > tr:nth-child(1) > td:nth-child(2)").first().text();
            String lastNameElement = doc.select("body > table > tbody > tr:nth-child(2) > td:nth-child(2)").first().text();

            Boolean includeCorrect = false;
            Boolean firstNameCorrect = false;
            Boolean lastNameCorrect = false;
            if (include.contains("<%@") && include.contains("taglib") && include.contains("uri=\"https://www.owasp.org/index.php/OWASP_java_Encode_Project\"") && include.contains("%>")){
                includeCorrect = true;
            }
            if (fistNameElement.equals("${e:forHtml(param.first_name)}")) {
                firstNameCorrect = true;
            }
            if (lastNameElement.equals("${e:forHtml(param.last_name)}")){
                lastNameCorrect = true;
            }
            if (includeCorrect && firstNameCorrect && lastNameCorrect){
                return success(this).feedback("xss-mitigation-3-success").build();
            }else {
                return failed(this).feedback("xss-mitigation-3-failure").build();
            }
        }catch (Exception e){
            return failed(this).output(e.getMessage()).build();
        }
    }
}
