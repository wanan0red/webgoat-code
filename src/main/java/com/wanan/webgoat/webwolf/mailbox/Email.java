package com.wanan.webgoat.webwolf.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
//作为数据类
@Builder
//可以链式调用的方式给赋值
@AllArgsConstructor
//生成有参构造
@Entity
//指数据映射到数据库中
@NoArgsConstructor
//生成无参构造
public class Email implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
// 主键自增
    private Long id;
    @JsonIgnore
//    在json时进行忽略
    private LocalDateTime time = LocalDateTime.now();
//    返回当时时区的时间
    @Column(length = 1024)
//    对string类型设置最大长度
    private String contents;
    private String sender;
    private String title;
    private String recipient;

    public String getSummary(){
        return "-" + this.contents.substring(0,Math.min(50,contents.length()));
//        先取contents的长度与50之中的最小值 接着进行截取
    }
    public LocalDateTime getTimestamp(){
        return time;
    }
    public String getTime(){
        return DateTimeFormatter.ofPattern("h:mm a").format(time);
//        进行时间格式化
    }
    public String getShortSender(){
        return sender.substring(0,sender.indexOf("@"));
//        进行截取
    }
}
