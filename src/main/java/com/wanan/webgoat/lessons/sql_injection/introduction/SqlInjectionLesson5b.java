package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;

import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint5b1", "SqlStringInjectionHint5b2", "SqlStringInjectionHint5b3", "SqlStringInjectionHint5b4"})
public class SqlInjectionLesson5b extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson5b(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjection/assignment5b")
    @ResponseBody
    public AttackResult completed(@RequestParam String userid, @RequestParam String login_count, HttpServletRequest request)throws IOException{
        return injectalbeQuery(login_count,userid);
    }

    protected AttackResult injectalbeQuery(String login_count, String userid) {
        String queryString = "SELECT * FROM user_data WHERE Login_Count = ? and userid = "+userid;
//        这里只进行了一个 字符的预编译
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement query = connection.prepareStatement(queryString,TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int count = 0;
            try {
                count = Integer.parseInt(login_count);
//                将预编译语句类型规定为int 并传入 login_count参数
            }catch (Exception e){
                return failed(this).output("Could not parse: "+login_count + " to a number" + "<br> Your query was: "+queryString.replace("?",login_count)).build();
            }
            query.setInt(1,count);
//            将参数添加到sql语句中
            try {
                ResultSet results = query.executeQuery();
                if ((results != null)&& (results.first() == true)){
                    ResultSetMetaData resultSetMetaData = results.getMetaData();
                    StringBuffer output = new StringBuffer();

                    output.append(SqlInjectionLesson5a.wirteTable(results,resultSetMetaData));
                    results.last();
                    if (results.getRow() >= 6){
                        return success(this).feedback("sql-injection.5b.success").output("You query was: "+queryString.replace("?",login_count)).feedbackArgs(output.toString()).build();
                    }else {
                        return failed(this).output(output.toString() + "<br> Your query was: "+ queryString.replace("?",login_count)).build();
                    }
                }else {
                    return failed(this).feedback("sql-injection.5b.no.results").output("Your query was: "+queryString.replace("?",login_count)).build();
                }

            }catch (SQLException sqle){
                return failed(this).output(sqle.getMessage() + "<br> Your query was: "+ queryString.replace("?",login_count)).build();
            }
        }catch (Exception e){
            return failed(this).output(this.getClass().getName()+" : "+e.getMessage() + "<br> Your query was: "+ queryString.replace("?",login_count)).build();
        }
    }

}
