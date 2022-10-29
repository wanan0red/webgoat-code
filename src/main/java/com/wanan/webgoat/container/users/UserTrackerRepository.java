package com.wanan.webgoat.container.users;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrackerRepository extends JpaRepository<UserTracker,String> {
    UserTracker findByUser(String user);




}
