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
@AssignmentHints(value = {"SqlStringInjectionHint.10.1", "SqlStringInjectionHint.10.2", "SqlStringInjectionHint.10.3", "SqlStringInjectionHint.10.4", "SqlStringInjectionHint.10.5", "SqlStringInjectionHint.10.6"})
public class SqlInjectionLesson10 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;


    public SqlInjectionLesson10(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack10")
    @ResponseBody
    public AttackResult completed(@RequestParam String action_string){
        return injectableQueryAvailability(action_string);
    }
    protected AttackResult injectableQueryAvailability(String action){
        StringBuffer output = new StringBuffer();
        String query = "SELECT * FROM access_log WHERE action LIKE '%"+action +"%'";
        try (Connection connection = dataSource.getConnection()){
            try {
                Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.getStatement() != null){
                    resultSet.first();
                    output.append(SqlInjectionLesson8.generateTable(resultSet));
                    return failed(this).feedback("sql-injection.10.entries").output(output.toString()).build();
                }else {
                    if (tableExists(connection)){
                        return failed(this).feedback("sql-injection.10.entries").output(output.toString()).build();
                    }else {
                        return success(this).feedback("sql-injection.10.success").build();
                    }
                }
            }catch (SQLException e){
                if (tableExists(connection)){
                    return failed(this).output("<span class='feedback-negative'>"+e.getMessage()+"</span><br>"+output.toString()).build();
                }else {
                    return success(this).feedback("sql-injection.10.success").build();
                }
            }

        }catch (Exception e){
            return failed(this).output("<span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }
    private boolean tableExists(Connection connection){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM access_log");
            int cols = resultSet.getMetaData().getColumnCount();
            return (cols > 0);
        }catch (SQLException e){
            String eMessage = e.getMessage();
            if (eMessage.contains("object not found: ACCESS_LOG")){
                return false;
            }else {
                System.err.println(e.getMessage());
                return false;
            }
        }
    }
}
