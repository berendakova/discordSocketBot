package ru.kphu.bot.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Game;
import ru.kphu.bot.model.Question;
import ru.kphu.bot.repositories.AnswerRepository;
import ru.kphu.bot.repositories.GameRepository;
import ru.kphu.bot.repositories.QuestionRepository;
import ru.kphu.bot.utils.ValidationUtils;

@Component
@Profile("dis")
public class AskCommand extends Command {
    private final ValidationUtils validationUtils;

    @Autowired
    GameRepository gameRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;


    public AskCommand(ValidationUtils validationUtils) {
        this.validationUtils = validationUtils;
    }

    @Override
    public void execute(GenericEvent event) {
        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
        try {
            validationUtils.validate(messageReceivedEvent.getMessage().getContentRaw(), 4);
        } catch (IllegalArgumentException e) {
            messageReceivedEvent.getChannel().sendMessage("Can't find command " + this.header().name() + "with allowed arguments").queue();
        }

        new Thread(() ->
        {

            MessageChannel channel = messageReceivedEvent.getTextChannel();
            String message = ((MessageReceivedEvent) event).getMessage().getContentRaw();
            String gameId = message.split(" ")[2];
            String question = message.split(" ")[3];
            User guess_user = ((MessageReceivedEvent) event).getAuthor();
            if (gameRepository.findById(Long.valueOf(gameId)).isPresent()) {
                Game game = gameRepository.findById(Long.valueOf(gameId)).get();
                Animal animal = game.getAnimal();
                if (questionRepository.findByDescription(question).isPresent()) {
                    Question asked = questionRepository.findByDescription(question).get();
                    if (answerRepository.findByAnimalAndQuestion(animal, asked).isPresent()) {
                        messageReceivedEvent.getChannel().sendMessage(answerRepository.findByAnimalAndQuestion(animal, asked).get().getIsTrue() + ", " + guess_user.getAsMention()).queue();
                    } else {
                        messageReceivedEvent.getChannel().sendMessage("I don't know. Try another question, " + guess_user.getAsMention()).queue();

                    }
                } else {
                    messageReceivedEvent.getChannel().sendMessage("Question is not found, " + guess_user.getAsMention()).queue();
                }
            } else {
                messageReceivedEvent.getChannel().sendMessage("Game is not found, " + guess_user.getAsMention()).queue();
            }
        }).start();
    }

    @Override
    public Headers header() {
        return Headers.ask;
    }

    @Override
    public String description() {
        return "Asking a question:  ask @TanuhaBomba gameId question";
    }

}