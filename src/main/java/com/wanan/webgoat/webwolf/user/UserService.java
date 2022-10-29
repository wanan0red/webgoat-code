package com.wanan.webgoat.webwolf.user;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容器中，不需要再在applicationContext.xml文件定义bean了
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public WebGoatUser loadUserByUsername(final String username) throws UsernameNotFoundException{
        WebGoatUser webGoatUser = userRepository.findByUsername(username);
//        调接口去查找用户名
        if (webGoatUser == null){
            throw new UsernameNotFoundException("User not found");
//            抛出新异常User 没找到
        }
        webGoatUser.createUser();
        return webGoatUser;
    }
    public void addUser(final String username, final String password) {
        userRepository.save(new WebGoatUser(username, password));
    }

}
