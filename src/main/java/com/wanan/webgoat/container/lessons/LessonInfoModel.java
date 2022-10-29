package com.wanan.webgoat.container.lessons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LessonInfoModel {
    private String lessonTitle;
    private boolean hasSource;
    private boolean hasSolution;
    private boolean hasPlan;
}
