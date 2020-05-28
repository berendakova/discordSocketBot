package ru.kphu.bot.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.dto.AnswerDto;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.service.PetService;
import ru.kphu.bot.utils.ValidationUtils;

import java.util.Optional;
@Component
@Profile("dis")
public class GetHomeCommand extends Command {
    private final ValidationUtils validationUtils;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    PetService animalService;
    public GetHomeCommand(ValidationUtils validationUtils) {
        this.validationUtils = validationUtils;
    }

    @Override
    public void execute(GenericEvent event) {

        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
        try {
            validationUtils.validate(messageReceivedEvent.getMessage().getContentRaw(), 3);
        } catch (IllegalArgumentException e) {
            messageReceivedEvent.getChannel().sendMessage("Can't find command " + this.header().name() + "with allowed arguments").queue();
        }

        new Thread(() ->
        {
            MessageChannel channel = messageReceivedEvent.getTextChannel();
            String message = ((MessageReceivedEvent) event).getMessage().getContentRaw();
            String name = message.split(" ")[2];
            Animal anismal = animalRepository.findAnimalByName(name);
            if(anismal.getName()!= null){
                if(anismal.getStatus() == 0){
                    anismal.setStatus(1);
                    anismal.setIdUser(((MessageReceivedEvent) event).getAuthor().getId());
                    animalRepository.save(anismal);
                    channel.sendMessage("You have booked an animal").queue();

                }
                else{
                    channel.sendMessage("You can't  booked an animal").queue();
                }
            }

        }).start();
    }

    @Override
    public Headers header() {
        return Headers.getHome;
    }

    @Override
    public String description() {
        return "You can get Animal at Home - getHome @Tanuhabomba NameAnimal";
    }
}
