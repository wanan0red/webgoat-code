package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint3-1", "SqlStringInjectionHint3-2"})
public class SqlInjectionLesson3 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson3(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack3")
    @ResponseBody
    public AttackResult completed(@RequestParam String query){
        return injectableQuery(query);
    }
    protected AttackResult injectableQuery(String query){
        try ( Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                Statement checkStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                statement.executeUpdate(query);
//                这里变成了更新语句
                ResultSet results = checkStatement.executeQuery("select * from employees where last_name='Barnett'");
                StringBuffer output = new StringBuffer();
                results.first();
                if (results.getString("department").equals("Sales")){
                    output.append("<span class='feedback-positive'>"+query + "</span>");
                    output.append(SqlInjectionLesson8.generateTable(results));
                    return success(this).output(output.toString()).build();
                }else {
                    return failed(this).output(output.toString()).build();
                }

            }catch (SQLException sqle){
                return failed(this).output(sqle.getMessage()).build();
            }

        } catch (Exception e) {
            return failed(this).output(this.getClass().getName() + " : " + e.getMessage()).build();
        }
    }
}
