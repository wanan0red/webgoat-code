package com.wanan.webgoat.lessons.webwolf_introduction;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;

import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class LandingAssignment extends AssignmentEndpoint {

    @Value("${webwolf.landingpage.url}")
    private String landingPageUrl;
//    同样的webwolf的landing的url在配置文件中可找

    @PostMapping("/WebWolf/landing")
    @ResponseBody
    public AttackResult click(String uniqueCode){
        if (StringUtils.reverse(getWebSession().getUserName()).equals(uniqueCode)){
//           如果将当前用户名字逆向对比为外部输入uniqueCode
            return success(this).build();
        }
        return failed(this).feedback("webwolf.landing_wrong").build();
    }
    @GetMapping("/WebWolf/landing/password-reset")
    public ModelAndView openPasswordReset(HttpServletRequest request) throws URISyntaxException{
        URI uri = new URI(request.getRequestURI().toString());
//        对一个url进行拆分 如http 127.0.0.1 8081 /webwolf
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("webwolfUrl",landingPageUrl);
//        添加两个model 一个url 一个 uncode
        modelAndView.addObject("uniqueCode",StringUtils.reverse(getWebSession().getUserName()));
        modelAndView.setViewName("lessons/webwolf_introduction/templates/webwolfPasswordReset.html");
//        设置视图 映射到哪一个html文件 根据路径去可以简单找到
        return modelAndView;
    }
}
