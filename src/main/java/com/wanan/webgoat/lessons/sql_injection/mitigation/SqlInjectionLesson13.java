package com.wanan.webgoat.lessons.sql_injection.mitigation;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint-mitigation-13-1", "SqlStringInjectionHint-mitigation-13-2", "SqlStringInjectionHint-mitigation-13-3", "SqlStringInjectionHint-mitigation-13-4"})
@Slf4j
public class SqlInjectionLesson13 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson13(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PostMapping("/SqlInjectionMitigations/attack12a")
    @ResponseBody
    public AttackResult completed(@RequestParam String ip){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select ip from servers where ip=? and hostname=?")){
                preparedStatement.setString(1,ip);
                preparedStatement.setString(2,"webgoat-prd");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return success(this).build();
            }
            return failed(this).build();
        }catch (SQLException e){
            log.error("Failed",e);
            return (failed(this).build());
        }
    }
}
