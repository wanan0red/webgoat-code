package com.wanan.webgoat.webwolf.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

@Controller
@Slf4j
@RequestMapping("/landing/**")
//获取所有带有landing请求
public class LandingPage {
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.DELETE,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
//    多种请求方法
    public Callable<ResponseEntity<?>> ok(HttpServletRequest request){
        return ()->{
            log.trace("Incoming request for: {}",request.getRequestURI());
//            当日志级别为trace时会打印这个日志
            return ResponseEntity.ok().build();
        };
    }

}
