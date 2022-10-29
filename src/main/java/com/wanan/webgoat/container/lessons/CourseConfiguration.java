package com.wanan.webgoat.container.lessons;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.Course;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.hsqldb.lib.ArrayUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Configuration
//将想要的组件添加到容器中
public class CourseConfiguration {
    private final List<Lesson> lessons;
    private final List<AssignmentEndpoint> assignment;
    private final Map<String,List<AssignmentEndpoint>> assignmentsByPackage;

    public CourseConfiguration(List<Lesson> lessons, List<AssignmentEndpoint> assignment) {
        this.lessons = lessons;
        this.assignment = assignment;
        assignmentsByPackage = this.assignment.stream().collect(groupingBy(a ->a.getClass().getPackageName()));
    }
    @Bean
    public Course course(){
        lessons.stream().forEach(l->l.setAssignments(createAssignment(l)));
//        首先去创建任务 接着赋值任务
        return new Course(lessons);
    }

    private List<Assignment> createAssignment(Lesson lesson){
        var endpoints = assignmentsByPackage.get(lesson.getClass().getPackageName());
//        根据包名获取lesson
        if (CollectionUtils.isEmpty(endpoints)){
//            对题目是否有二级菜单的判断
            log.warn("Lesson:{} hax no endpoints, is this intentionally?",lesson.getTitle());
            return new ArrayList<>();
        }
        return endpoints.stream()
                .map(e->new Assignment(e.getClass().getSimpleName(),getPath(e.getClass()),getHints(e.getClass())))
//                第一个参数 lesson名字 HttpBasicsLesson  路径值/HttpBasics/attack1 获取提示 我们先去看getHints 这里传入的参数其实是一个我们less的类对象
                .toList();
    }

    private String getPath(Class<? extends AssignmentEndpoint> e){
        for (Method m : e.getMethods()){
            if (methodReturnTypeIsOfTypeAttackResult(m)){
                var mapping = getMapping(m);
                if (mapping != null){
                    return mapping;
                }
            }

        }
        throw new IllegalStateException("Assignment endpoint: " + e + " has no mapping like @GetMapping/@PostMapping etc," +
                "with return type 'AttackResult' or 'ResponseEntity<AttackResult>' please consider adding one");
    }
    private boolean methodReturnTypeIsOfTypeAttackResult(Method m ){
        if (m.getReturnType() == AttackResult.class){
            return true;
        }
        var genericType = m.getGenericReturnType();
        if (genericType instanceof ParameterizedType){
            return ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0] == AttackResult.class;
        }
        return false;
    }

    private String getMapping(Method m){
        String[] paths = null;
        if (m.getAnnotation(RequestMapping.class)!= null){
            paths = ArrayUtils.addAll(m.getAnnotation(RequestMapping.class).value(),m.getAnnotation(RequestMapping.class).path());
        } else if (m.getAnnotation(PostMapping.class) != null) {
            paths = ArrayUtils.addAll(m.getAnnotation(PostMapping.class).value(), m.getAnnotation(PostMapping.class).path());
        } else if (m.getAnnotation(GetMapping.class) != null) {
            paths = ArrayUtils.addAll(m.getAnnotation(GetMapping.class).value(), m.getAnnotation(GetMapping.class).path());
        } else if (m.getAnnotation(PutMapping.class) != null) {
            paths = ArrayUtils.addAll(m.getAnnotation(PutMapping.class).value(), m.getAnnotation(PutMapping.class).path());
        }
        if (paths == null){
            return null;
        }else {
            return Arrays.stream(paths).filter(path -> !"".equals(path) ).findFirst().orElse("");
        }
    }

    private List<String > getHints(Class<? extends AssignmentEndpoint > e){
        if (e.isAnnotationPresent(AssignmentHints.class)){
//            如果类上面存在AssignmentHints注解
            return List.of(e.getAnnotationsByType(AssignmentHints.class)[0].value());
//            那么就返回AssignmentHints注解的第一个参数
        }
        return Collections.emptyList();
//        否则返回空
    }

}
