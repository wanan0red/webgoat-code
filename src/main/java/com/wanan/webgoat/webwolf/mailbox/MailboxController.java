package com.wanan.webgoat.webwolf.mailbox;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
//@RestController 是@controller和@ResponseBody 的结合
//@Controller 将当前修饰的类注入到springboot ioc容器,使得从该类所在的项目跑起来的过程中,这个类就被实例化
//@ResponseBody 它的作用简短的说就是指该类中的api即可返回的数据 不管对应方法返回map或者其他的object,它会以json字符串的形式返回给客户端
@AllArgsConstructor
@Slf4j
public class MailboxController {

    private final MailboxRepository mailboxRepository;

    @GetMapping(value = "/mail")
    public ModelAndView mail(){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        获取当前用户名称
        ModelAndView modelAndView = new ModelAndView();
        List<Email> emails = mailboxRepository.findByRecipientOrderByTimeDesc(user.getUsername());
        if (emails != null && !emails.isEmpty()){
            modelAndView.addObject("total",emails.size());
            modelAndView.addObject("emails",emails);

        }
        modelAndView.setViewName("mailbox");
        return modelAndView;
    }
    @PostMapping(value = "/mail")
    public ResponseEntity<?> sendEmail(@RequestBody Email email){
//      获取请求体
        mailboxRepository.save(email);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
