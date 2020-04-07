package com.mytelegrambot.mybot.repository;

import com.mytelegrambot.mybot.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Integer> {

    //@Query("SELECT new com.mytelegrambot.mybot.dto.StatusAndUser(c.name , p.productName) FROM Customer c JOIN c.products p")
    //public List<StatusAndUser> getJoinInformation();
}
