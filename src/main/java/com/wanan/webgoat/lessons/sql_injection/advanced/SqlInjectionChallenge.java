package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@AssignmentHints(value = {"SqlInjectionChallenge1", "SqlInjectionChallenge2", "SqlInjectionChallenge3"})
@Slf4j
public class SqlInjectionChallenge extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionChallenge(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @PutMapping("/SqlInjectionAdvanced/challenge")
    @ResponseBody
    public AttackResult registerNewUser(@RequestParam String username_reg,@RequestParam String email_reg,@RequestParam String password_reg)throws Exception{
        AttackResult attackResult = checkArguments(username_reg,email_reg,password_reg);
        if (attackResult == null){
            try (Connection connection = dataSource.getConnection()){
                String checkUserQuery = "select userid from sql_challenge_users where userid = '" + username_reg + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(checkUserQuery);
                if (resultSet.next()){
                    if (username_reg.contains("tom'")){
//                        如果 查到了 这个tom' 就成功
                        attackResult = success(this).feedback("user.exists").build();
                    }else {
                        attackResult = failed(this).feedback("user.exists").feedbackArgs(username_reg).build();
                    }
                } else {
//                    如果没这个 用户名 就插入
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sql_challenge_users VALUE (?,?,?)");
                    preparedStatement.setString(1,username_reg);
                    preparedStatement.setString(2,email_reg);
                    preparedStatement.setString(3,password_reg);
                    attackResult = success(this).feedback("user.created").feedbackArgs(username_reg).build();
                }
            }catch (SQLException e){
                attackResult = failed(this).output("Something went wrong").build();
            }
        }
        return attackResult;
    }

    private AttackResult checkArguments(String username_reg, String email_reg, String password_reg) {
        if (StringUtils.isEmpty(username_reg) || StringUtils.isEmpty(email_reg) || StringUtils.isEmpty(password_reg)){
            return failed(this).feedback("input.invalid").build();
        }
        if (username_reg.length() > 250 || email_reg.length() > 30 || password_reg.length()>30){
            return failed(this).feedback("input.invalid").build();
        }
        return null;
    }
}
