package com.wanan.webgoat.container.lessons;






import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Lesson {
    private static int count = 1;
    private Integer id = null;
    private List<Assignment> assignments;

    public Lesson() {
        id =++count;
    }
    public String getName(){
        String className = getClass().getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }
//    返回本类名
    public Category getCategory(){
        return getDefaultCategory();
    }

    protected abstract Category getDefaultCategory();

    public abstract String getTitle();

    protected String getPath(){
        return "#lesson/";
    }
    public String getLink(){
        return String.format("%s%s.lesson",getPath(),getId());
    }
    public final String getId(){
        return this.getClass().getSimpleName();
    }
    public String toString(){return getTitle();}
    public final String getPackage(){
        var packageName  = this.getClass().getPackageName();
        return packageName.replaceAll("com.wanan.webgoat.lessons.","").replaceAll("\\..*","");
    }


}
