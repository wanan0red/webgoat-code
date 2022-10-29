package com.wanan.webgoat.lessons.ssrf;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@AssignmentHints({"ssrf.hint3"})
public class SSRFTask2 extends AssignmentEndpoint {

    @PostMapping("/SSRF/task2")
    @ResponseBody
    public AttackResult completed(@RequestParam String url){
        return fulBall(url);
    }
    protected AttackResult fulBall(String url){
        if (url.matches("http://ifconfig.pro")){
            String html;
            try (InputStream in  = new URL(url).openStream()){
//                这里比较好理解 就是初始化了一个url读入流去读取数据 这里当然会存在ssrf了
                html = new String(in.readAllBytes(), StandardCharsets.UTF_8)
                        .replace("\n","<br>");
            }catch (MalformedURLException e){
                return getFailedResult(e.getMessage());
            }catch (IOException e){
                html = "<html><body>Although the http://ifconfig.pro site is down, you still managed to solve" +
                        " this exercise the right way!</body></html>";
            }
            return success(this).feedback("ssrf.success")
                    .output(html).build();
        }
        var html = "<img class=\"image\" alt=\"image post\" src=\"images/cat.jpg\">";
        return getFailedResult(html);
    }

    private AttackResult getFailedResult(String errorMsg) {
        return failed(this).feedback("ssrf.failure")
                .output(errorMsg).build();
    }
}
