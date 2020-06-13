package ru.kphu.bot;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Component
@Profile("tel")
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramResolver telegramResolver;

    public TelegramBot(@Lazy TelegramResolver telegramResolver){
        this.telegramResolver = telegramResolver;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            System.out.println(update.getMessage().getFrom().getUserName());
            System.out.println(update.getMessage().getText());
            telegramResolver.executeCommand(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "MyLovelyAnimals";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}