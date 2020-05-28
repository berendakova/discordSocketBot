package ru.kphu.bot;

import lombok.var;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.command.WebCommand;
import ru.kphu.bot.dto.MessageDto;

import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;

@Profile("web")
@Component

public class WebMessageResolver {

    private final ApplicationContext context;
    private final Map<String, WebCommand> commandMap = new HashMap<>();

    public WebMessageResolver(ApplicationContext applicationContext) {
        this.context = applicationContext;
        initializeCommands();
    }

    public Map<String, WebCommand> getCommandMap() {
        return commandMap;
    }

    private void initializeCommands() {
        var commands = context.getBeansOfType(WebCommand.class).values();
        commands.forEach(this::addCommand);
    }

    private void addCommand(WebCommand command) {
        commandMap.put(command.header().name(), command);
    }

    public String executeCommand(MessageDto message) {

        var content = message.getText().split(" ");

        if ((content[0].substring(0, 1)).equals("!")) {
            var commandText = content[0].substring(1);
            WebCommand command = commandMap.get(commandText);
            if (command == null) {
                throw new IllegalArgumentException("Unknown header " + commandText);
            }
            return command.execute(message);
        } else return "";
    }


}
