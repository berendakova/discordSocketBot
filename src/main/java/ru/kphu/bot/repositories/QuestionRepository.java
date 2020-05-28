package ru.kphu.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kphu.bot.model.Question;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Optional<Question> findByDescription(String description);
}
