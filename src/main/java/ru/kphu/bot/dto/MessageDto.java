package ru.kphu.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Profile({"dis","web"})
public class MessageDto {

    private String text;
    private UserDto from;


    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .text(message.getText())
                .build();
    }
}