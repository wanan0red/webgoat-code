package com.wanan.webgoat.container.lessons;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@EqualsAndHashCode
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    场景：公司开发使用Mysql数据库，生产使用Oracle数据库，当同时使用两种数据库时，JPA主键生成策略可以选择GenerationType.Auto来实现。
    private long id;
    private String name;
    private String path;

    @Transient
    private List<String > hints;

    public Assignment() {
    }
    public Assignment(String name,String path,List<String> hints){
        if (path.equals("") || path.equals("/") || path.equals("/WebGoat/")){
//           如果path是空 path 是/ path是/WebGoat/ 就输出覆盖webgoat异常
            throw new IllegalStateException("The path of assignment '" + name + "' overrides WebGoat endpoints, please choose a path within the scope of the lesson");
        }
        this.name = name;
        this.path = path;
        this.hints = hints;
    }

}
