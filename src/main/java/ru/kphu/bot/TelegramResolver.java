package ru.kphu.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.kphu.bot.command.telegram.TelegCommand;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("tel")
public class TelegramResolver {

    private final ApplicationContext context;
    private final Map<String, TelegCommand> commandMap = new HashMap<>();
    @Autowired
    TelegramBot telegramBot;

    public TelegramResolver(ApplicationContext context) {
        this.context = context;
        initializeCommands();
    }

    public Map<String, TelegCommand> getCommandMap() {
        return commandMap;
    }

    private void initializeCommands() {
        Collection<TelegCommand> commands = context.getBeansOfType(TelegCommand.class).values();
        for (TelegCommand command : commands) {
            addCommand(command);
        }
    }

    private void addCommand(TelegCommand command) {
        commandMap.put(command.header().name(), command);
    }

    public void executeCommand(Update update) {
        Message message = update.getMessage();

        String commandText = message.getText().toLowerCase().split(" ")[0];
        if (commandText.charAt(0) == '/') {
            String commandName = commandText.substring(1);

            TelegCommand command = commandMap.get(commandName);
            if (command != null) {
                command.execute(update);
            }
        }
    }
}