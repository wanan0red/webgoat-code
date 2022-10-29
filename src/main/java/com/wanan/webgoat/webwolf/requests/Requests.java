package com.wanan.webgoat.webwolf.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTrace.Request;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import static java.util.stream.Collectors.toList;
@Controller
// 该类代表控制类 控制层 响应层
@RequiredArgsConstructor
//写在类上可以代替@Autowired注解,需要注意的是在注入时需要用final定义,或者使用@notnull注解
@Slf4j
//日志
@RequestMapping(value = "/requests")
//对应请求的路径
public class Requests {
    private final WebWolfTraceRepository traceRepository;
//    这里的traceRepository很重要 我们先去看看WebWolfTraceRepository
    private final ObjectMapper objectMapper;
    @AllArgsConstructor
//    生成有参构造
    @Getter
    private class Tracert{
        private final Instant date;
        private final String path;
        private final String json;
    }
    @GetMapping
    public ModelAndView get(){
        var model = new ModelAndView("requests");
        var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        这里是获取当前的用户
        var traces = traceRepository.findAllTraces().stream()
//                这里可能有点难以理解 刚开始我不知道这个t是如何去赋值的
                .filter(t -> allowedTrace(t, user))
                .map(t -> new Tracert(t.getTimestamp(), path(t), toJsonString(t))).collect(toList());
        model.addObject("traces",traces);
        return model;


    }
    private boolean allowedTrace(HttpTrace t, UserDetails user){
        Request req = t.getRequest();
        boolean allowed = true;
        if (req.getUri().getPath().contains("/files") && !req.getUri().getPath().contains(user.getUsername())) {
//            如果包含files 并且不包含username 就不许包含
            allowed = false;
        } else if (req.getUri().getPath().contains("/landing") && req.getUri().getQuery()!=null && req.getUri().getQuery().contains("uniqueCode") && !req.getUri().getQuery().contains(StringUtils.reverse(user.getUsername()))) {
            allowed = false;
        }
        return allowed;
    }
    private String path(HttpTrace t){
        return (String) t.getRequest().getUri().getPath();
    }
    private String toJsonString(HttpTrace trace){
        try{
            return objectMapper.writeValueAsString(trace);
        }catch (JsonProcessingException e){
            log.error("Unable to create json",e);
        }
        return "No request(s) found";
    }
}
