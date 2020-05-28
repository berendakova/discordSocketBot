/*
package ru.kphu.bot;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {
    private static final Collection<Emoji> EMOJI_ALL = EmojiManager.getAll();
    private static final String token = "NzA5MDQ4MDQ5Mjc1NTAyNzIy.XrkCGg.GeloY-bDRkquFjc0L1kkMNgPO3o";

    public static void main(String[] args) {
        App app = new App();
        app.init();
    }

    private void init() {
        try {
            JDA jda = new JDABuilder(token).build();
            jda.addEventListener(new Bot());
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    private static class Bot extends ListenerAdapter {
        private Message preMessage;

        private static String getBot(List<Member> members, String id) {
            List<Member> bots = new ArrayList<Member>();
            for (Member member : members) {
                if (member.getUser().isBot() &&
                        member.getOnlineStatus().equals(OnlineStatus.ONLINE) &&
                        !member.getId().equals(id)
                )
                    bots.add(member);
            }
            if (bots.isEmpty())
                return null;
            else {
                int rand = (int) (Math.random() * bots.size());
                return bots.get(rand).getId();
            }
        }

        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            Message message = event.getMessage();
            MessageChannel channel = event.getChannel();
            List<Member> members = event.getGuild().getMembers();
            if (message.getContentRaw().toLowerCase().startsWith("lead") && !message.getMentionedMembers().isEmpty()) {
                preMessage = message;
                if (message
                        .getMentionedMembers()
                        .get(0)
                        .getId()
                        .equals(event
                                .getJDA()
                                .getSelfUser()
                                .getId()
                        )
                ) {
                    String randomBotId = getBot(members, event.getJDA().getSelfUser().getId());
                    channel.sendTyping().queue();
                    if (randomBotId != null) {
                        channel.sendMessage("lead <@" + randomBotId + ">").queueAfter(4, TimeUnit.SECONDS);
                    } else
                        channel.sendMessage("Я не хочу играть один ").queueAfter(2, TimeUnit.SECONDS);

                }
                for (int i = 0; i < 10; i++) {
                    message.addReaction(getRandomEmoji()).queue();
                }
            }

        }

        private static String getRandomEmoji() {
            Emoji[] emojis = new Emoji[EMOJI_ALL.size()];
            EMOJI_ALL.toArray(emojis);
            int rand = (int) (Math.random() * emojis.length);
            return emojis[rand].getUnicode();
        }

        @Override
        public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
            System.out.println(preMessage.getReactions());
            if (event.getMessageId().equals(preMessage.getId())) {
                checkEmoji(preMessage, event.getJDA().getSelfUser().getName());
            }
        }

        private static void checkEmoji(Message preMessage, String name) {
            List<MessageReaction> messageReactions = preMessage.getReactions();
            for (MessageReaction reaction : messageReactions) {
                List<User> reactedUsers = reaction.retrieveUsers().complete();
                if (reactedUsers.size() > 1) {
                    for (User user : reactedUsers) {
                        if (!user.getName().equals(name))
                            preMessage.getChannel().sendMessage(
                                    user.getAsMention() + "have same emojis"
                            ).queue();
                    }
                }
            }
        }
    }
}*/
