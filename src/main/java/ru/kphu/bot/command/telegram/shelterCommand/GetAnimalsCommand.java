package ru.kphu.bot.command.telegram.shelterCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.kphu.bot.TelegramResolver;
import ru.kphu.bot.command.telegram.TelegCommand;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.service.PetService;

import java.util.ArrayList;
import java.util.List;
@Component
@Profile("tel")
public class GetAnimalsCommand extends TelegCommand {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    PetService petService;
    @Override
    public void execute(Update update) {
        StringBuilder builder = new StringBuilder();
        Message message = update.getMessage();
        List<AnimalDto> animalDtoList = new ArrayList<>();
        List<AnimalDto> allAnimals = petService.getAllAnimals();
        builder.append("This is animals:\n");
        String m = "\n";
        for (AnimalDto animal : allAnimals
        ) {
            String status;
            if (animal.getStatus() == 0) {
                status = " pet in shelter";
            } else {
                status = "pet in hand";
            }
            builder.append("/").append(animal.getName()).append(" - ").append(status).append(" ").append("\n");

        }
    }

    @Override
    public Header header() {
        return Header.getAnimals;
    }

    @Override
    public String description() {
        return "Return all animals in shelter/hand";
    }
}
