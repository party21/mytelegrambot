package com.mytelegrambot.mybot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.print.attribute.standard.DateTimeAtCreation;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Status {
    @Id
    private int id;
    private String userName;
    private String text;
    private Date date;
    private DateTimeAtCreation datetime;
    private long userId;
    private String userChatId;
}
