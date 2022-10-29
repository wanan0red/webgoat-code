package com.wanan.webgoat.container.users;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<WebGoatUser,String> {
    WebGoatUser findByUsername(String username);
//    根据用户名去查找用户
    List<WebGoatUser> findAll();
    boolean existsByUsername(String username);

}
