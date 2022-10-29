package com.wanan.webgoat.lessons.hijacksession;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.hijacksession.cas.Authentication;
import com.wanan.webgoat.lessons.hijacksession.cas.HijackSessionAuthenticationProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@AssignmentHints({
        "hijacksession.hints.1",
        "hijacksession.hints.2",
        "hijacksession.hints.3",
        "hijacksession.hints.4",
        "hijacksession.hints.5",

})
public class HijackSessionAssignment extends AssignmentEndpoint {
    private static final String COOKIE_NAME = "hijack_cookie";
    @Autowired
    HijackSessionAuthenticationProvider provider;
//    这里自动注入了

    @PostMapping(path = "/HijackSession/login")
    @ResponseBody
    public AttackResult login(@RequestParam String username, @RequestParam String password,
                              @CookieValue(value = COOKIE_NAME,required = false) String cookieValue,
                              HttpServletResponse response){
        Authentication authentication;
//        定义authentication对象
        if (StringUtils.isEmpty(cookieValue)){
//            如果cookieValue是空的
            authentication = provider.authenticate(Authentication.builder().name(username).credentials(password).build());
            setCookie(response,authentication.getId());

        }else {
            authentication = provider.authenticate(Authentication.builder().id(cookieValue).build());
        }
        if (authentication.isAuthenticated()){
//            如果已经认证了
            return success(this).build();
        }
        return failed(this).build();
    }

    private void setCookie(HttpServletResponse response,String cookieValue){
        Cookie cookie = new Cookie(COOKIE_NAME,cookieValue);
        cookie.setPath("/WebGoat");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}
