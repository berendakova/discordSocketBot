package ru.kphu.bot.service;


import ru.kphu.bot.dto.QuestionDto;
import ru.kphu.bot.model.Question;

public interface QuestionService {
    Question addQuestion(QuestionDto questionDto);
}
