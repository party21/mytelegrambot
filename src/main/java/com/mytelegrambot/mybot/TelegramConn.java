package com.mytelegrambot.mybot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TelegramConn {

    //private final TelegramConfig config;

    private final KnownChatIds chatIds;

    private MyAmazingBot bot;

    @Bean
    public MyAmazingBot getBot() {
        return bot;
    }

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        bot = new MyAmazingBot();
        api.registerBot(bot);
    }

}