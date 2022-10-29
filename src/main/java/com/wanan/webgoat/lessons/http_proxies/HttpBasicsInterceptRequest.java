package com.wanan.webgoat.lessons.http_proxies;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HttpBasicsInterceptRequest extends AssignmentEndpoint {
    @RequestMapping(path = "/HttpProxies/intercept-request",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public AttackResult completed(@RequestHeader(value = "x-request-intercepted",required = false)Boolean headerValue, @RequestParam(value = "changeMe",required = false) String paramValue, HttpServletRequest request){
//        获取请求头中的x-request-intercepted required = false表示可以有也可以没有
//        将请求参数中的changeMe 绑定为paramValue
        if (HttpMethod.POST.matches(request.getMethod())){
//            如果请求是post就失败
            return failed(this).feedback("http-proxies.intercept.failure").build();
        }
        if (headerValue != null && paramValue != null && headerValue && "Requests are tampered easily".equalsIgnoreCase(paramValue)){
//            如果headerValue 不为空 paramValue 不为空 headerValue为true 并且paramValue与字符串相等
            return success(this).feedback("http-proxies.intercept.success").build();
        }else {
            return failed(this).feedback("http-proxies.intercept.failure").build();
        }


    }
}
