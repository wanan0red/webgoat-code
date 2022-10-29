package com.wanan.webgoat.lessons.missing_ac;

import com.wanan.webgoat.container.session.WebSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_ADMIN;
import static com.wanan.webgoat.lessons.missing_ac.MissingFunctionAC.PASSWORD_SALT_SIMPLE;

@Controller
@AllArgsConstructor
@Slf4j
public class MissingFunctionACUsers {
    private final MissingAccessControlUserRepository userRepository;
    private final WebSession webSession;

    @GetMapping(path = {"access-control/users"})
    public ModelAndView listUsers() {
        ModelAndView model = new ModelAndView();
//        创建一个 model 对象
        model.setViewName("list_users");
//        设置一个list_users值
        List<User> allUsers = userRepository.findAllUsers();
//        查找所用用户
        model.addObject("numUsers", allUsers.size());
//        将用户的数量添加进去
        List<DisplayUser> displayUsers = new ArrayList<>();
//        新建一个displayUser数组
        for (User user : allUsers) {
            displayUsers.add(new DisplayUser(user, PASSWORD_SALT_SIMPLE));
//            将查到的用户填入到内存中
        }
        model.addObject("allUsers", displayUsers);
//        在model中添加进去
        return model;
    }

    @GetMapping(path = {"access-control/users"}, consumes = "application/json")
    @ResponseBody
//    接受请求参数 json
    public ResponseEntity<List<DisplayUser>> usersService() {
//      获取用户信息
        return ResponseEntity.ok(userRepository.findAllUsers().stream().map(user -> new DisplayUser(user, PASSWORD_SALT_SIMPLE)).collect(Collectors.toList()));
//        返回所有用户的信息

    }

    @GetMapping(path = {"access-control/users-admin-fix"}, consumes = "application/json")
    @ResponseBody
//    接受json参数
    public ResponseEntity<List<DisplayUser>> usersFixed() {

        var currentUser = userRepository.findByUsername(webSession.getUserName());
//        获取当前 session中的用户名的信息
        if (currentUser != null && currentUser.isAdmin()) {
//            如果当前用户不为空 并且当前用户是admin
            return ResponseEntity.ok(userRepository.findAllUsers().stream().map(user -> new DisplayUser(user, PASSWORD_SALT_ADMIN)).collect(Collectors.toList()));
//            返回所有用户的信息
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        返回禁止访问
    }

    @PostMapping(path = {"access-control/users", "access-control/users-admin-fix"}, consumes = "application/json", produces = "application/json")
//    接受json信息 产生json信息
    @ResponseBody
    public User addUser(@RequestBody User newUser) {
//        接受newUser
        try {
            userRepository.save(newUser);
//            保存增用户
            return newUser;
        } catch (Exception ex) {
            log.error("Error creating new User", ex);
            return null;
        }
    }
}
