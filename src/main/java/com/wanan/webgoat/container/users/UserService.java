package com.wanan.webgoat.container.users;


import com.wanan.webgoat.container.lessons.Initializeable;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;


@Service
//@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容器中，不需要再在applicationContext.xml文件定义bean了
@AllArgsConstructor
//生成构造方法
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserTrackerRepository userTrackerRepository;
    private final JdbcTemplate jdbcTemplate;
    //                获取JdbcTemplat实例
    private final Function<String, Flyway> flywayLessons;
    private final List<Initializeable> lessonInitializables;

    @Override
    public WebGoatUser loadUserByUsername(String username) throws UsernameNotFoundException{
        WebGoatUser webGoatUser = userRepository.findByUsername(username);
//        根据用户名查找用户
        if (webGoatUser == null ){
            throw new UsernameNotFoundException("User not found");

        }else {
            webGoatUser.createUser();
            lessonInitializables.forEach(l->l.initialize(webGoatUser));
        }
        return webGoatUser;
    }
    public void addUser(String username ,String password){
        var userAlreadyExists = userRepository.existsByUsername(username);
//        根据用户名查看是否存在用户
        var webGoatUser = userRepository.save(new WebGoatUser(username,password));
//
        if (!userAlreadyExists){
            userTrackerRepository.save(new UserTracker(username));
//            如果用户存在就不会在创建了
            createLessonsForUser(webGoatUser);

        }

    }
    private void createLessonsForUser(WebGoatUser webGoatUser){
        jdbcTemplate.execute("CREATE SCHEMA \""+webGoatUser.getUsername()+"\" authorization dba");
        //使用该实例的execute(String sql)方法执行 SQL语句创建一个以user用户名的数据库
        flywayLessons.apply(webGoatUser.getUsername()).migrate();
//      进行迁移应用

    }
    public List<WebGoatUser> getAllUsers(){return userRepository.findAll();}



}
