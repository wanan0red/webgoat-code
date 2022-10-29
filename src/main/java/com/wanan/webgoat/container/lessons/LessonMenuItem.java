package com.wanan.webgoat.container.lessons;

import java.util.ArrayList;
import java.util.List;

public class LessonMenuItem {
    private String name;
//这个名字其实就是在Category类中的"Introduction"
    private LessonMenuItemType type;
//    这里的类型其实是LessonMenuItemType枚举类型 其中CATEGORY代表一级标题类型 LESSON代表二级标题有几个科目
    private List<LessonMenuItem> children = new ArrayList<>();
//    简单来说就是二级题目
    private boolean complete;
//    是否完成整个一级标签项目 对应着左边栏的绿色小对号
    private String link;
//    链接地址
    private int ranking;

    public String getName(){
        return name;
    }
    public void  setName(String name){
        this.name = name;
    }
    public  List<LessonMenuItem> getChildren(){
        return children;
    }
    public void setChildren(List<LessonMenuItem> children){
        this.children = children;
    }
    public LessonMenuItemType getType(){
        return type;
    }
    public void setType(LessonMenuItemType type){
        this.type = type;
    }
    public void addChild(LessonMenuItem child){
        children.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name).append(" | ");
        builder.append("Type: ").append(name).append(" | ");
        return builder.toString();
    }
    public boolean isComplete(){
        return complete;
    }
    public void setComplete(boolean complete){
        this.complete = complete;
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }
    public  void setRanking(int ranking){
        this.ranking = ranking;

    }
    public int getRanking(){
        return this.ranking;
    }
}
