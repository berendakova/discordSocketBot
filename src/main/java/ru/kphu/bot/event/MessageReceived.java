package ru.kphu.bot.event;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.kphu.bot.MessageResolver;

import javax.annotation.Nonnull;

@Component
@Slf4j
@Profile("dis")
public class MessageReceived extends ListenerAdapter {

    private final MessageResolver resolver;

    public MessageReceived(@Lazy MessageResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        resolver.executeCommand(event);
    }

}