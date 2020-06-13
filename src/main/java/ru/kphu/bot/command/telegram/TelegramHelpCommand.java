package ru.kphu.bot.command.telegram;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.kphu.bot.TelegramResolver;
import ru.kphu.bot.command.telegram.TelegCommand;

@Component
@Profile("tel")
public class TelegramHelpCommand extends TelegCommand {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Help\n");
        Header[] headers = Header.values();
        for (Header header : headers) {
            if(!header.equals(Header.start))
                builder.append("/").append(header.name()).append(" - ").append(applicationContext.getBean(TelegramResolver.class).getCommandMap().get(header.name()).description()).append("\n");
        }

        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        try {
            telegramBot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.help;
    }

    @Override
    public String description() {
        return "Send all commands";
    }
}