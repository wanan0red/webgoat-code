package com.wanan.webgoat.lessons.missing_ac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    这里很明显是一个数据类
    private String username;
    private String password;
    private boolean admin;
}
