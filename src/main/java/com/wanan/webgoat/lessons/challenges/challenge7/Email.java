package com.wanan.webgoat.lessons.challenges.challenge7;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class Email implements Serializable {
    private LocalDateTime time;
    private String contents;
    private String sender;
    private String title;
    private String recipient;
}
