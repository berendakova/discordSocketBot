package ru.kphu.bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Answer;
import ru.kphu.bot.model.Game;
import ru.kphu.bot.repositories.AnswerRepository;
import ru.kphu.bot.service.GameService;
import ru.kphu.bot.utils.ValidationUtils;

import java.awt.*;
import java.util.List;

@Component
@Profile("dis")
public class StartCommand extends Command {
    private final ValidationUtils validationUtils;

    @Autowired
    GameService service;
    @Autowired
    AnswerRepository answerRepository;


    public StartCommand(ValidationUtils validationUtils) {
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

        new Thread(() ->
        {
            MessageChannel channel = messageReceivedEvent.getTextChannel();
            Game game = service.addGame();
            Animal animal = game.getAnimal();
            String gameId = game.getId().toString();
            List<Answer> answers = answerRepository.findAllByAnimal(animal);
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Questions for " + gameId);
            builder.setColor(new Color(255,22,72));
            builder.setDescription("Game is started! Id: " + gameId + "\n Ask questions using: ask @EasyBot <question> <game-id>\n");
            int i = 0;
            for (Answer answer : answers
            ) {
                builder.addField(String.valueOf(i), answer.getQuestion().getDescription(), false);
                i++;
            }
            messageReceivedEvent.getChannel().sendMessage(builder.build()).queue();
        }).start();
    }

    @Override
    public Headers header() {
        return Headers.start;
    }

    @Override
    public String description() {
        return "Starting new game.\n Use: \n start @TanuhaBombaBot";
    }
}