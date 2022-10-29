package com.wanan.webgoat.lessons.password_reset;

import com.google.common.collect.Maps;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.password_reset.resetlink.PasswordChangeForm;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AssignmentHints({"password-reset-hint1", "password-reset-hint2", "password-reset-hint3", "password-reset-hint4", "password-reset-hint5", "password-reset-hint6"})
public class ResetLinkAssignment extends AssignmentEndpoint {
    static final String PASSWORD_TOM_9 = "somethingVeryRandomWhichNoOneWillEverTypeInAsPasswordForTom";
//    作为tom 的密码
    static final String TOM_EMAIL = "tom@webgoat-cloud.org";
//    tom 的邮箱
    static Map<String ,String > userToTomResetLink = new HashMap<>();
    static Map<String ,String > usersToTomPassword = Maps.newHashMap();
    static List<String> resetLinks = new ArrayList<>();
//    这里用于存放准备重置密码的连接

    static final String TEMPLATE = "Hi, you requested a password reset link, please use this "
            + "<a target='_blank' href='http://%s/WebGoat/PasswordReset/reset/reset-password/%s'>link</a> to reset your password."
            + "\n \n\n"
            + "If you did not request this password change you can ignore this message."
            + "\n"
            + "If you have any comments or questions, please do not hesitate to reach us at support@webgoat-cloud.org"
            + "\n\n"
            + "Kind regards, \nTeam WebGoat";

    @PostMapping("/PasswordReset/reset/login")
    @ResponseBody
    public AttackResult login(@RequestParam String password ,@RequestParam String email){
        if (TOM_EMAIL.equals(email)){
            String passwordTom = usersToTomPassword.getOrDefault(getWebSession().getUserName(),PASSWORD_TOM_9);
//            这里需要tom 的密码才能过关
            if (passwordTom.equals(PASSWORD_TOM_9)){
                return success(this).build();
            }
        }
        return failed(this).feedback("login_failed.tom").build();
    }
    @GetMapping("/PasswordReset/reset/reset-password/{link}")
    public ModelAndView resetPassword(@PathVariable(value = "link")String  link, Model model){
        ModelAndView modelAndView = new ModelAndView();
        if (ResetLinkAssignment.resetLinks.contains(link)){
//            如果存在这个重置密码的url
            PasswordChangeForm form = new PasswordChangeForm();
//            新建一个form表单对象
            form.setResetLink(link);
//            设置重置链接为link
            model.addAttribute("form",form);

            modelAndView.addObject("form",form);
//            添加进入这个表单对象
            modelAndView.setViewName("lessons/password_reset/templates/password_reset.html");
        }else {
            modelAndView.setViewName("lessons/password_reset/templates/password_link_not_found.html");

        }
        return modelAndView;
    }

    @GetMapping("/PasswordReset/reset/change-password")
    public ModelAndView illegalCall(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lessons/password_reset/templates/password_link_not_found.html");
        return modelAndView;
    }
    @PostMapping("/PasswordReset/reset/change-password")
    public ModelAndView changePassword(@ModelAttribute("form") PasswordChangeForm form, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (!org.springframework.util.StringUtils.hasText(form.getPassword())){
            bindingResult.rejectValue("password","not.empty");
        }
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("lessons/password_reset/templates/password_reset.html");
            return modelAndView;
        }
        if (!resetLinks.contains(form.getResetLink())){
            modelAndView.setViewName("lessons/password_reset/templates/password_link_not_found.html");
            return modelAndView;
        }
        if (checkIfLinkIsFromTom(form.getResetLink())){
            usersToTomPassword.put(getWebSession().getUserName(),form.getPassword());
        }
        modelAndView.setViewName("lessons/password_reset/templates/success.html");
        return modelAndView;

    }

    private boolean checkIfLinkIsFromTom(String resetLinkFromForm) {
        String resetLink = userToTomResetLink.getOrDefault(getWebSession().getUserName(),"unknown");
        return resetLink.equals(resetLinkFromForm);
    }
}
