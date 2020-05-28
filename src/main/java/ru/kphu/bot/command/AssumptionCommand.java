package ru.kphu.bot.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.model.Game;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.repositories.AnswerRepository;
import ru.kphu.bot.repositories.GameRepository;
import ru.kphu.bot.service.GameService;
import ru.kphu.bot.utils.ValidationUtils;

@Component
@Profile("dis")
public class AssumptionCommand extends Command {
    private final ValidationUtils validationUtils;
    @Autowired
    GameService service;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    AnimalRepository animalRepository;


    public AssumptionCommand(ValidationUtils validationUtils) {
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
            String name = message.split(" ")[3];
            if (gameRepository.findById(Long.valueOf(gameId)).isPresent() & animalRepository.getAnimalByName(name).isPresent()) {
                Game game = gameRepository.findById(Long.valueOf(gameId)).get();
                if (!game.getIsFinished()) {
                    String guessed_name = game.getAnimal().getName();
                    User guess_user = ((MessageReceivedEvent) event).getAuthor();
                    if (name.equals(guessed_name)) {
                        game.setIsFinished(true);
                        gameRepository.delete(game);
                        messageReceivedEvent.getChannel().sendMessage("Winn, " + guess_user.getAsMention()).queue();
                    } else {
                        messageReceivedEvent.getChannel().sendMessage("Your answer is false, " + guess_user.getAsMention()).queue();
                    }
                } else {
                    messageReceivedEvent.getChannel().sendMessage("Game is over.").queue();
                }
            } else {
                messageReceivedEvent.getChannel().sendMessage("Game or user is not found.").queue();
            }
        }).start();
    }

    @Override
    public Headers header() {
        return Headers.assumption;
    }

    @Override
    public String description() {
        return "You can refuse an animal : assumption @TanuhaBomba NameAnimal   ";
    }

}