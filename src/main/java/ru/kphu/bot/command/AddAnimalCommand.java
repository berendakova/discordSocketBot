package ru.kphu.bot.command;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.service.PetServiceImpl;
import ru.kphu.bot.utils.ValidationUtils;

@Component
@Profile("dis")
public class AddAnimalCommand extends Command{

    private final ValidationUtils validationUtils;
    @Autowired
    PetServiceImpl animalService;

    public AddAnimalCommand(ValidationUtils validationUtils) {
        this.validationUtils = validationUtils;
    }

    @Override
    public void execute(GenericEvent event) {

        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
        System.out.println(event);
        try {

            validationUtils.validate(messageReceivedEvent.getMessage().getContentRaw(), 4);
        } catch (IllegalArgumentException e) {
            messageReceivedEvent.getChannel(    ).sendMessage("Can't find command " + this.header().name() + "with allowed arguments").queue();
        }

        new Thread(() ->
        {
            MessageChannel channel = messageReceivedEvent.getTextChannel();
            String message = ((MessageReceivedEvent) event).getMessage().getContentRaw();
            String name = message.split(" ")[2];
            String image = message.split(" ")[3];
            animalService.addAnimal(new AnimalDto(0, name, image,0,"0", null));
            channel.sendMessage("Animal " + name + " was added.").queue();
        }).start();
    }

    @Override
    public Headers header() {
        return Headers.addAnimal;
    }

    @Override
    public String description() {
        return "Add animal: addPerson @TanuhaBombaBot name url";
    }

}