package com.wanan.webgoat.lessons.password_reset;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class PasswordResetEmail implements Serializable {
//    可序列化
    private LocalDateTime time;
    private String contents;
    private String sender;
    private String title;
    private String recipient;
}
