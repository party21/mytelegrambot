package com.mytelegrambot.mybot.repository;

import com.mytelegrambot.mybot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query(
            value = "SELECT id, chat_id, city, date, full_name, phone_number, " +
                    "position, text, user_name, work_place FROM employee " +
                    "WHERE phone_number=?1",
            nativeQuery = true)
    Employee findPhoneNumber(String phone);


    @Query(
            value = "SELECT id, chat_id, city, date, full_name, phone_number, " +
                    "position, text, user_name, work_place FROM employee " +
                    "WHERE chat_id=?1",
            nativeQuery = true)
    Employee findChatId(String chatId);


    @Query(
            value = "SELECT id, chat_id, city, date, full_name, phone_number, " +
                    "position, text, user_name, work_place FROM employee " +
                    "WHERE chat_id=?1",
            nativeQuery = true)
    Employee findStatistic(String username);

    //@Modifying
    //@Query("update Employee u set u.chat_id = ?1 where u.phone_number = ?2")
    //void setUserInfoById(String chatId, String phoneNumber);


}
