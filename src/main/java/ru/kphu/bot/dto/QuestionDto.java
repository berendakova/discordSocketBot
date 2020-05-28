package ru.kphu.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kphu.bot.model.Question;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionDto {
    private int id;
    private String description;
    public static QuestionDto from(Question question){
        return QuestionDto.builder()
                .description(question.getDescription())
                .id(question.getId())
                .build();
    }

    public static List<QuestionDto> from (List<Question> questions){
        return questions.stream()
                .map(QuestionDto::from)
                .collect(Collectors.toList());
    }
}
