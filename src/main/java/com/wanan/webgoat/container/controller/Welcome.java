package com.wanan.webgoat.container.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//Controller 该类代表控制器(控制层 表现层)
public class Welcome {
    private static final String WELCOMED = "welcomed";

    @GetMapping(path = {"welcome.mvc"})
    public ModelAndView welcome(HttpServletRequest request){
        HttpSession session = request.getSession();
//        获取httpsession对象
        if (session.getAttribute(WELCOMED) == null){
//            如果获取到的webcomed是空就进行赋值
            session.setAttribute(WELCOMED,"true");
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("forward:/attack?start=true");
//        这里也是进行请求转发到/attack?start=true
        return model;
    }

}
