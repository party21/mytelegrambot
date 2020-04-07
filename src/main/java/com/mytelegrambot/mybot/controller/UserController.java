package com.mytelegrambot.mybot.controller;

import com.mytelegrambot.mybot.KnownChatIds;
import com.mytelegrambot.mybot.MyAmazingBot;
import com.mytelegrambot.mybot.MybotApplication;
import com.mytelegrambot.mybot.TelegramConn;
import com.mytelegrambot.mybot.dto.StatusAndUser;
import com.mytelegrambot.mybot.entity.Employee;
import com.mytelegrambot.mybot.repository.StatusRepository;
import com.mytelegrambot.mybot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private  StatusRepository statusRepository;
    @Autowired
    private KnownChatIds userInfo;

    MyAmazingBot bot;
    TelegramConn telegramConn;

    @GetMapping("/findphone")
    public List<?> findAllActiveUsersNative(@RequestParam(name="phone") String phoneNumber) {

        //List<User> myInv = userRepository.findPhoneNumber(phoneNumber); //From your method
        List<Employee> myInv = employeeRepository.findAll();
        //List<User> myInv = userInfo.getChatIds();
        System.out.println("from database "+myInv);
        List<StatusAndUser> myInvoiceDTO = new ArrayList<>();

        myInv.forEach(el -> {
            System.out.println(el.getPhoneNumber());
        });


        return myInv;
    }

    @GetMapping("/sendOneMessage")
    public List<Employee> findOneM() {

        List<Employee> myInv = employeeRepository.findAll();


        return myInv;
    }

    private void sendOneMessage(String chatId) {
        /* if (chatId.getChatIds().isEmpty()) {
            log.error("No chat id present. Please start a conversation with the bot and type in the configured token.");
            return;
        }  */
        try {
            bot.execute(new SendMessage(chatId, "Это работает"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        /* chatIds.getChatIds()
                .forEach(id -> {
                    try {
                        bot.execute(new SendMessage(id, text));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }); */
    }

}
