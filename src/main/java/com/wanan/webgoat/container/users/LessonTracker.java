package com.wanan.webgoat.container.users;


import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.lessons.Lesson;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
//映射到数据库
public class LessonTracker {
    @Id
//      用于声明一个实体类的属性映射为数据库的主键列,该属性通常置于属性语句之前,可与声明语句同行,也可写在单独行上面
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private String lessonName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Assignment> solvedAssignments = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Assignment> allAssignments = new HashSet<>();
    @Getter
    private int numberOfAttempts = 0;
    @Version
    private Integer version;

    private LessonTracker(){

    }

    public LessonTracker(Lesson lesson) {
        lessonName = lesson.getId();
        allAssignments.addAll(lesson.getAssignments() == null ? List.of() : lesson.getAssignments());
    }

    public Optional<Assignment> getAssignment(String name) {
        return allAssignments.stream().filter(a -> a.getName().equals(name)).findFirst();
    }
    public void assignmentSolved(String solvedAssignment) {
        getAssignment(solvedAssignment).ifPresent(solvedAssignments::add);
    }
    public boolean isLessonSolved() {
        return allAssignments.size() == solvedAssignments.size();
    }
    public void incrementAttempts() {
        numberOfAttempts++;
    }

    void reset() {
        solvedAssignments.clear();
    }

    public Map<Assignment, Boolean> getLessonOverview() {
        List<Assignment> notSolved = allAssignments.stream()
                .filter(i -> !solvedAssignments.contains(i))
                .toList();
        Map<Assignment, Boolean> overview = notSolved.stream().collect(Collectors.toMap(a -> a, b -> false));
        overview.putAll(solvedAssignments.stream().collect(Collectors.toMap(a -> a, b -> true)));
        return overview;
    }
}
