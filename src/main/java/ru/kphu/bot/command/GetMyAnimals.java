package ru.kphu.bot.command;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.service.PetService;
import ru.kphu.bot.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dis")
public class GetMyAnimals extends Command {
    private final ValidationUtils validationUtils;
    @Autowired
    PetService petService;
    @Autowired
    AnimalRepository animalRepository;

    public GetMyAnimals(ValidationUtils validationUtils) {
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
        List<AnimalDto> animalDtoList = new ArrayList<>();
        String userId =( (MessageReceivedEvent) event).getAuthor().getId();
        List<Animal> allAnimals = animalRepository.findAnimalByIdUser(userId);

        builder.setTitle("Your Animals");

        for (Animal animal : allAnimals
        ) {

            builder.addField(animal.getName(), "in your hand", false);
            builder.setImage(animal.getJpg());
        }
        messageReceivedEvent.getChannel().sendMessage(builder.build()).queue();

    }



    @Override
    public Headers header() {
        return Headers.getMyAnimals;
    }

    @Override
    public String description() {
        return "You can get animals in your hand : getMyAnimals @TanuhaBomba";
    }
}
