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

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint4-1", "SqlStringInjectionHint4-2", "SqlStringInjectionHint4-3"})
public class SqlInjectionLesson4 extends AssignmentEndpoint {

    private final LessonDataSource dataSource;

    public SqlInjectionLesson4(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjection/attack4")
    @ResponseBody
    public AttackResult completed(@RequestParam String query) {
        return injectableQuery(query);
    }

    protected AttackResult injectableQuery(String query) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY)) {
                statement.executeUpdate(query);
                connection.commit();
                ResultSet results = statement.executeQuery("SELECT phone from employees;");
                StringBuffer output = new StringBuffer();
                // user completes lesson if column phone exists
                if (results.first()) {
                    output.append("<span class='feedback-positive'>" + query + "</span>");
                    return success(this).output(output.toString()).build();
                } else {
                    return failed(this).output(output.toString()).build();
                }
            } catch (SQLException sqle) {
                return failed(this).output(sqle.getMessage()).build();
            }
        } catch (Exception e) {
            return failed(this).output(this.getClass().getName() + " : " + e.getMessage()).build();
        }
    }
}
