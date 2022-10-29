package com.wanan.webgoat.lessons.webwolf_introduction;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
// @Builder作用见下图
//可以通过链式调用的方法给类中的参数进行赋值
@Data
//作为数据类
public class Email implements Serializable {
    private String contents;
    private String sender;
    private String title;
    private String recipient;
}
