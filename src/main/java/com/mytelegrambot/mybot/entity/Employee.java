package com.mytelegrambot.mybot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.print.attribute.standard.DateTimeAtCreation;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    private int id;
    @Column(name="chat_id")
    private String chatId;
    @Column(name="city")
    private String city;
    @Column(name="date")
    private Date date;
    @Column(name="full_name")
    private String fullName;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="position")
    private String position;
    @Column(name="text")
    private String text;
    @Column(name="user_name")
    private String userName;
    @Column(name="work_place")
    private String workPlace;


}
