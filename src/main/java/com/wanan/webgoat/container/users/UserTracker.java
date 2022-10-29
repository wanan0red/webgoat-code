package com.wanan.webgoat.container.users;

import com.wanan.webgoat.container.lessons.Assignment;
import com.wanan.webgoat.container.lessons.Lesson;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
//启动日志
@Entity
//是指这个类映射到数据库中
public class UserTracker {
    @Id
//    用于声明一个实体类的属性映射为数据库的主键列,该属性通常置于属性语句之前,可与声明语句同行,也可写在单独行上面
    @GeneratedValue
//    公司开发使用Mysql数据库，生产使用Oracle数据库，当同时使用两种数据库时，JPA主键生成策略可以选择GenerationType.Auto来实现。
    private Long id;
    @Column(name="username")
    private String user;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
// @OneToMany 一对多关联映射   @Cascade：相当于set标签的cascade属性 fetch: 加载类型，有lazy和eager两种
    private Set<LessonTracker> lessonTrackers = new HashSet<>();
    public UserTracker(){

    }
    public UserTracker(final String user){
        this.user = user;
    }
    public LessonTracker getLessonTracker(Lesson lesson){
        Optional<LessonTracker> lessonTracker  = lessonTrackers
                .stream().filter(l->l.getLessonName().equals(lesson.getId())).findFirst();
        if (!lessonTracker.isPresent()){
//            如果lessonTracker存在
            LessonTracker newLessonTracker = new LessonTracker(lesson);
            lessonTrackers.add(newLessonTracker);
            return newLessonTracker;
        }else {
            return lessonTracker.get();
        }
    }
    public Optional<LessonTracker> getLessonTracker(String id){
        return lessonTrackers.stream().filter(l->l.getLessonName().equals(id)).findFirst();
    }
    public void assignmentSolved(Lesson lesson,String assignmentName){
        LessonTracker lessonTracker = getLessonTracker(lesson);
        lessonTracker.incrementAttempts();
        lessonTracker.assignmentSolved(assignmentName);

    }
    public void assignmentFailed(Lesson lesson){
        LessonTracker lessonTracker= getLessonTracker(lesson);
        lessonTracker.incrementAttempts();
    }
    public void reset(Lesson al){
        LessonTracker lessonTracker = getLessonTracker(al);
        lessonTracker.reset();
    }
    public int numberOfLessonsSolved(){
        int numberOfLessonsSolved = 0;
        for (LessonTracker lessonTracker:lessonTrackers){
            if (lessonTracker.isLessonSolved()){
                numberOfLessonsSolved = numberOfLessonsSolved+1;

            }
        }
        return numberOfLessonsSolved;
    }
    public int numberOfAssignmentsSolved(){
        int numberOfAssignmentsSolved = 0;
        for (LessonTracker lessonTracker : lessonTrackers){
            Map<Assignment,Boolean> lessonOverview = lessonTracker.getLessonOverview();
            numberOfAssignmentsSolved = lessonOverview.values().stream().filter(b->b).collect(Collectors.counting()).intValue();
        }
        return numberOfAssignmentsSolved;
    }
}
