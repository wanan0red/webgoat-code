package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AssignmentHints(value = {"SqlInjectionChallengeHint1", "SqlInjectionChallengeHint2", "SqlInjectionChallengeHint3", "SqlInjectionChallengeHint4"})
public class SqlInjectionChallengeLogin extends AssignmentEndpoint {

    private final LessonDataSource dataSource;

    public SqlInjectionChallengeLogin(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/SqlInjectionAdvanced/challenge_Login")
    @ResponseBody
    public AttackResult login(@RequestParam String username_login, @RequestParam String password_login) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("select password from sql_challenge_users where userid = ? and password = ?");
            statement.setString(1, username_login);
            statement.setString(2, password_login);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return ("tom".equals(username_login)) ? success(this).build()
                        : failed(this).feedback("ResultsButNotTom").build();
            } else {
                return failed(this).feedback("NoResultsMatched").build();
            }
        }
    }
}

