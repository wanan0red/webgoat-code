package com.wanan.webgoat.lessons.challenges.challenge1;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.challenges.Flag;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.wanan.webgoat.lessons.challenges.SolutionConstants.PASSWORD;

@RestController
public class Assignment1 extends AssignmentEndpoint {
    @PostMapping("/challenge/1")
    @ResponseBody
    public AttackResult completed(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
        boolean ipAddressKnown = true;
        boolean passwordCorrect = "admin".equals(username) && PASSWORD.replace("1234",String.format("%04d",ImageServlet.PINCODE)).equals(password);
        if (passwordCorrect && ipAddressKnown) {
            return success(this).feedback("challenge.solved").feedbackArgs(Flag.FLAGS.get(1)).build();

        }else if (passwordCorrect) {
            return failed(this).feedback("ip.address.unknown").build();
        }
        return failed(this).build();
    }
    public static boolean containsHeader(HttpServletRequest request){
        return StringUtils.hasText(request.getHeader("X-Forwarded-For"));
    }
}
