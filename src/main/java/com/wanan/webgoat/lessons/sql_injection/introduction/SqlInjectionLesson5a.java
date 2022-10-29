package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint5a1"})
public class SqlInjectionLesson5a extends AssignmentEndpoint {
    private static final String EXPLANATION = "<br> Explanation: This injection works, because <span style=\"font-style: italic\">or '1' = '1'</span> "
            + "always evaluates to true (The string ending literal for '1 is closed by the query itself, so you should not inject it). "
            + "So the injected query basically looks like this: <span style=\"font-style: italic\">SELECT * FROM user_data WHERE first_name = 'John' and last_name = '' or TRUE</span>, "
            + "which will always evaluate to true, no matter what came before it.";
    private final LessonDataSource dataSource;

    public SqlInjectionLesson5a(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjection/assignment5a")
    @ResponseBody
    public AttackResult completed(@RequestParam String account, @RequestParam String operator, @RequestParam String injection) {
        return injectableQuery(account + " " + operator + " " + injection);
//        将获取到的三个请求参数进行拼接
    }

    protected AttackResult injectableQuery(String accountName) {
        String query = "";
        try (Connection connection = dataSource.getConnection()) {
            query = "SELECT * FROM user_data where first_name = 'John' and last_name= '" + accountName + "'";
//            查询语句
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ResultSet results = statement.executeQuery(query);
                if ((results != null) && (results.first())) {
//                    将光标移动到ResultSet对象的第一个
                    ResultSetMetaData resultSetMetaData = results.getMetaData();
//                    检索词对象列的属性
                    StringBuffer output = new StringBuffer();
                    output.append(wirteTable(results, resultSetMetaData));
                    results.last();

                    if (results.getRow() >= 6) {
                        return success(this).feedback("sql-injection.5a.success").output("Your query was: " + query + EXPLANATION).feedbackArgs(output.toString()).build();
                    } else {
                        return failed(this).output(output.toString() + "<br> Your query was: " + query).build();
                    }
                } else {
                    return failed(this).feedback("sql-injection.5a.no.results").output("Your query was: " + query).build();
                }
            }

        } catch (SQLException sqle) {
            return failed(this).output(sqle.getMessage() + "<br> Your query was: " + query).build();
        } catch (Exception e) {
            return failed(this).output(this.getClass().getName() + " : " + e.getMessage() + "<br> Your query was: " + query).build();
        }
    }
    public static String  wirteTable(ResultSet results,ResultSetMetaData resultSetMetaData)throws SQLException{
        int numColumns = resultSetMetaData.getColumnCount();
//        获取列数
        results.beforeFirst();
        StringBuffer t = new StringBuffer();
        t.append("<p>");
        if (results.next()){
            for (int i =1;i<(numColumns + 1);i++){
                t.append(resultSetMetaData.getColumnName(i));
                t.append(", ");
            }
            t.append("<br />");
            results.beforeFirst();
            while (results.next()){
                for (int i = 1;i<(numColumns + 1);i++){
                    t.append(results.getString(i));
                    t.append(", ");
                }
                t.append("<br />");
            }
        }else {
            t.append("Query Successful;however no data was returned from this query.");
        }
        t.append("</p>");
        return (t.toString());
    }

}
