package ru.kphu.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.kphu.bot.TelegramBot;
import ru.kphu.bot.TelegramResolver;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "ru.kphu.bot")
@Profile("tel")
public class TelegramBotConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public TelegramLongPollingBot init(TelegramResolver telegramResolver){
//        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            TelegramLongPollingBot telegramLongPollingBot = new TelegramBot(telegramResolver);
            telegramBotsApi.registerBot(telegramLongPollingBot);
            return telegramLongPollingBot;
        } catch (TelegramApiRequestException e) {
            throw new IllegalStateException("Failed creating bot", e);
        }
    }

}