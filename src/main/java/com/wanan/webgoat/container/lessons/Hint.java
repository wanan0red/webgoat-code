package com.wanan.webgoat.container.lessons;


import lombok.Value;

@Value
//@Value同样会生成getter、toString、hashCode等方法
public class Hint {
    private String hint;
    private String assignmentPath;
}
