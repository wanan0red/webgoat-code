package com.wanan.webgoat.container;

import com.wanan.webgoat.container.session.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class HammerHead {
    private final Course course;

    @RequestMapping(path = "/attack",method = {RequestMethod.GET,RequestMethod.POST})
//   接受/attack 的get和post请求
    public ModelAndView attack(){
//        视图层和数据层
        return new ModelAndView("redirect:"+"start.mvc" + course.getFirstLesson().getLink());

    }
}
