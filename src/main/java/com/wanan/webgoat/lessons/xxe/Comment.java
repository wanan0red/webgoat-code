package com.wanan.webgoat.lessons.xxe;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
// 类与xml文件进行映射
@ToString
public class Comment {
    private String user;
    private String dateTime;
    private String text;
}
