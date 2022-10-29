package com.wanan.webgoat.container.lessons;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
//@Componnet是用在类上，声明这个类是一个组件被spring管理起来
@Slf4j
//使用日志组件
public class LessonScanner {
    private static final Pattern lessonPattern = Pattern.compile("^.*/lessons/([^/]*)/.*$");
//            使用的是Java中的Pattern.compile函数来实现对指定字符串的截取。 匹配 aaa/lessons//aaa 为啥匹配这个? 是文件名吗
    @Getter
    private final Set<String > lessons = new HashSet<>();
//    用于存放读取到的lessons

    public LessonScanner(ResourcePatternResolver resourcePatternResolver){
        //       ResourcePatternResolver 用于解析资源文件的策略接口，其特殊的地方在于，它应该提供带有*号这种通配符的资源路径。

        try {
            var resources = resourcePatternResolver.getResources("classpath:/lessons/*/*");
            for (var resource : resources){
                var url = resource.getURL();
//                这里的url我感觉是路径的意思 其实是一个file协议去读取lessons的内容
                var matcher = lessonPattern.matcher(url.toString());
                if (matcher.matches()){
//                    如果匹配到了就添加到上面的lessons中数组中
                    lessons.add(matcher.group(1));

                }

            }
            log.debug("Found {} lessons",lessons.size());
//            打印日志看看找到了多少个lessons文件
        } catch (IOException e) {
            log.warn("No lessons found...");
//            打印lessons没有找到的日志
        }
//                加载多文件

    }
    public List<String > applyPattern(String pattern){
        return lessons.stream().map(lesson -> String.format(pattern,lesson)).toList();
//        使用stream() 将源数据—包括集合、数组等转换成流  将集合中的每一个字符进行格式化输出 将数据存储到列表中
    }
}
