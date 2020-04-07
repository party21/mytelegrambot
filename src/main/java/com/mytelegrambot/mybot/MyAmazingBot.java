package com.mytelegrambot.mybot;


import com.mytelegrambot.mybot.entity.Employee;
import com.mytelegrambot.mybot.entity.Status;
import com.mytelegrambot.mybot.repository.StatusRepository;
import com.mytelegrambot.mybot.repository.EmployeeRepository;
import com.mytelegrambot.mybot.service.EmploeeImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MyAmazingBot extends TelegramLongPollingBot {

    private KnownChatIds userInfo;

    @Autowired
    EmploeeImp emploeeService;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    StatusRepository statusRepository;


    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.getMessage() == null) {
            return;
        }
        if (update.getMessage().getText() != null && update.getMessage().getText().equals(("/start"))) {
            SendMessage sendMessage = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("Пожалуйста, подтвердите ваш номер.");
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);

            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText("Проверить регистрацию >").setRequestContact(true);
            keyboardFirstRow.add(keyboardButton);
            keyboard.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboard);

            log.info("{} id has started bot", update.getMessage().getChatId());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.getMessage().getContact() != null) {
            Long chatId = update.getMessage().getChatId();
            Contact contact = update.getMessage().getContact();

            //List<Employee> myInv = emploeeService.findAll();
            Employee myInv = employeeRepository.findPhoneNumber(contact.getPhoneNumber());
            Employee myChat = employeeRepository.findChatId(chatId.toString());
            Boolean a = false;
            Boolean b = false;
            if (myInv != null) {a=true;} else {myInv = employeeRepository.findPhoneNumber("1");}
            if (myChat != null) {a=true;} else {myChat = employeeRepository.findChatId("1");}

            System.out.println("LOGGING"+myInv + " "+ chatId);
            log.info("{} checked phone number {}",chatId,contact.getPhoneNumber());


            if (myChat.getChatId().contains(chatId.toString())){
                //log.info("Chat id {} with number {} already known.", chatId, contact.getPhoneNumber());
                String fullname = myChat.getFullName();
                sendMessage(chatId, "Здравствуйте, "+ fullname + ", \n Вы уже зарегистрированы.");
                //this.updateUser(chatId, contact.getPhoneNumber());
                sendMessage2(chatId, "Выберите Ваш статус: \n" +
                        "1. Карантин  \n" +
                        "2. На больничном  \n" +
                        "3. С плохим самочувствием  \n" +
                        "4. В офисе \n" +
                        "5. На удаленной работе  \n" +
                        "6. В отпуске ");
                log.info("{} id  already registered by {}", chatId, fullname);
            } else
             if (a && myInv.getPhoneNumber().contains(contact.getPhoneNumber()))  {
                 String fullname = myChat.getFullName();
                 sendMessage(chatId, "Здравствуйте, "+ fullname + ", \n Вы удачно провели валидацию.");
                 this.updateUser(chatId, contact.getPhoneNumber());
                 sendMessage2(chatId, "Выберите Ваш статус: \n" +
                         "1. Карантин  \n" +
                         "2. На больничном  \n" +
                         "3. С плохим самочувствием  \n" +
                         "4. В офисе \n" +
                         "5. На удаленной работе  \n" +
                         "6. В отпуске ");
                 log.info("Phone number {} and chat id {} registered " +
                         "succesfully by {}", contact.getPhoneNumber(),chatId,fullname);
             }
             else
             {
                sendMessage(chatId, "Ваш номер не может быть подтвержден. Обратитесь в Отдел кадр.");
                log.info("This number {} with chat id {} cannot be validated.", contact.getPhoneNumber(), chatId);
            }
        } else {
            Long chatId = update.getMessage().getChatId();
            Employee myChat = employeeRepository.findChatId(chatId.toString());
            if (myChat != null) {
                //int day = 4;
                String tomorrowText = "Пожалуйста, не забудьте завтра обновить Ваш статус до 11:00";
                switch (update.getMessage().getText().toString()) {
                    case "1":
                        sendMessage(chatId, "Ваше состояние зафиксировано: На карантине \n" + tomorrowText);
                        updateStatus(chatId, "На карантине");
                        log.info("{} have chosen 1 status", chatId);
                        break;
                    case "2":
                        sendMessage(chatId, "Ваше состояние зафиксировано: На больничном \n" + tomorrowText);
                        updateStatus(chatId, "На больничном");
                        log.info("{} have chosen 2 status", chatId);
                        break;
                    case "3":
                        sendMessage(chatId, "Ваше состояние зафиксировано: С плохим самочувствием \n" + tomorrowText);
                        updateStatus(chatId, "С плохим самочувствием");
                        log.info("{} have chosen 3 status", chatId);
                        break;
                    case "4":
                        sendMessage(chatId, "Ваше состояние зафиксировано: В офисе \n" + tomorrowText);
                        updateStatus(chatId, "В офисе");
                        log.info("{} have chosen 4 status", chatId);
                        break;
                    case "5":
                        sendMessage(chatId, "Ваше состояние зафиксировано: На удаленной работе \n" + tomorrowText);
                        updateStatus(chatId, "На удаленной работе");
                        log.info("{} have chosen 5 status", chatId);
                        break;
                    case "6":
                        sendMessage(chatId, "Ваше состояние зафиксировано: В отпуске \n" + tomorrowText);
                        updateStatus(chatId, "В отпуске");
                        log.info("{} have chosen 6 status", chatId);
                        break;
                    default:
                        sendMessage(chatId, "Неправильный вариант ответа");
                        log.info("{} have chosen wrong answer", chatId);
                        break;
                }

            } else {
                sendMessage(update.getMessage().getChatId(), "Ваш номер не может быть подтвержден. Обратитесь в Отдел кадр.");
                log.info("{} chat id is not registered", chatId);
                //Contact contact = update.getMessage().getContact();
                  //Long chatId = update.getMessage().getChatId();
                //System.out.println("phone number"+contact.getPhoneNumber());
                //System.out.println("userid"+contact.getUserID());
                //log.warn("Error validating number with chat id {}.", update.getMessage().getChatId());
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "<username>";
    }

    @Override
    public String getBotToken() {
        return "<token>";
    }

    private void sendMessage(Long id, String text) {
        try {
            execute(new SendMessage(id, text));
        } catch (TelegramApiException e) {
            //log.error("Error sending message.", e);
        }
    }

    private void sendMessage2(Long id, String text) {
        try {
            SendMessage sendM = new SendMessage()
                    .setChatId(id)
                    .setText(text);
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendM.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);

            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            KeyboardRow keyboardThirdRow = new KeyboardRow();
            KeyboardRow keyboardFourthRow = new KeyboardRow();
            KeyboardRow keyboardFifthRow = new KeyboardRow();
            KeyboardRow keyboardSixthRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            KeyboardButton keyboardButton2 = new KeyboardButton();
            KeyboardButton keyboardButton3 = new KeyboardButton();
            KeyboardButton keyboardButton4 = new KeyboardButton();
            KeyboardButton keyboardButton5 = new KeyboardButton();
            KeyboardButton keyboardButton6 = new KeyboardButton();
            keyboardButton.setText("1 ");
            keyboardButton2.setText("2 ");
            keyboardButton3.setText("3 ");
            keyboardButton4.setText("4 ");
            keyboardButton5.setText("5 ");
            keyboardButton6.setText("6 ");
            keyboardFirstRow.add(keyboardButton);
            keyboardSecondRow.add(keyboardButton2);
            keyboardThirdRow.add(keyboardButton3);
            keyboardFourthRow.add(keyboardButton4);
            keyboardFifthRow.add(keyboardButton5);
            keyboardSixthRow.add(keyboardButton6);
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardThirdRow);
            keyboard.add(keyboardFourthRow);
            keyboard.add(keyboardFifthRow);
            keyboard.add(keyboardSixthRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            execute(sendM);
        } catch (TelegramApiException e) {
            //log.error("Error sending message.", e);
        }
    }

    public void updateUser(Long chatId, String phone) {
        Employee userFromDb = employeeRepository.findPhoneNumber(phone);
        // crush the variables of the object found
        userFromDb.setChatId(chatId.toString());
        //userFromDb.setPhoneNumber(phone);
        employeeRepository.save(userFromDb);
    }

    public void updateStatus(Long chatId, String text) {
        //Employee userFromDb = employeeRepository.findById(u.getid());
        // crush the variables of the object found
        LocalDateTime rightNow = LocalDateTime.now();
        Status status = new Status();
        status.setUserChatId(chatId.toString());
        status.setText(text);
        statusRepository.save(status);
    }



    private void sendOneMessage(String chatId) {

        try {
            execute(new SendMessage(chatId, "Это работает"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public MyAmazingBot(String chatId) {
        sendOneMessage(chatId);
    }

    public MyAmazingBot() {
    }

}
