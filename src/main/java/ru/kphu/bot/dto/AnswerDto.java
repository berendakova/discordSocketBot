package ru.kphu.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kphu.bot.model.Answer;
import ru.kphu.bot.model.Question;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnswerDto {
    private int id;
    private String animal;
    private String question;
    private Boolean isTrue;

    public static AnswerDto from(Answer answer){
        return AnswerDto.builder()
                .id(answer.getId())
                .animal(AnimalDto.from(answer.getAnimal()).getName())
                .question(QuestionDto.from(answer.getQuestion()).getDescription())
                .isTrue(answer.getIsTrue())
                .build();
    }

    public static List<AnswerDto> from (List<Answer> answers){
        return answers.stream()
                .map(AnswerDto::from)
                .collect(Collectors.toList());
    }

}
