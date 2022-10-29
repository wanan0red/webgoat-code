package com.wanan.webgoat.lessons.xxe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("xxe/comments")
public class CommentsEndpoint {
    @Autowired
    private CommentsCache comments ;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<Comment> retrieveComments(){
        return comments.getComments();
    }
}
