package com.wanan.webgoat.lessons.xss.stored;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.headius.invokebinder.transform.Collect;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.util.*;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
public class StoredXssComments extends AssignmentEndpoint {
    @Autowired
    private WebSession webSession;
    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-mm-dd, HH:mm:ss");
//    创建时间格式化对象
    private static final Map<String , List<Comment>> userComments = new HashMap<>();
//    用于存放用户评论的map
    private static final List<Comment> comments = new ArrayList<>();
//    存放评论的list
    private static final String phoneHomeString = "<script>webgoat.customjs.phoneHome()</script>";
//    这个是作为用户通关的条件
    static {
        comments.add(new Comment("secUriTy", DateTime.now().toString(fmt),"<script>console.warn(' unit test me')</script>Comment for Unit Testing"));
        comments.add(new Comment("webgoat",DateTime.now().toString(fmt),"This comment is safe"));
        comments.add(new Comment("guest",DateTime.now().toString(fmt),"This one is safe too."));
        comments.add(new Comment("guest",DateTime.now().toString(fmt),"Can you post a comment, calling webgoat.customjs.phoneHome() ?"));
    }
    @GetMapping(path = "/CrossSiteScripting/stored-xss",produces = MediaType.APPLICATION_JSON_VALUE,consumes = ALL_VALUE)
    @ResponseBody
    public Collection<Comment> retrieveComments(){
//        检索信息
        List<Comment> allComments = Lists.newArrayList();
        Collection<Comment> newComments = userComments.get(webSession.getUserName());
//        获取当前用户的所有评论
        allComments.addAll(comments);
//        追加comments所有元素到allComments中去
        if (newComments != null) {
            allComments.addAll(newComments);
//            追加用户添加的评论
        }
        Collections.reverse(allComments);
//        逆序评论
        return allComments;
    }

    @PostMapping("/CrossSiteScripting/stored-xss")
    @ResponseBody
    public AttackResult createNewComment(@RequestBody String commentStr){
        Comment comment = parseJson(commentStr);
//        将评论反序列化回对象
        List<Comment> comments = userComments.getOrDefault(webSession.getUserName(),new ArrayList<>());
//        获取当前用户所有评论
        comment.setDateTime(DateTime.now().toString(fmt));

        comment.setUser(webSession.getUserName());
        comments.add(comment);
        userComments.put(webSession.getUserName(),comments);
        if (comment.getText().contains(phoneHomeString)){
            return success(this).feedback("xss-stored-comment-success").build();
        }else {
            return failed(this).feedback("xss-stored-comment-failure").build();
        }

    }
    private Comment parseJson(String comment ){
        ObjectMapper mapper = new ObjectMapper();
//        新建一个对象映射器
        try {
            return mapper.readValue(comment,Comment.class);
//            对这个comment 进行读值
        } catch (JsonProcessingException e) {
            return new Comment();
        }
    }
}
