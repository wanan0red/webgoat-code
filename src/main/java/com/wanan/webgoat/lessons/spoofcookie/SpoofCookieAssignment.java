package com.wanan.webgoat.lessons.spoofcookie;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.spoofcookie.encoders.EncDec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class SpoofCookieAssignment extends AssignmentEndpoint {
    private static final String COOKIE_NAME = "spoof_auth";
//    定义cookie的名字
    private static final String COOKIE_INFO = "Cookie details for user %s:<br />" + COOKIE_NAME + "=%s";
//    作为cookie的标准化形式 用于以后的格式化输出入
    private static final String ATTACK_USERNAME = "tom";
//
    private static final Map<String ,String > users = Map.of(
            "webgoat","webgoat",
            "admin","admin",
            ATTACK_USERNAME,"apasswordfortom");
//          定义users 用户 map
    @PostMapping(path = "/SpoofCookie/login")
    @ResponseBody
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
//  没太理解 当不满足下面的请求结构统一抛异常出去?
    public AttackResult login(
            @RequestParam String username,
//            获取用户参数
            @RequestParam String password,
//
            @CookieValue(value = COOKIE_NAME , required = false ) String cookieValue,
//            获取 cookie的值 非必须
            HttpServletResponse response){
        if (StringUtils.isEmpty(cookieValue)){
//            如果cookie是空的 就到验证用户登录的地方
            return credentialsLoginFlow(username,password,response);
        }else{
//            如果存在cookie就去验证cookie
            return cookieLoginFlow(cookieValue);
        }
    }

    @GetMapping(path = "/SpoofCookie/cleanup")
    public void cleanup(HttpServletResponse response){
//        这里是删除cookie
        Cookie cookie = new Cookie(COOKIE_NAME,"");
//      先给一个空cookie
        cookie.setMaxAge(0);
//        设置cookie期限是0 也就是立即结束
        response.addCookie(cookie);
//        添加cookie
    }

    private AttackResult credentialsLoginFlow(String username,String password,HttpServletResponse response){
        String lowerCasedUsername = username.toLowerCase();
//        获取用户名的小写
        if (ATTACK_USERNAME.equals(lowerCasedUsername) && users.get(lowerCasedUsername).equals(password)){
//            如果登录的用户名是 tom  并且 得到的用户名 与 password相同
            return informationMessage(this).feedback("spoofcookie.cheating").build();
//            返回 不要作弊?
        }
        String authPassword = users.getOrDefault(lowerCasedUsername,"");
//        如果 如果 users中存在这个键 就返回这个键的值 如果不存在就返回空
        if (!authPassword.isBlank() && authPassword.equals(password)){
//            如果密码不存在空白字符 并且 给的密码等于users中的密码
            String newCookieValue = EncDec.encode(lowerCasedUsername);
//            就加密下cookie
            Cookie newCookie = new Cookie(COOKIE_NAME,newCookieValue);
//            新建一个cookie
            newCookie.setPath("/WebGoat");
//            设置cookie路径
            newCookie.setSecure(true);
//            通过安全协议发送
            response.addCookie(newCookie);
//            在响应上面添加cookie
            return informationMessage(this)
                    .feedback("spoofcookie.login")
//                    返回已经得到cookie
                    .output(String.format(COOKIE_INFO,lowerCasedUsername,newCookie.getValue()))
//                    输出cookie的值
                    .build();
        }
        return informationMessage(this)
                .feedback("spoofcookie.wrong-login")
//                返回登录失败
                .build();

    }
    private AttackResult cookieLoginFlow(String cookieValue){
        String cookieUsername;
        try{
            cookieUsername = EncDec.decode(cookieValue).toLowerCase();
//            解密cookie

        }catch (Exception e){
            return failed(this).output(e.getMessage()).build();
        }
        if (users.containsKey(cookieUsername)){
//            如果users中包含 cookie中的username
            if (cookieUsername.equals(ATTACK_USERNAME)){
//                如果cookiename等于 tom
                return success(this).build();
            }
            return failed(this).feedback("spoofcookie.cookie-login")
//                    返回使用cookie登录
                    .output(String.format(COOKIE_INFO,cookieUsername,cookieValue)).build();
        }
        return failed(this).feedback("spoofcookie.wrong-cookie").build();
//        返回cookie错误
    }
}
