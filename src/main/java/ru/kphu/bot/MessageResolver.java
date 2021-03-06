package ru.kphu.bot;

import lombok.var;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.command.discord.Command;

import java.util.HashMap;
import java.util.Map;

@Component
@Profile("dis")
public class MessageResolver {

    private final ApplicationContext context;

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    private final Map<String, Command> commandMap = new HashMap<>();
    private final JDA jda;

    public MessageResolver(ApplicationContext applicationContext, JDA jda) {
        this.context = applicationContext;
        this.jda = jda;
        initializeCommands();
    }

    private void initializeCommands() {
        var commands = context.getBeansOfType(Command.class).values();
        commands.forEach(this::addCommand);
    }

    private void addCommand(Command command) {
        commandMap.put(command.header().name(), command);
    }

    public void executeCommand(MessageReceivedEvent event) {

        Message message = event.getMessage();

        String commandText = message.getContentRaw();
        System.out.println("typed:"+commandText);
        Command command = commandMap.get(commandText.split(" ")[0]);


        if (message.getMentionedMembers().contains(event.getGuild().getSelfMember()) ||
                message.getContentRaw().contains("709317853114793984")) {
            if (command == null) {
                throw new IllegalArgumentException("Unknown header " + commandText);
            }
            System.out.println("executing " + command);
            command.execute(event);
        }
    }
}