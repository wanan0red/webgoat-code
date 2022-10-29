package com.wanan.webgoat.container.session;

import com.wanan.webgoat.container.lessons.Category;
import com.wanan.webgoat.container.lessons.Lesson;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Course {
    private List<Lesson> lessons;

    public Course(List<Lesson> lessons){
        this.lessons = lessons;
    }
    public List<Category> getCategories(){
        return lessons.parallelStream().map(Lesson::getCategory).distinct().sorted().toList();

    }
    public Lesson getFirstLesson(){
        return getLessons(getCategories().get(0)).get(0);

    }
    public List<Lesson> getLessons(){
        return this.lessons;
    }

    public List<Lesson> getLessons(Category category){
        return this.lessons.stream().filter(l->l.getCategory() == category).toList();
    }
    public void setLessons(List<Lesson> lessons){
        this.lessons = lessons;
    }
    public int getTotalOfLessons(){
        return this.lessons.size();
    }
    public int getTotalOfAssignments(){
        return this.lessons.stream().reduce(0,(total,lessons) -> lessons.getAssignments().size() + total,Integer::sum);
    }

}
