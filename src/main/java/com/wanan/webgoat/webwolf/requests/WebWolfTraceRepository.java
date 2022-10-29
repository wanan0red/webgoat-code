package com.wanan.webgoat.webwolf.requests;

import com.google.common.collect.EvictingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebWolfTraceRepository implements HttpTraceRepository {
    private final EvictingQueue<HttpTrace> traces = EvictingQueue.create(10000);
//    最近N次执行http请求，执行时间超过阈值T的次数大于等于M，则认为当前网络慢 用于缓存
//    这里也可以看到其实这个traces其实就是记录我们的http请求的
    private final List<String> exclusionList = List.of("/tmpdir","/home","/files","/images/",
        "/favicon.ico","/js/","/webjars","/requests","/css/","/mail");
//    这里是排除列表


    @Override
    public List<HttpTrace> findAll(){
        return List.of();
    }
    public List<HttpTrace> findAllTraces(){
        return new ArrayList<>(traces);
    }
    private boolean isInExclusionList(String path){
        return exclusionList.stream().anyMatch(e->path.contains(e));
//        这里就是去判断是否在排除列表里面
    }
    @Override
    public void add(HttpTrace httpTrace){
        var path = httpTrace.getRequest().getUri().getPath();
//        获取uri路径
        if(!isInExclusionList(path)){
            traces.add(httpTrace);
//            如果不在就添加进去
        }

    }

}
