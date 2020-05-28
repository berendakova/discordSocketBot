package ru.kphu.bot.service;

import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.dto.AnswerDto;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Answer;

public interface AnswerService {
    Answer addAnswer(AnswerDto answer);
}
