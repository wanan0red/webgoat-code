package com.wanan.webgoat.container.assignments;

import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.UserTracker;
import com.wanan.webgoat.container.users.UserTrackerRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class LessonTrackerInterceptor implements ResponseBodyAdvice<Object> {
    private UserTrackerRepository userTrackerRepository;
    private WebSession webSession;
    public LessonTrackerInterceptor(UserTrackerRepository userTrackerRepository,WebSession webSession){
        this.userTrackerRepository = userTrackerRepository;
        this.webSession = webSession;
    }
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> clazz){
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse){
        if (o instanceof AttackResult attackResult){
            trackProgress(attackResult);
        }
        return o;
    }
    protected AttackResult trackProgress(AttackResult attackResult){
        UserTracker userTracker = userTrackerRepository.findByUser(webSession.getUserName());
        if (userTracker == null){
            userTracker = new UserTracker(webSession.getUserName());
        }
        if (attackResult.assignmentSolved()){
            userTracker.assignmentSolved(webSession.getCurrentLesson(),attackResult.getAssignment());
        }else {
            userTracker.assignmentFailed(webSession.getCurrentLesson());
        }
        userTrackerRepository.saveAndFlush(userTracker);
        return attackResult;
    }
}
