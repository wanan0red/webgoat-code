package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.lessons.Hint;
import com.wanan.webgoat.container.lessons.Lesson;
import com.wanan.webgoat.container.session.WebSession;
import org.hibernate.engine.HibernateIterator;
import org.jcodings.util.Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class HintService {
    public static final String URL_HINTS_MVC = "/service/hint.mvc";
    private final WebSession webSession;
    public HintService(WebSession webSession){
        this.webSession = webSession;
    }
    @GetMapping(path = URL_HINTS_MVC,produces = "application/json")
//    返回json数据
    @ResponseBody
    public List<Hint> getHints(){
        Lesson l = webSession.getCurrentLesson();
//        获取当前lesson
        return createAssignmentHints(l);
    }

    private List<Hint> createAssignmentHints(Lesson l ){
        if (l!= null){
            return l.getAssignments().stream()
//                    先getAssignments 后传入create
                    .map(this::createHint)
                    .flatMap(Collection::stream)
                    .toList();

        }
        return List.of();
    }
    private List<Hint> createHint(Assignment a){
        return a.getHints().stream().map(h -> new Hint(h,a.getPath())).toList();
//        这里进Hint的构建
    }
}
