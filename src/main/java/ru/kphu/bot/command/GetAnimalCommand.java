package ru.kphu.bot.command;

import lombok.var;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.service.PetService;
import ru.kphu.bot.utils.ValidationUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Component
@Profile("dis")
public class GetAnimalCommand extends Command {
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    PetService petService;
    private final ApplicationContext context;
    private final ValidationUtils validationUtils;


    public GetAnimalCommand(ApplicationContext context, ValidationUtils validationUtils) {
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
        List<AnimalDto> animalDtoList = new ArrayList<>();
        List<AnimalDto> allAnimals = petService.getAllAnimals();
        builder.setTitle("Animals");

        for (AnimalDto animal :allAnimals
             ) {
            String status;
            if(animal.getStatus() == 0){
                status = " pet in shelter";
            }
            else{
                status = "pet in hand";
            }
            builder.addField(animal.getName(),status + " " + animal.getJpg(),false);

        }
        messageReceivedEvent.getChannel().sendMessage(builder.build()).queue();

    }

    @Override
    public Headers header() {
        return Headers.getAnimals;
    }

    @Override
    public String description() {
        return "Get All animals : getAnimals @TanuhaBomba";
    }
}
