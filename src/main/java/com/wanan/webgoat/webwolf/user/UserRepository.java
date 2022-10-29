package com.wanan.webgoat.webwolf.user;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<WebGoatUser,String> {
//   JpaRepository 主要是用来查询的
    WebGoatUser findByUsername(String username);

}
