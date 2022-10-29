package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint2-1", "SqlStringInjectionHint2-2", "SqlStringInjectionHint2-3", "SqlStringInjectionHint2-4"})
public class SqlInjectionLesson2 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;
    public SqlInjectionLesson2(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjection/attack2")
    @ResponseBody
    public AttackResult completed(@RequestParam String query){
//        这里传入了query参数
        return injectableQuery(query);
    }
    protected AttackResult injectableQuery(String query){
        try(var connection =  dataSource.getConnection()) {
            Statement statement = connection.createStatement(TYPE_SCROLL_INSENSITIVE,CONCUR_READ_ONLY);
//            TYPE_SCROLL_INSENSITIVE结果集的游标可以上下移动 ,当前数据库发生变化时,当前结果集不变
//            不能用结果集更新数据库中的表
            ResultSet results = statement.executeQuery(query);
//            这里对数据库的语句进行了执行
            StringBuffer output = new StringBuffer();
//            这里使用 stringBuffer 去存储信息
            results.first();
//            将光标移动到数据库的第一行
            if (results.getString("department").equals("Marketing")){
//                如果从 列名 department中获取的数据为 Marketing
                output.append("<span class='feedback-positive'>" + query + "</span>");
//                添加sql语句到html中
                output.append(SqlInjectionLesson8.generateTable(results));
//                这里是去调用 8 中的生成表的方法将结果进行 html化
                return success(this).feedback("sql-injection.2.success").output(output.toString()).build();
            }else{
                return failed(this).feedback("sql-injection.2.failed").output(output.toString()).build();
            }
        } catch (SQLException sqle) {
            return failed(this).feedback("sql-injection.2.failed")
                    .output(sqle.getMessage()).build();
        }
    }


}
