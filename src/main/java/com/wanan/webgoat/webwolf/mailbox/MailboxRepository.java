package com.wanan.webgoat.webwolf.mailbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailboxRepository extends JpaRepository<Email,String > {
    List<Email> findByRecipientOrderByTimeDesc(String recipient);


}
