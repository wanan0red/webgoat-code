package com.wanan.webgoat.container.controller;

import com.wanan.webgoat.container.session.Course;
import com.wanan.webgoat.container.session.WebSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StartLesson {
    private final WebSession ws;
    private final Course course;

    public StartLesson(WebSession ws,Course course){
        this.ws = ws;
        this.course = course;
    }

    @RequestMapping(path =  "startlesson.mvc",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView start(){
        var model = new ModelAndView();
        model.addObject("course",course);
        model.addObject("lesson",ws.getCurrentLesson());
//        设置model中存放的数据
        model.setViewName("lesson_content");
        return model;
    }
    @RequestMapping(value = {"*.lesson"},produces = "text/html")
//  produces指定返回编码
    public ModelAndView lessonPage(HttpServletRequest request){
        var model = new ModelAndView("lesson_content");
        var path = request.getRequestURI().toString();
//        获取得到的路径 现在我们得到了 /a/b/c/AccessControlMatrix.lesson
        var lessonName = path.substring(path.lastIndexOf('/')+1,path.indexOf(".lesson"));
//      获取到了名字 AccessControlMatrix
        course.getLessons()
                .stream()
                .filter(l->l.getId().equals(lessonName))
                .findFirst()
                .ifPresent(lesson -> {
                    ws.setCurrentLesson(lesson);
                    model.addObject("lesson",lesson);
                });
        return model;

    }
}
