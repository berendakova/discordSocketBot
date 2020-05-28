package ru.kphu.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.dto.AnswerDto;
import ru.kphu.bot.dto.QuestionDto;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Answer;
import ru.kphu.bot.model.Question;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.repositories.AnswerRepository;
import ru.kphu.bot.repositories.QuestionRepository;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    QuestionServiceImpl questionService;
    @Override
    public Answer addAnswer(AnswerDto answerDto) {
        Question question;
        if(questionRepository.findByDescription(answerDto.getQuestion()).isPresent()){
            question = questionRepository.findByDescription(answerDto.getQuestion()).get();
        }
        else{
            question = questionService.addQuestion(new QuestionDto(0, answerDto.getQuestion()));
        }
        Answer answer = Answer.builder()
                .id(answerDto.getId())
                .question(question)
                .animal(animalRepository.getAnimalByName(answerDto.getAnimal()).get())
                .isTrue(answerDto.getIsTrue())
                .build();
        answerRepository.save(answer);
        return answer;
    }
}
