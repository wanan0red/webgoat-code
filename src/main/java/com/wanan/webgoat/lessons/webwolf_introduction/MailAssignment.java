package com.wanan.webgoat.lessons.webwolf_introduction;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
//@RestController 是@controller和@ResponseBody 的结合
//@Controller 将当前修饰的类注入到springboot ioc容器,使得从该类所在的项目跑起来的过程中,这个类就被实例化
//@ResponseBody 它的作用简短的说就是指该类中的api即可返回的数据 不管对应方法返回map或者其他的object,它会以json字符串的形式返回给客户端

public class MailAssignment extends AssignmentEndpoint {
    private final String webWolfURL;
    private RestTemplate restTemplate;
//    restTemplat像一个封装好的网络请求工具类这里我们使用它来去发送url请求

    public MailAssignment(RestTemplate restTemplate, @Value("${webwolf.mail.url}") String webWolfURL){
//        这里的@Value的值可以在application-webgoat.properties中找到,其实是webwolf的email的url
        this.restTemplate =restTemplate;
        this.webWolfURL = webWolfURL;
    }
    @PostMapping("/WebWolf/mail/send")
    @ResponseBody
    public AttackResult sendEmail(@RequestParam String email){
//        获取post请求中的email参数
        String username = email.substring(0,email.indexOf("@"));
//        通过先获取@的位置接着截取@之前的值如 123@test.com 就会截取123
        if (username.equalsIgnoreCase(getWebSession().getUserName())){
//            将得到的值忽略大小写与当前用户名进行对比
            Email mailEvent = Email.builder()
//                    由于我们在email类中设置了@Data 与 @Builder注解 这样我们就可以进行链式赋值 首先调用了builder()
                    .recipient(username)
                    .title("Test messages from WebWolf")
                    .contents("This is a test message from WebWolf, your unique code is: "+ StringUtils.reverse(username))
//                    这里书写了邮箱内容 并附带了unique code
                    .sender("webgoat@owasp.org")
                    .build();

            try {
                restTemplate.postForEntity(webWolfURL,mailEvent,Object.class);
//                这里是进行了一个post网络请求 Object.class是以对象发送? 解释看下图
            }catch (RestClientException e){
                return informationMessage(this).feedback("webwolf.email_failed").output(e.getMessage()).build();
//                这里是没请求成功后的返回html的信息 这里进informationMessage进行查看
//                由于之前的messages已经获取了配置文件 ,那么这里我们之间读取配置文件并进行赋值
            }
            return  informationMessage(this).feedback("webwolf.email_send").feedbackArgs(email).build();
        }else {
            return informationMessage(this).feedback("webwolf.email_mismatch").feedbackArgs(username).build();
        }

    }
    @PostMapping("/WebWolf/mail")
    @ResponseBody
    public AttackResult completed(@RequestParam String uniqueCode){
//        首先获取post表单 uniqueCode
        if (uniqueCode.equals(StringUtils.reverse(getWebSession().getUserName()))){
//            将获取到的值进行逆序与当前用户名进行对比 为什么是逆序看上面的mailEvent 赋值操作
            return success(this).build();
//            这里如果相等就完成了这项挑战调用success
        }else {
            return failed(this).feedbackArgs("webwolf.code_incorrect").feedbackArgs(uniqueCode).build();
        }
    }


}
