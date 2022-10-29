package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.lessons.sql_injection.introduction.SqlInjectionLesson5a;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.sql.*;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint-advanced-6a-1", "SqlStringInjectionHint-advanced-6a-2", "SqlStringInjectionHint-advanced-6a-3",
        "SqlStringInjectionHint-advanced-6a-4", "SqlStringInjectionHint-advanced-6a-5"})
public class SqlInjectionLesson6a extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson6a(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjectionAdvanced/attack6a")
    @ResponseBody
    public AttackResult completed(@RequestParam String userid_6a){
        return injectableQuery(userid_6a);
    }

    public AttackResult injectableQuery(String accountName) {
        String query = "";
        try (Connection connection = dataSource.getConnection()){
            boolean usedUnion = true;
            query = "SELECT * FROM user_data WHERE last_name = '"+accountName +"'";
            if (!accountName.matches("(?i)(^[^-/*;)]*)(\\s*)UNION(.*$)")){
                usedUnion = false;
            }
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                ResultSet resultSet = statement.executeQuery(query);
                if ((resultSet != null) && (resultSet.first())){
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    StringBuffer output = new StringBuffer();
                    output.append(SqlInjectionLesson5a.wirteTable(resultSet,resultSetMetaData));
                    String appendingWhenSucceded;
                    if (usedUnion)
//                        这里分别表示使用union还是没用
                        appendingWhenSucceded = "Well done! Can you also figure out a solution, by appending a new SQL Statement?";
                    else
                        appendingWhenSucceded = "Well done! Can you also figure out a solution, by using a UNION?";
                    resultSet.last();
                    if (output.toString().contains("dave")&&output.toString().contains("passW0rD")){
//                        这里是通关条件
                        output.append(appendingWhenSucceded);
                        return success(this).feedback("sql-injection.advanced.6a.success").feedbackArgs(output.toString()).output(" Your query was : "+query).build();
                    }else {
                        return failed(this).output(output.toString()+"<br> Your query was : "+query).build();
                    }
                }else {
                    return failed(this).feedback("sql-injection.advanced.6a.no.results").output(" Your query was : " + query).build();
                }

            }catch (SQLException sqle){
                return failed(this).output(sqle.getMessage() + "<br> Your query was : " + query).build();
            }
        }catch (Exception e){
            e.printStackTrace();
            return failed(this).output(this.getClass().getName()+ " : "+e.getMessage() +"<br> Your query was : "+query).build();
        }
    }
}
