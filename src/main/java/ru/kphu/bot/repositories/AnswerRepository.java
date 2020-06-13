package ru.kphu.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Answer;
import ru.kphu.bot.model.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

    List<Answer> findAllByAnimal(Animal animal);
    Optional<Answer> findByAnimalAndQuestion(Animal animal, Question question);
    List<Answer> findAll();
}
