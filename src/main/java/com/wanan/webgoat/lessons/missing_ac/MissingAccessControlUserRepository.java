package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.LessonDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class MissingAccessControlUserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
//    这里是使用的 框架的 jdbc工具栏 去方便的进行sql查询
    private final RowMapper<User> mapper = (rs,rowMapper) -> new User(rs.getString("username"),rs.getString("password"),rs.getBoolean("admin"));
//    这里不理解的 的看下面的图
    public MissingAccessControlUserRepository(LessonDataSource lessonDataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(lessonDataSource);
//        这里是进行 jdbc的初始化
    }
    public List<User> findAllUsers(){
        return jdbcTemplate.query("select username,password,admin from access_control_users",mapper);
//      这里是进行了数据库的查询
    }
    public User findByUsername(String username){
        var users = jdbcTemplate.query("select username,password,admin from access_control_users where username=:username",new MapSqlParameterSource().addValue("username",username),mapper);
//        这里我想的是存不存在sql注入 从这里的含义上来看是存在的 先放一下
        if (CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }
    public User save(User user){
        jdbcTemplate.update("INSERT INTO access_control_users(username, password, admin) VALUES(:username,:password,:admin)",
                new MapSqlParameterSource()
                        .addValue("username",user.getUsername())
                        .addValue("password",user.getPassword())
                        .addValue("admin",user.isAdmin()));
        return user;
    }
}
