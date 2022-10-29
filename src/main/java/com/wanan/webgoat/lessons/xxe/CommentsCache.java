package com.wanan.webgoat.lessons.xxe;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanan.webgoat.container.WebGoat;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.WebGoatUser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static java.util.Optional.empty;

@Component
@Scope("singleton")
public class CommentsCache {
    static class Comments extends ArrayList<Comment> {
        void sort() {
            sort(Comparator.comparing(Comment::getDateTime).reversed());
//            收集评论进行排序
        }
    }
    private static final Comments comments = new Comments();
    private static final Map<WebGoatUser, Comments> userComments = new HashMap<>();
    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss");
    private final WebSession webSession;

    public CommentsCache(WebSession webSession) {
        this.webSession = webSession;
        initDefaultComments();
//        添加默认的评论
    }

     void initDefaultComments() {
        comments.add(new Comment("webgoat", DateTime.now().toString(fmt), "Silly cat....") );
        comments.add(new Comment("guest", DateTime.now().toString(fmt), "I think I will use this picture in one of my projects."));
        comments.add(new Comment("guest", DateTime.now().toString(fmt), "Lol!! :-)."));
    }

    protected Comments getComments(){
        Comments allComments = new Comments();
        Comments commentsByUser = userComments.get(webSession.getUser());
        if (commentsByUser != null){
            allComments.addAll(commentsByUser);
        }

        allComments.addAll(comments);
        allComments.sort();
        return allComments;
    }
    protected Comment parseXml(String xml) throws JAXBException, XMLStreamException {
//        处理xml文件
        var jc = JAXBContext.newInstance(Comment.class);
//        生成一个用于 处理 comment 类的xml对象
        var xif = XMLInputFactory.newInstance();
//      定义xml输入流
        if (webSession.isSecurityEnable()){
            xif.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD,"");
            xif.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA,"");
        }
        var xsr = xif.createXMLEventReader(new StringReader(xml));
//        读取xml数据并生成一个xml阅读器
        var unmarshaller = jc.createUnmarshaller();
//        创建将xml数据转换成对象
        return (Comment) unmarshaller.unmarshal(xsr);
//        进行转换Comment

    }
    protected Optional<Comment> parseJson(String comment){
//        解析json数据为comment对象
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.readValue(comment,Comment.class));
        } catch (IOException e) {
            return empty();
        }
    }
    public void addComment(Comment comment,boolean visibleForAllUsers){
//        添加评论
        comment.setDateTime(DateTime.now().toString(fmt));
        comment.setUser(webSession.getUserName());
        if (visibleForAllUsers){
            comments.add(comment);
        }else {
            var comments = userComments.getOrDefault(webSession.getUserName(),new Comments());
            comments.add(comment);
            userComments.put(webSession.getUser(),comments);
        }
    }
    public void reset(WebGoatUser user){
//        重置评论
        comments.clear();
        userComments.remove(user);
        initDefaultComments();
    }
}
