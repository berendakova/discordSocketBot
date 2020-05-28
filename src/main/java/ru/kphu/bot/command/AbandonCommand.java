package ru.kphu.bot.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.service.PetService;
import ru.kphu.bot.utils.ValidationUtils;

@Component
@Profile("dis")
public class AbandonCommand extends Command {
    private final ValidationUtils validationUtils;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    PetService animalService;

    public AbandonCommand(ValidationUtils validationUtils) {
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
            String animalUserId = anismal.getIdUser();

            if(animalUserId.equals(((MessageReceivedEvent) event).getAuthor().getId())){
                if (anismal.getStatus() == 1) {
                    anismal.setStatus(0);
                    animalRepository.save(anismal);
                    channel.sendMessage("You abandon from animal").queue();

                } else {
                    channel.sendMessage("You can't  abandon an animal").queue();
                }
            }
            else{
                channel.sendMessage("You can't  abandon an animal").queue();
            }

        }).start();
    }

    @Override
    public Headers header() {
        return Headers.abandonAnimal;
    }

    @Override
    public String description() {
        return "You can refuse an animal- abandonAnimal @Tanuhabomba NameAnimal";
    }
}