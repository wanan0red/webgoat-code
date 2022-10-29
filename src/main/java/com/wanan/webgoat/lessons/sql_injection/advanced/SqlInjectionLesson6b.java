package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class SqlInjectionLesson6b extends AssignmentEndpoint {

    private final LessonDataSource dataSource;

    public SqlInjectionLesson6b(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjectionAdvanced/attack6b")
    @ResponseBody
    public AttackResult completed(@RequestParam String userid_6b) throws IOException {
        if (userid_6b.equals(getPassword())) {
            return success(this).build();
        } else {
            return failed(this).build();
        }
    }

    protected String getPassword() {
        String password = "dave";
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT password FROM user_system_data WHERE user_name = 'dave'";
            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet results = statement.executeQuery(query);

                if (results != null && results.first()) {
                    password = results.getString("password");
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (password);
    }
}
