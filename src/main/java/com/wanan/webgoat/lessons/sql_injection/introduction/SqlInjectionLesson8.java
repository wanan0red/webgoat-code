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
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint.8.1", "SqlStringInjectionHint.8.2", "SqlStringInjectionHint.8.3", "SqlStringInjectionHint.8.4", "SqlStringInjectionHint.8.5"})
public class SqlInjectionLesson8 extends AssignmentEndpoint {

    private final LessonDataSource dataSource;
    public SqlInjectionLesson8(LessonDataSource dataSource){
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/attack8")
    @ResponseBody
    public AttackResult completed(@RequestParam String name,@RequestParam String auth_tan){
        return injectableQueryConfidentiality(name,auth_tan);
    }
    protected AttackResult injectableQueryConfidentiality(String name,String auth_tan){
        StringBuffer output = new StringBuffer();
        String query = "SELECT * FROM employees WHERE last_name = '" + name + "'AND auth_tan = '"+auth_tan + "'";
        try(Connection connection = dataSource.getConnection()){
            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                log(connection,query);
                ResultSet results= statement.executeQuery(query);
                if (results.getStatement() != null){
                    if (results.first()){
                        output.append(generateTable(results));
                        results.last();

                        if (results.getRow() > 1){
//                        超过一条记录
                            return success(this).feedback("sql-injection.8.success").output(output.toString()).build();
                        }else {
//                            只有一条记录
                            return failed(this).feedback("sql-injection.8.one").output(output.toString()).build();
                        }
                    }else {
//                        没有结果
                        return failed(this).feedback("sql-injection.8.no.results").build();
                    }

                }else {
                    return failed(this).build();
                }
            }catch (SQLException e){
                return failed(this).output("<br><span class='feedback-negative'>" + e.getMessage()+"</span>").build();
            }
        }catch (Exception e){
            return failed(this).output("<br><span class='feedback-negative'>"+e.getMessage()+"</span>").build();
        }
    }



    public static String generateTable(ResultSet results)throws SQLException{
        ResultSetMetaData  resultSetMetaData = results.getMetaData();
//        检索数据库中的数据
        int numColumns = resultSetMetaData.getColumnCount();
//        获取其中的列数
        results.beforeFirst();
//        将光标移动到第一行前面
        StringBuilder table = new StringBuilder();
//
        table.append("<table>");
        if (results.next()){
//          如果结果集中存在值
            table.append("<tr>");
            for (int i = 1; i < (numColumns + 1);i ++ ){
//                进行计数输出
                table.append("<tr>" + resultSetMetaData.getColumnName(i) + "</th>");
            }
            table.append("</tr>");
            results.beforeFirst();
//            这里也是重新回到第一个
            while (results.next()){
                table.append("<tr>");
                for (int i = 1;i< (numColumns + 1); i++ ){
//                    这里是对数据库中查到的数据进行输出
                    table.append("<td>" + results.getString(i) + "</td>");
                }
                table.append("</tr>");
            }
        }else {
            table.append("Query Successful; however no data was returned form this query.");

        }
        table.append("</table>");
        return (table.toString());
    }
    public static void log(Connection connection,String action){
        action =action.replace('\'','"');
        Calendar cal = Calendar.getInstance();
//        用于获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//        进行时间格式化
        String time = sdf.format(cal.getTime());
        String logQuery = "INSERT INTO access_log (time,action) VALUES ('"+time+"','"+action+"')";
        try{
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(logQuery);
//                    CONCUR_UPDATABLE 能用结果集更新表中数据
        } catch (SQLException e) {
            System.err.print(e.getMessage());
//            有颜色的输出 报错专用
        }

    }
}
