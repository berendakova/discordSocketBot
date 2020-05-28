package ru.kphu.bot.command;


import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.ApplicationContext;

import ru.kphu.bot.MessageResolver;
import ru.kphu.bot.utils.ValidationUtils;

import java.awt.*;


@Component
@Profile("dis")
public class DHelpCommand extends Command {
    @Autowired
    DHelpCommand help;
    @Autowired
    AddAnimalCommand addAnimalCommand;
    @Autowired
    GetAnimalCommand getAnimalCommand;
    @Autowired
    GetHomeCommand getHomeCommand;
    @Autowired
    AbandonCommand abandonCommand;
    @Autowired
    AddAnswerCommand addAnswerCommand;
    @Autowired
    GetMyAnimals getMyAnimalsCommand;
    @Autowired
    StartCommand startCommand;
    @Autowired
    AskCommand askCommand;
    @Autowired
    AssumptionCommand assumptionCommand;
    private final ApplicationContext context;
    private final ValidationUtils validationUtils;


    public DHelpCommand(ApplicationContext context, ValidationUtils validationUtils) {
        this.context = context;
        this.validationUtils = validationUtils;
    }

    @Override
    public void execute(GenericEvent event) {
        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;


        try {
            validationUtils.validate(messageReceivedEvent.getMessage().getContentRaw(), 2);

        } catch (IllegalArgumentException e) {
            messageReceivedEvent.getChannel().sendMessage("Can't find command " + this.header().name() + "with allowed arguments").queue();
        }

        var header = Headers.values();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Help");
        builder.setImage("https://im0-tub-ru.yandex.net/i?id=e5bbe862ad0549959f4db5f86805cf57&n=13");
        builder.setColor(new Color(255,22,72));
        builder.setDescription("Commands for Game: \n");
        builder.addField(help.header().name(), help.description(), false);
        builder.addField(startCommand.header().name(),startCommand.description(),false);
        builder.addField(addAnimalCommand.header().name(),addAnimalCommand.description(),false);
        builder.addField(getAnimalCommand.header().name(),getAnimalCommand.description(),false);
        builder.addField(addAnswerCommand.header().name(),addAnswerCommand.description(),false);
        builder.addField(askCommand.header().name(),askCommand.description(),false);
        builder.addField(getAnimalCommand.header().name(),getAnimalCommand.description(),false);
        builder.addField(getHomeCommand.header().name(),getHomeCommand.description(),false);
        builder.addField(assumptionCommand.header().name(),assumptionCommand.description(),false);
        builder.addField(getMyAnimalsCommand.header().name(),getMyAnimalsCommand.description(),false);
        builder.addField(abandonCommand.header().name(),abandonCommand.description(),false);

        messageReceivedEvent.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public Headers header() {
        return Headers.help;
    }

    @Override
    public String description() {
        return "Show all commands";
    }
}