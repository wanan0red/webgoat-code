package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint.9.1", "SqlStringInjectionHint.9.2", "SqlStringInjectionHint.9.3", "SqlStringInjectionHint.9.4", "SqlStringInjectionHint.9.5"})
public class SqlInjectionLesson9 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson9(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack9")
    @ResponseBody
    public AttackResult completed(@RequestParam String name,@RequestParam String auth_tan){
        return injectableQueryIntegrity(name,auth_tan);
    }
    protected AttackResult injectableQueryIntegrity(String  name,String auth_tan){
        StringBuffer output = new StringBuffer();
        String query = "SELECT * FROM employees WHERE last_name = '"+ name + "' AND auth_tan = '" + auth_tan +"'";
        try(Connection connection = dataSource.getConnection()){
            try{
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
//                注意这里是更新语句
                SqlInjectionLesson8.log(connection,query);
                ResultSet resultSet = statement.executeQuery(query);
                var test = resultSet.getRow() != 0;
                if (resultSet.getStatement() != null){
                    if (resultSet.first()){
                        output.append(SqlInjectionLesson8.generateTable(resultSet));
                    }else {
                        return failed(this).feedback("sql-injection.8.no.results").build();
                    }
                }
            }catch (SQLException e){
                System.err.println(e.getMessage());
                return failed(this).output("<br><span class='feedback-negative'>" + e.getMessage() + "</span>").build();
            }
            return checkSalaryRanking(connection,output);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return failed(this).output("<br><span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }
    private AttackResult checkSalaryRanking(Connection connection,StringBuffer output){
        try {
            String query = "SELECT * FROM employees ORDER BY salary DESC";
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.first();
//                这里游标到达第一个
                if ((resultSet.getString(2).equals("John"))&& (resultSet.getString(3).equals("Smith"))){
//                    这里就是检测结果first_name是不是 John last_name是不是 Smith
                    output.append(SqlInjectionLesson8.generateTable(resultSet));
                    return success(this).feedback("sql-injection.9.success").output(output.toString()).build();
                }else {
                    return failed(this).feedback("sql-injection.9.one").output(output.toString()).build();
                }

            }
        }catch (SQLException e){
            return failed(this).output("<br><span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }
}
