package ru.kphu.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kphu.bot.dto.QuestionDto;
import ru.kphu.bot.model.Question;
import ru.kphu.bot.repositories.QuestionRepository;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Override
    public Question addQuestion(QuestionDto questionDto) {
        Question question = Question.builder()
                .description(questionDto.getDescription())
                .id(questionDto.getId())
                .build();
        questionRepository.save(question);
        return question;
    }
}
