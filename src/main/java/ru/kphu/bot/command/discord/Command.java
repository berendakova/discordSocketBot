package ru.kphu.bot.command.discord;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.GenericEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;

@Component
@Profile("dis")
public abstract class Command {
    @Getter
    @Setter
    private ValidationUtils validationUtils;

    public abstract void execute(GenericEvent event);

    public abstract Headers header();

    public enum Headers{
     addAnimal,
     addAnswer,
     getHome,
     getAnimals,
     start,
     getMyAnimals,
     abandonAnimal,
     ask,
        questions,
     assumption,
     help,

    }

    public abstract String description();
}

