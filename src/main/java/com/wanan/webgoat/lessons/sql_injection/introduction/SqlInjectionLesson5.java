package com.wanan.webgoat.lessons.sql_injection.introduction;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@AssignmentHints(value = {"SqlStringInjectionHint5-1", "SqlStringInjectionHint5-2", "SqlStringInjectionHint5-3", "SqlStringInjectionHint5-4"})
public class SqlInjectionLesson5 extends AssignmentEndpoint {
    private final LessonDataSource dataSource;

    public SqlInjectionLesson5(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
//    @PostConstruct注解的方法在项目启动的时候执行这个方法，也可以理解为在spring容器启动的时候执行，可作为一些数据的常规化加载，比如数据字典之类的。
    public void createUser() {
        try (Connection connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("create user unauthorized_user password test")){
//                postgresql 创建一个 unauthorized_user 用户密码为 test
                statement.execute();
            }
        }catch (Exception e){
//              user already exists continue
        }
    }


    @PostMapping("/SqlInjection/attack5")
    @ResponseBody
    public AttackResult completed(String query){
        createUser();
        return injectableQuery(query);

    }
    protected AttackResult injectableQuery(String query){
        try(Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)){
                statement.executeQuery(query);
                if (checkSolution(connection)){
                    return success(this).build();
                }
                return failed(this).output("Your query was: "+ query).build();
            }

        } catch (Exception e) {
            return failed(this).output(this.getClass().getName()+ " : " + e.getMessage() + "<br> Your query was: "+ query ).build();
        }
    }
    private boolean checkSolution(Connection connection){
        try {
            var stmt = connection.prepareStatement("select  * from information_schema.table_privileges where table_name = ? and grantee = ?");
//          查询是否有权限
            stmt.setString(1,"GRANT_RIGHTS");
            stmt.setString(2,"UNAUTHORIZED_USER");
            var resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }


}