package ru.kphu.bot.command.telegram;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.kphu.bot.TelegramBot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@Profile("tel")
public abstract class TelegCommand {

    @Autowired
    TelegramBot telegramBot;

    public abstract void execute(Update update);

    public abstract Header header();

    public abstract String description();

    protected void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        try {
            telegramBot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public enum Header {
        start,
        help,
        getAnimals,
    }


}