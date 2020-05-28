package ru.kphu.bot.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.dto.AnswerDto;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.service.AnswerService;
import ru.kphu.bot.utils.ValidationUtils;

@Component
@Profile("dis")
public class AddAnswerCommand extends Command {
    private final ValidationUtils validationUtils;
    @Autowired
    AnswerService answerService;
    @Autowired
    AnimalRepository animalRepository;

    public AddAnswerCommand(ValidationUtils validationUtils) {
        this.validationUtils = validationUtils;
    }


    @Override
    public void execute(GenericEvent event) {

        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
        try {
            validationUtils.validate(messageReceivedEvent.getMessage().getContentRaw(), 5);
        } catch (IllegalArgumentException e) {
            messageReceivedEvent.getChannel().sendMessage("Can't find command " + this.header().name() + "with allowed arguments").queue();
        }

        new Thread(() ->
        {
            MessageChannel channel = messageReceivedEvent.getTextChannel();
            String message = ((MessageReceivedEvent) event).getMessage().getContentRaw();
            String name = message.split(" ")[2];
            String question = message.split(" ")[3];
            String answer = message.split(" ")[4];
            System.out.println(name + "NAme");
            if (animalRepository.getAnimalByName(name).isPresent()) {
                answerService.addAnswer(new AnswerDto( 0, name, question, answer.toLowerCase().equals("true")));
                channel.sendMessage("Answer " + answer + " was successfully added to " + name).queue();
            } else {
                channel.sendMessage("Animal " + name + " hasn't found.").queue();
            }
        }).start();
    }


    @Override
    public Headers header() {
        return Headers.addAnswer;
    }

    @Override
    public String description() {
        return "Add Answer : addAnswer @TanuhaBomba Animal Question Answer";
    }
}
